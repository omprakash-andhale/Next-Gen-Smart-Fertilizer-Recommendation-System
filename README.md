# 🌾 Next Gen-Smart Fertilizer Recommendation System

> **IoT Edge Telemetry (Raspberry Pi Pico 2 W) • Java Spring Boot Backend • Real-Time Web Advisory Dashboard**  
> *Published at 2nd International Conference on Recent Advances in Technology & Management (ICRATM-2026)*

---

## 👨‍💻 Project Authors & Academic Credentials

- **Pratik Sadar** (Exam Seat No: `B401030056` | Email: `pratiksadar9@gmail.com`)
- **Priyanka Jawale** (Exam Seat No: `B401030051` | Email: `priyankajawale847@gmail.com`)
- **Omprakash Andhale** (Exam Seat No: `B401030045` | Email: `omprakashandhale8@gmail.com`)

**Project Guide:** Prof. Kalpita Mane (`kalpitamane1989@gmail.com`)  
**Department:** Department of Electronics and Telecommunication Engineering  
**Institution:** Dr. D.Y. Patil School of Engineering & Technology, Savitribai Phule Pune University (A.Y. 2025–26)

---

## 📖 Overview
The **Next Gen-Smart Fertilizer Recommendation System** is an end-to-end precision agriculture solution that bridges real-time soil sensing with intelligent nutrient deficit analysis. The system captures field parameters (Nitrogen, Phosphorus, Potassium, soil pH, temperature, and humidity) via hardware sensors, processes them using a Java Spring Boot REST API, and provides farmers with instant, localized fertilizer recommendations.

---

## 🌟 Key Features
- **🌿 Intelligent Fertilizer Engine**: Agronomic recommendation matrix supporting **Tomato, Wheat, Rice, Cotton, Maize, and Sugarcane**, mapping nutrient gaps to **Urea (46% N), DAP, MOP, Elemental Sulfur, Lime, and NPK 19:19:19**.
- **🌐 Responsive Web Dashboard**: Full-screen, farmer-friendly dashboard featuring real-time sensor gauges, interactive Chart.js time-series telemetry, and manual test override sandbox.
- **🇮🇳 Bilingual Localization**: Instant 1-click switching between **Hindi (हिन्दी)** and **English**.
- **☀️/🌙 Adaptive Themes**: Clean white light theme and high-contrast dark theme with preference persistence.
- **📡 IoT Hardware Integration**: MicroPython firmware for **Raspberry Pi Pico 2 W** querying **RS485 Modbus RTU NPK sensors** (via MAX485), analog pH electrode, and DHT11.
- **🛡️ Enterprise Backend**: Built on **Java 17 / Spring Boot 3**, Spring Data JPA, H2/MySQL support, Bean Validation, and global exception handling.

---

## 🛠️ Architecture & Tech Stack
- **Backend**: Java 17, Spring Boot 3.3.4, Spring Web, Spring Data JPA, Maven
- **Database**: H2 (In-memory development) / MySQL (Production ready)
- **Frontend**: Vanilla HTML5, CSS3, JavaScript (ES6+), Chart.js
- **Microcontroller & IoT**: Raspberry Pi Pico 2 W (RP2350 / ARM Cortex-M33), MAX485 TTL-to-RS485, Modbus RTU Soil Sensor, pH Electrode, DHT11

---

## 🚀 Getting Started

### 1. Prerequisites
- Java Development Kit (JDK 17 or higher)
- Apache Maven 3.8+
- Python 3 (optional, for IoT simulator)

### 2. Run the Application
```bash
# Clone the repository
git clone <your-repository-url>
cd "Fertiliser System"

# Run with Maven
mvn spring-boot:run
```

Access the dashboard at:
👉 **http://localhost:8080**  
(Database console available at `http://localhost:8080/h2-console`)

### 3. Run on Mobile / Local Network
Open `http://<YOUR_LAPTOP_IP>:8080` (e.g. `http://192.168.1.2:8080`) from any phone connected to the same Wi-Fi.

### 4. Run IoT Sensor Simulation
```bash
python3 iot-simulator/pico_sensor_client.py
```

---

## 📜 REST API Endpoints
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/fertilizer/sensors/data` | Ingests real-time sensor packets from Pico 2 W |
| `GET` | `/api/fertilizer/sensors/latest` | Retrieves the latest soil telemetry reading |
| `GET` | `/api/fertilizer/sensors/history` | Fetches historical time-series logs for charts |
| `GET` | `/api/fertilizer/crops` | Lists all supported crops and nutritional thresholds |
| `GET` | `/api/fertilizer/recommend?crop={name}` | Calculates fertilizer advice for the current soil state |
| `POST` | `/api/fertilizer/evaluate?crop={name}` | Sandbox simulation for custom soil inputs |

---

## 📄 Research & Conference Publications
- **Paper Title**: *Next Gen-Smart Fertilizer Recommendation System*
- **Conference**: 2nd International Conference on Recent Advances in Technology & Management (ICRATM-2026), 29th–30th April 2026
- **Project Competition**: National Level Project Competition (NLPC-26), IETE Pune Centre
