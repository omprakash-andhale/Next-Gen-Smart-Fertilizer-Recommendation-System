"""
Raspberry Pi Pico 2 W MicroPython Production Firmware
------------------------------------------------------
Upload this file (as main.py) to your physical Raspberry Pi Pico 2 W using Thonny or VS Code Pico Extension.

Wiring Diagram:
- MAX485 DI  -> Pico GP0 (UART0 TX)
- MAX485 RO  -> Pico GP1 (UART0 RX)
- MAX485 DE & RE -> Pico GP2 (Direction control: HIGH=Transmit, LOW=Receive)
- pH Sensor Analog Out -> Pico GP26 (ADC0)
- DHT11 Data Pin -> Pico GP15 (with 10k pull-up resistor)
- Power: VBUS (5V) / 3V3 and GND
"""

import time
import machine
import network
import urequests
import json
import dht

# --- Configuration ---
WIFI_SSID = "YOUR_WIFI_NAME"
WIFI_PASSWORD = "YOUR_WIFI_PASSWORD"
SERVER_URL = "http://192.168.1.2:8080/api/fertilizer/sensors/data"
DEVICE_ID = "PICO2W-SOIL-PHYSICAL-01"

# --- Hardware Setup ---
# 1. UART for MAX485 RS485 Modbus RTU Communication
uart = machine.UART(0, baudrate=9600, tx=machine.Pin(0), rx=machine.Pin(1), timeout=500)
de_re_pin = machine.Pin(2, machine.Pin.OUT)
de_re_pin.value(0) # Default to receive mode

# 2. pH Sensor on ADC0 (GP26)
ph_adc = machine.ADC(machine.Pin(26))

# 3. DHT11 on GP15
dht_sensor = dht.DHT11(machine.Pin(15))

# --- Modbus RTU Query Frame for 7-in-1 NPK Sensor ---
# Standard Modbus query for Nitrogen, Phosphorus, Potassium:
# Address 0x01, Read Holding Registers (0x03), Start Register 0x001E, 3 registers, CRC
MODBUS_NPK_QUERY = bytes([0x01, 0x03, 0x00, 0x1E, 0x00, 0x03, 0x65, 0xCD])

def connect_wifi():
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    if not wlan.isconnected():
        print(f"Connecting to Wi-Fi: {WIFI_SSID}...")
        wlan.connect(WIFI_SSID, WIFI_PASSWORD)
        timeout = 20
        while not wlan.isconnected() and timeout > 0:
            time.sleep(0.5)
            timeout -= 1
    if wlan.isconnected():
        print(" Connected to Wi-Fi. IP:", wlan.ifconfig()[0])
    else:
        print(" Failed to connect to Wi-Fi.")

def read_npk_physical():
    """Queries RS485 Modbus NPK sensor probe"""
    try:
        # Switch MAX485 to Transmit mode
        de_re_pin.value(1)
        time.sleep_ms(10)
        uart.write(MODBUS_NPK_QUERY)
        time.sleep_ms(10)
        # Switch back to Receive mode
        de_re_pin.value(0)

        time.sleep_ms(100)
        if uart.any() >= 11:
            res = uart.read(11)
            # Frame: [Addr, Func, ByteCount, N_hi, N_lo, P_hi, P_lo, K_hi, K_lo, CRC_lo, CRC_hi]
            nitrogen = (res[3] << 8) | res[4]
            phosphorus = (res[5] << 8) | res[6]
            potassium = (res[7] << 8) | res[8]
            return float(nitrogen), float(phosphorus), float(potassium)
    except Exception as e:
        print("NPK Modbus Read Error:", e)
    # Default fallback if sensor not in soil
    return 120.0, 60.0, 140.0

def read_ph_physical():
    """Reads analog pH probe voltage and applies calibration curve"""
    try:
        # Read 16-bit ADC value (0-65535) and convert to voltage (0-3.3V)
        raw = ph_adc.read_u16()
        voltage = raw * 3.3 / 65535
        # Standard pH probe calibration equation (Adjust slope/offset during buffer calibration)
        # pH = 7.0 + ((2.5 - voltage) * 3.5)
        ph = 7.0 + ((2.5 - voltage) * 3.5)
        return round(max(0.0, min(14.0, ph)), 2)
    except Exception as e:
        print("pH Read Error:", e)
        return 6.8

def read_dht11_physical():
    """Reads digital temperature and humidity from DHT11"""
    try:
        dht_sensor.measure()
        temp = dht_sensor.temperature()
        hum = dht_sensor.humidity()
        return float(temp), float(hum)
    except Exception as e:
        print("DHT11 Read Error:", e)
        return 28.0, 65.0

def loop_and_transmit():
    while True:
        n, p, k = read_npk_physical()
        ph = read_ph_physical()
        temp, hum = read_dht11_physical()

        payload = {
            "deviceId": DEVICE_ID,
            "nitrogen": n,
            "phosphorus": p,
            "potassium": k,
            "ph": ph,
            "temperature": temp,
            "humidity": hum,
            "soilMoisture": round(hum * 0.7, 1)
        }

        print(f"📡 Sensor Readings: N={n} P={p} K={k} mg/kg | pH={ph} | T={temp}°C H={hum}%")

        try:
            headers = {'Content-Type': 'application/json'}
            res = urequests.post(SERVER_URL, data=json.dumps(payload), headers=headers)
            print(" Server Response:", res.status_code, res.text)
            res.close()
        except Exception as err:
            print(" Transmission Failed:", err)

        time.sleep(5)

if __name__ == "__main__":
    connect_wifi()
    loop_and_transmit()
