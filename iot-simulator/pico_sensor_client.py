"""
Raspberry Pi Pico 2 W MicroPython / Python Sensor Node Simulator
-----------------------------------------------------------------
Simulates the hardware setup described in Chapter 4 & 8:
- NPK Soil Sensor (RS485 Modbus RTU via MAX485 TTL converter)
- Analog pH Sensor Electrode Module
- DHT11 Digital Temperature & Humidity Sensor
- Transmits telemetry packets to Spring Boot REST endpoint: /api/fertilizer/sensors/data
"""

import time
import random
import json
import urllib.request
import urllib.error

SERVER_URL = "http://localhost:8080/api/fertilizer/sensors/data"
DEVICE_ID = "PICO2W-SOIL-NODE-01"

def read_npk_modbus():
    """Simulate RS485 Modbus RTU register query (0x001E, 0x001F, 0x0020)"""
    nitrogen = round(random.uniform(115.0, 135.0), 1)
    phosphorus = round(random.uniform(60.0, 75.0), 1)
    potassium = round(random.uniform(130.0, 155.0), 1)
    return nitrogen, phosphorus, potassium

def read_ph_sensor():
    """Simulate analog pH probe voltage to pH conversion"""
    return round(random.uniform(6.4, 7.2), 2)

def read_dht11():
    """Simulate DHT11 single-bus digital temperature and humidity reading"""
    temperature = round(random.uniform(26.0, 31.0), 1)
    humidity = round(random.uniform(60.0, 75.0), 1)
    return temperature, humidity

def transmit_packet():
    n, p, k = read_npk_modbus()
    ph = read_ph_sensor()
    temp, hum = read_dht11()

    payload = {
        "deviceId": DEVICE_ID,
        "nitrogen": n,
        "phosphorus": p,
        "potassium": k,
        "ph": ph,
        "temperature": temp,
        "humidity": hum,
        "soilMoisture": round(random.uniform(40.0, 55.0), 1)
    }

    print(f"📡 [Pico 2 W] Transmitting Sensor Packet: N={n} P={p} K={k} pH={ph} T={temp}°C H={hum}%")

    try:
        data = json.dumps(payload).encode('utf-8')
        req = urllib.request.Request(
            SERVER_URL,
            data=data,
            headers={'Content-Type': 'application/json'}
        )
        with urllib.request.urlopen(req, timeout=5) as response:
            res_body = response.read().decode('utf-8')
            print(f"✅ [Server Response]: {res_body}")
    except urllib.error.URLError as e:
        print(f"⚠️ [Connection Error]: Could not reach Spring Boot backend ({e.reason})")

if __name__ == "__main__":
    print("🌾 Starting Raspberry Pi Pico 2 W Soil Sensor Emulation...")
    print(f"Target Gateway: {SERVER_URL}")
    for i in range(3):
        transmit_packet()
        time.sleep(2)
    print("✨ Pico sensor test run completed.")
