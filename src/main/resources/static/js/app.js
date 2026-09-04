let currentCrop = 'Tomato';
let currentLang = 'hi'; // Default to Hindi
let telemetryChart = null;

const translations = {
    hi: {
        brandTitle: "स्मार्ट खाद सलाहकार प्रणाली",
        brandSub: "किसानों के लिए वास्तविक समय में मिट्टी परीक्षण और सटीक खाद की सिफारिश",
        picoStatus: "Pico 2 W कनेक्टेड",
        refreshBtn: "🔄 रीफ्रेश",
        darkTheme: "डार्क मोड",
        lightTheme: "लाइट मोड",
        step1Title: "कदम 1: अपनी फसल चुनें",
        cropTomato: "टमाटर (Tomato)",
        cropWheat: "गेहूं (Wheat)",
        cropRice: "धान / चावल (Rice)",
        cropCotton: "कपास (Cotton)",
        cropMaize: "मक्का (Maize)",
        cropSugarcane: "गन्ना (Sugarcane)",
        lblN: "नाइट्रोजन (Nitrogen)",
        lblP: "फास्फोरस (Phosphorus)",
        lblK: "पोटेशियम (Potassium)",
        lblPh: "मिट्टी का pH",
        lblTemp: "तापमान (Temperature)",
        lblHum: "नमी / आर्द्रता (Humidity)",
        heroHeading: "🌾 सुझाई गई खाद और कार्य योजना",
        heroTag: "उपयोग करने के लिए सबसे उपयुक्त खाद",
        dosagePrefix: "📦 अनुशंसित मात्रा:",
        stepsLabel: "किसान भाइयों के लिए जरूरी सलाह",
        simHeading: "🧪 मिट्टी का मान मैन्युअल रूप से जांचें",
        inN: "नाइट्रोजन (N) mg/kg",
        inP: "फास्फोरस (P) mg/kg",
        inK: "पोटेशियम (K) mg/kg",
        inPh: "मिट्टी का pH स्तर",
        inTemp: "तापमान (°C)",
        inHum: "नमी (%)",
        calcBtn: "🔍 सही खाद की गणना करें",
        presetLabel: "त्वरित मिट्टी परीक्षण नमूने:",
        preLowN: "🌱 कम नाइट्रोजन (यूरिया)",
        preAlk: "🧪 क्षारीय मिट्टी (सल्फर)",
        preBal: "✅ संतुलित मिट्टी",
        chartHeading: "📊 समय के साथ मिट्टी के पोषक तत्वों का ग्राफ",
        chartSub: "रियल-टाइम सेंसर डेटा",
        footer: "Next Gen-Smart Fertilizer Recommendation System • भारतीय कृषि और सटीक खेती के लिए समर्पित",
        cropPrefix: "फसल: ",
        statusOptimal: "✅ उत्तम (पर्याप्त)",
        statusLow: "⚠️ कम (खाद की आवश्यकता)",
        statusHigh: "⚠️ अधिक (अधिकतम)"
    },
    en: {
        brandTitle: "Smart Fertilizer Advisory System",
        brandSub: "Real-Time Soil Health Analysis & Precision Recommendations for Farmers",
        picoStatus: "Pico 2 W Connected",
        refreshBtn: "🔄 Refresh",
        darkTheme: "Dark Mode",
        lightTheme: "Light Mode",
        step1Title: "Step 1: Select Your Crop",
        cropTomato: "Tomato",
        cropWheat: "Wheat",
        cropRice: "Rice / Paddy",
        cropCotton: "Cotton",
        cropMaize: "Maize",
        cropSugarcane: "Sugarcane",
        lblN: "Nitrogen (N)",
        lblP: "Phosphorus (P)",
        lblK: "Potassium (K)",
        lblPh: "Soil pH Level",
        lblTemp: "Temperature",
        lblHum: "Moisture / Humidity",
        heroHeading: "🌾 Recommended Fertilizer & Action Plan",
        heroTag: "Best Fertilizer to Apply",
        dosagePrefix: "📦 Recommended Dose:",
        stepsLabel: "Step-by-Step Instructions for Farmer",
        simHeading: "🧪 Test Soil Reading Manually",
        inN: "Nitrogen (N) mg/kg",
        inP: "Phosphorus (P) mg/kg",
        inK: "Potassium (K) mg/kg",
        inPh: "Soil pH Level",
        inTemp: "Temperature (°C)",
        inHum: "Humidity (%)",
        calcBtn: "🔍 Calculate Best Fertilizer",
        presetLabel: "Quick Field Test Conditions:",
        preLowN: "🌱 Low Nitrogen (Urea)",
        preAlk: "🧪 Alkaline Soil (Sulfur)",
        preBal: "✅ Balanced Soil",
        chartHeading: "📊 Soil Nutrient Trend Over Time",
        chartSub: "Real-Time Field Telemetry",
        footer: "Next Gen-Smart Fertilizer Recommendation System • Dedicated to Smart Precision Farming",
        cropPrefix: "Crop: ",
        statusOptimal: "✅ Healthy (Optimal)",
        statusLow: "⚠️ Low (Needs Fertilizer)",
        statusHigh: "⚠️ High (Excess)"
    }
};

function setLanguage(lang) {
    currentLang = lang;
    localStorage.setItem('app-lang', lang);

    document.getElementById('btnLangHi').classList.toggle('active', lang === 'hi');
    document.getElementById('btnLangEn').classList.toggle('active', lang === 'en');

    const t = translations[lang];
    document.getElementById('t-brand-title').textContent = t.brandTitle;
    document.getElementById('t-brand-sub').textContent = t.brandSub;
    document.getElementById('t-pico-status').textContent = t.picoStatus;
    document.getElementById('t-refresh-btn').textContent = t.refreshBtn;
    document.getElementById('t-step1-title').textContent = t.step1Title;
    document.getElementById('t-crop-tomato').textContent = t.cropTomato;
    document.getElementById('t-crop-wheat').textContent = t.cropWheat;
    document.getElementById('t-crop-rice').textContent = t.cropRice;
    document.getElementById('t-crop-cotton').textContent = t.cropCotton;
    document.getElementById('t-crop-maize').textContent = t.cropMaize;
    document.getElementById('t-crop-sugarcane').textContent = t.cropSugarcane;
    document.getElementById('t-lbl-n').textContent = t.lblN;
    document.getElementById('t-lbl-p').textContent = t.lblP;
    document.getElementById('t-lbl-k').textContent = t.lblK;
    document.getElementById('t-lbl-ph').textContent = t.lblPh;
    document.getElementById('t-lbl-temp').textContent = t.lblTemp;
    document.getElementById('t-lbl-hum').textContent = t.lblHum;
    document.getElementById('t-hero-heading').textContent = t.heroHeading;
    document.getElementById('t-hero-tag').textContent = t.heroTag;
    document.getElementById('t-dosage-prefix').textContent = t.dosagePrefix;
    document.getElementById('t-steps-label').textContent = t.stepsLabel;
    document.getElementById('t-sim-heading').textContent = t.simHeading;
    document.getElementById('t-in-n').textContent = t.inN;
    document.getElementById('t-in-p').textContent = t.inP;
    document.getElementById('t-in-k').textContent = t.inK;
    document.getElementById('t-in-ph').textContent = t.inPh;
    document.getElementById('t-in-temp').textContent = t.inTemp;
    document.getElementById('t-in-hum').textContent = t.inHum;
    document.getElementById('t-calc-btn').textContent = t.calcBtn;
    document.getElementById('t-preset-label').textContent = t.presetLabel;
    document.getElementById('t-pre-lowN').textContent = t.preLowN;
    document.getElementById('t-pre-alk').textContent = t.preAlk;
    document.getElementById('t-pre-bal').textContent = t.preBal;
    document.getElementById('t-chart-heading').textContent = t.chartHeading;
    document.getElementById('t-chart-sub').textContent = t.chartSub;
    document.getElementById('t-footer').textContent = t.footer;

    const theme = document.documentElement.getAttribute('data-theme') || 'light';
    updateThemeButtonUI(theme);
    updateCropBadge();
    fetchLiveTelemetry();
}

function updateCropBadge() {
    const t = translations[currentLang];
    const badge = document.getElementById('activeCropBadge');
    let cropLabel = currentCrop;
    if (currentLang === 'hi') {
        if (currentCrop === 'Tomato') cropLabel = 'टमाटर (Tomato)';
        else if (currentCrop === 'Wheat') cropLabel = 'गेहूं (Wheat)';
        else if (currentCrop === 'Rice') cropLabel = 'धान (Rice)';
        else if (currentCrop === 'Cotton') cropLabel = 'कपास (Cotton)';
        else if (currentCrop === 'Maize') cropLabel = 'मक्का (Maize)';
        else if (currentCrop === 'Sugarcane') cropLabel = 'गन्ना (Sugarcane)';
    }
    badge.textContent = `${t.cropPrefix}${cropLabel}`;
}

// Theme Toggle
function initTheme() {
    const savedTheme = localStorage.getItem('app-theme') || 'light';
    document.documentElement.setAttribute('data-theme', savedTheme);
    updateThemeButtonUI(savedTheme);
}

function toggleTheme() {
    const currentTheme = document.documentElement.getAttribute('data-theme') || 'light';
    const nextTheme = currentTheme === 'light' ? 'dark' : 'light';
    document.documentElement.setAttribute('data-theme', nextTheme);
    localStorage.setItem('app-theme', nextTheme);
    updateThemeButtonUI(nextTheme);

    if (telemetryChart) {
        updateChartTheme(nextTheme);
    }
}

function updateThemeButtonUI(theme) {
    const icon = document.getElementById('themeIcon');
    const label = document.getElementById('themeLabel');
    const t = translations[currentLang];
    if (theme === 'dark') {
        icon.textContent = '☀️';
        label.textContent = t.lightTheme;
    } else {
        icon.textContent = '🌙';
        label.textContent = t.darkTheme;
    }
}

function selectCrop(cropName, element) {
    document.querySelectorAll('.crop-card').forEach(c => c.classList.remove('active'));
    if (element) {
        element.classList.add('active');
    }
    currentCrop = cropName;
    updateCropBadge();
    fetchLiveTelemetry();
}

function initChart() {
    const ctx = document.getElementById('soilTelemetryChart').getContext('2d');
    const isDark = document.documentElement.getAttribute('data-theme') === 'dark';
    const textColor = isDark ? '#94a3b8' : '#475569';
    const gridColor = isDark ? 'rgba(255, 255, 255, 0.06)' : 'rgba(0, 0, 0, 0.05)';

    telemetryChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['17:24', '17:25', '17:29', '17:40', '17:57', '18:02', 'Now (Live)'],
            datasets: [
                {
                    label: 'Nitrogen (N)',
                    data: [110, 115, 118, 122, 130, 128, 125],
                    borderColor: '#16a34a',
                    backgroundColor: 'rgba(22, 163, 74, 0.1)',
                    borderWidth: 3,
                    tension: 0.35,
                    fill: true
                },
                {
                    label: 'Phosphorus (P)',
                    data: [58, 60, 62, 64, 68, 66, 65],
                    borderColor: '#2563eb',
                    backgroundColor: 'transparent',
                    borderWidth: 3,
                    tension: 0.35
                },
                {
                    label: 'Potassium (K)',
                    data: [135, 138, 142, 145, 150, 142, 140],
                    borderColor: '#7c3aed',
                    backgroundColor: 'transparent',
                    borderWidth: 3,
                    tension: 0.35
                },
                {
                    label: 'Soil pH (x10)',
                    data: [65, 66, 69, 70, 71, 69, 68],
                    borderColor: '#d97706',
                    backgroundColor: 'transparent',
                    borderWidth: 3,
                    tension: 0.35,
                    borderDash: [6, 6]
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    labels: { color: textColor, font: { family: 'Plus Jakarta Sans', size: 13, weight: '700' } }
                }
            },
            scales: {
                x: {
                    grid: { color: gridColor },
                    ticks: { color: textColor, font: { weight: '600' } }
                },
                y: {
                    grid: { color: gridColor },
                    ticks: { color: textColor, font: { weight: '600' } }
                }
            }
        }
    });
}

function updateChartTheme(theme) {
    const isDark = theme === 'dark';
    const textColor = isDark ? '#94a3b8' : '#475569';
    const gridColor = isDark ? 'rgba(255, 255, 255, 0.06)' : 'rgba(0, 0, 0, 0.05)';

    telemetryChart.options.plugins.legend.labels.color = textColor;
    telemetryChart.options.scales.x.grid.color = gridColor;
    telemetryChart.options.scales.x.ticks.color = textColor;
    telemetryChart.options.scales.y.grid.color = gridColor;
    telemetryChart.options.scales.y.ticks.color = textColor;
    telemetryChart.update();
}

async function fetchLiveTelemetry() {
    try {
        const recRes = await fetch(`/api/fertilizer/recommend?crop=${encodeURIComponent(currentCrop)}`);
        if (recRes.ok) {
            const data = await recRes.json();
            updateUIWithRecommendation(data);
        }
    } catch (e) {
        console.warn("API request fallback", e);
    }
}

function updateUIWithRecommendation(data) {
    if (!data) return;

    const reading = data.inputReading;
    if (reading) {
        document.getElementById('valN').innerHTML = `${reading.nitrogen} <span class="sensor-box-unit">mg/kg</span>`;
        document.getElementById('valP').innerHTML = `${reading.phosphorus} <span class="sensor-box-unit">mg/kg</span>`;
        document.getElementById('valK').innerHTML = `${reading.potassium} <span class="sensor-box-unit">mg/kg</span>`;
        document.getElementById('valPH').innerHTML = `${reading.ph} <span class="sensor-box-unit">pH</span>`;
        document.getElementById('valTemp').innerHTML = `${reading.temperature} <span class="sensor-box-unit">°C</span>`;
        document.getElementById('valHumidity').innerHTML = `${reading.humidity} <span class="sensor-box-unit">%</span>`;

        // Sync inputs
        document.getElementById('inputN').value = reading.nitrogen;
        document.getElementById('inputP').value = reading.phosphorus;
        document.getElementById('inputK').value = reading.potassium;
        document.getElementById('inputPH').value = reading.ph;
        document.getElementById('inputTemp').value = reading.temperature;
        document.getElementById('inputHumidity').value = reading.humidity;
    }

    // Hero Fertilizer Banner
    let fertilizerTitle = data.primaryFertilizer || "NPK 19:19:19 Composite";
    let summaryText = data.soilConditionSummary || "Soil nutrients are healthy for this crop.";
    let dosageText = data.dosageRecommendation || "50 kg/acre maintenance application.";

    if (currentLang === 'hi') {
        if (fertilizerTitle.includes('Urea')) fertilizerTitle = "यूरिया (Urea 46% N)";
        else if (fertilizerTitle.includes('DAP')) fertilizerTitle = "डीएपी (DAP - Diammonium Phosphate)";
        else if (fertilizerTitle.includes('MOP')) fertilizerTitle = "एमओपी (MOP - Muriate of Potash)";
        else if (fertilizerTitle.includes('NPK')) fertilizerTitle = "एनपीके 19:19:19 संतुलित खाद";

        if (summaryText.includes('Deficiencies')) summaryText = "मिट्टी में पोषक तत्वों की कमी पाई गई है।";
        else if (summaryText.includes('Balanced')) summaryText = "मिट्टी में सभी पोषक तत्व संतुलित अवस्था में हैं।";
    }

    document.getElementById('primaryFertilizerName').textContent = fertilizerTitle;
    document.getElementById('recSummaryText').textContent = summaryText;
    document.getElementById('recDosageText').innerHTML = `<span>${translations[currentLang].dosagePrefix}</span> <span>${dosageText}</span>`;

    // Advice List
    const adviceList = document.getElementById('adviceListContainer');
    adviceList.innerHTML = '';
    if (data.actionableAdvice && data.actionableAdvice.length > 0) {
        data.actionableAdvice.forEach(adv => {
            let translatedAdv = adv;
            if (currentLang === 'hi') {
                if (adv.includes('Nitrogen') || adv.includes('Urea')) translatedAdv = "नाइट्रोजन युक्त खाद (यूरिया) को विभाजित मात्रा में खेत में डालें।";
                else if (adv.includes('Phosphorus') || adv.includes('DAP')) translatedAdv = "जड़ों के विकास के लिए डीएपी (DAP) खाद का प्रयोग करें।";
                else if (adv.includes('Potassium') || adv.includes('MOP')) translatedAdv = "फसल की रोग प्रतिरोधक क्षमता बढ़ाने हेतु पोटाश (MOP) डालें।";
                else if (adv.includes('sulfur') || adv.includes('alkaline')) translatedAdv = "मिट्टी अधिक क्षारीय है, pH संतुलित करने के लिए सल्फर/जिप्सम डालें।";
                else if (adv.includes('lime') || adv.includes('Acidic')) translatedAdv = "मिट्टी अम्लीय है, pH सुधारने के लिए कृषि चूना (Lime) डालें।";
            }
            const div = document.createElement('div');
            div.className = 'advice-card';
            div.innerHTML = `<span class="advice-card-icon">📌</span><span>${translatedAdv}</span>`;
            adviceList.appendChild(div);
        });
    }

    // Update Badges
    if (data.nutrientStatuses) {
        data.nutrientStatuses.forEach(ns => {
            if (ns.parameter.includes("Nitrogen")) updateBadge('statusN', ns.status);
            if (ns.parameter.includes("Phosphorus")) updateBadge('statusP', ns.status);
            if (ns.parameter.includes("Potassium")) updateBadge('statusK', ns.status);
            if (ns.parameter.includes("pH")) updateBadge('statusPH', ns.status);
        });
    }
}

function updateBadge(elementId, status) {
    const el = document.getElementById(elementId);
    if (!el) return;
    const t = translations[currentLang];
    el.className = 'sensor-box-status';
    if (status.includes('OPTIMAL')) {
        el.classList.add('status-good');
        el.innerHTML = t.statusOptimal;
    } else if (status.includes('LOW')) {
        el.classList.add('status-alert');
        el.innerHTML = t.statusLow;
    } else {
        el.classList.add('status-warn');
        el.innerHTML = t.statusHigh;
    }
}

async function submitSensorReading(e) {
    e.preventDefault();
    const btn = document.getElementById('submitBtn');
    btn.innerHTML = `<span>⏳ ${currentLang === 'hi' ? 'गणना हो रही है...' : 'Calculating...'}</span>`;
    btn.disabled = true;

    const payload = {
        nitrogen: parseFloat(document.getElementById('inputN').value),
        phosphorus: parseFloat(document.getElementById('inputP').value),
        potassium: parseFloat(document.getElementById('inputK').value),
        ph: parseFloat(document.getElementById('inputPH').value),
        temperature: parseFloat(document.getElementById('inputTemp').value),
        humidity: parseFloat(document.getElementById('inputHumidity').value)
    };

    try {
        const response = await fetch(`/api/fertilizer/sensors/data`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (response.ok) {
            const evalRes = await fetch(`/api/fertilizer/evaluate?crop=${encodeURIComponent(currentCrop)}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
            if (evalRes.ok) {
                const evalData = await evalRes.json();
                updateUIWithRecommendation(evalData);
            }

            if (telemetryChart) {
                const datasets = telemetryChart.data.datasets;
                datasets[0].data.push(payload.nitrogen);
                datasets[1].data.push(payload.phosphorus);
                datasets[2].data.push(payload.potassium);
                datasets[3].data.push(payload.ph * 10);
                telemetryChart.data.labels.push(currentLang === 'hi' ? 'अभी' : 'Now');
                if (datasets[0].data.length > 8) {
                    datasets.forEach(d => d.data.shift());
                    telemetryChart.data.labels.shift();
                }
                telemetryChart.update();
            }
        }
    } catch (err) {
        console.error("Submission error", err);
    } finally {
        btn.innerHTML = `<span>${translations[currentLang].calcBtn}</span>`;
        btn.disabled = false;
    }
}

function loadPreset(type) {
    if (type === 'lowN') {
        document.getElementById('inputN').value = 45;
        document.getElementById('inputP').value = 75;
        document.getElementById('inputK').value = 180;
        document.getElementById('inputPH').value = 6.5;
    } else if (type === 'alkaline') {
        document.getElementById('inputN').value = 150;
        document.getElementById('inputP').value = 70;
        document.getElementById('inputK').value = 170;
        document.getElementById('inputPH').value = 8.2;
    } else if (type === 'balanced') {
        document.getElementById('inputN').value = 160;
        document.getElementById('inputP').value = 75;
        document.getElementById('inputK').value = 190;
        document.getElementById('inputPH').value = 6.6;
    }
    document.getElementById('sensorInputForm').dispatchEvent(new Event('submit'));
}

document.addEventListener('DOMContentLoaded', () => {
    const savedLang = localStorage.getItem('app-lang') || 'hi';
    initTheme();
    initChart();
    setLanguage(savedLang);
});
