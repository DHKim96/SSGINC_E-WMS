// ============================================================= 공통 유틸리티 함수 ===========================================================================

// // 로딩 스피너
// function showLoadingSpinner() {
//     const spinner = document.getElementById('loading-spinner');
//     if (spinner) spinner.style.display = 'block';
// }
//
// function hideLoadingSpinner() {
//     const spinner = document.getElementById('loading-spinner');
//     if (spinner) spinner.style.display = 'none';
// }

// 날짜 옵션 갱신 함수
function updateDayOptions(year, month) {
    const daySelect = document.getElementById('day-select');

    // 연도와 월이 선택되지 않은 경우 초기화
    if (!year || !month) {
        daySelect.innerHTML = `<option value="">전체</option>`;
        return;
    }

    // 해당 월의 마지막 날짜 계산
    const daysInMonth = new Date(year, month, 0).getDate();
    let dayOptions = `<option value="">전체</option>`;
    for (let day = 1; day <= daysInMonth; day++) {
        const dayValue = day < 10 ? `0${day}` : day; // 01, 02 형식 유지
        dayOptions += `<option value="${dayValue}">${day}일</option>`;
    }

    daySelect.innerHTML = dayOptions;
}

// ============================================================= 입출고 분석 차트 ===========================================================================

let lineChartInstance = null;

async function fetchChartData(sort, type) {
    try {
        // showLoadingSpinner();
        const response = await axios.get(`/dashboard/chart/${sort}/${type}`);
        // hideLoadingSpinner();

        console.log(response);

        if (response.data.status !== 200) {
            throw new Error(response.data.message || '데이터 요청 실패');
        }

        return {
            type: type,
            labels: response.data.data.map((item) => sort === 'income' ? item.incomeDate : item.outgoingDate),
            datasets: [
                {
                    label: sort === 'income' ? '입고량' : '출고량',
                    data: response.data.data.map((item) => sort === 'income' ? parseInt(item.incomeQuantity || 0, 10) : parseInt(item.outgoingQuantity || 0, 10)),
                    borderColor: 'rgba(74, 144, 226, 1)',
                    backgroundColor: 'rgba(74, 144, 226, 0.2)',
                    fill: true,
                    tension: 0.4,
                    borderWidth: 2,
                },
            ],
        };
    } catch (error) {
        // hideLoadingSpinner();
        console.error('Error fetching chart data:', error);
        alert(error.response.data.message || '데이터를 불러오는 중 오류가 발생했습니다.');
        return null;
    }
}

function initializeLineChart(data) {
    if (lineChartInstance) {
        lineChartInstance.destroy();
    }
    lineChartInstance = new Chart(document.getElementById('line-chart').getContext('2d'), {
        type: 'line',
        data: data,
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                x: { grid: { display: false } },
                y: { grid: { color: 'rgba(0, 0, 0, 0.1)' }, ticks: { stepSize: data.type === "daily" ? 50 : data.type === "monthly" ? 500 : 5000 }, beginAtZero: true },
            },
        },
    });
}

async function loadInitialChart() {

    const initialData = await fetchChartData('income', 'daily');
    if (initialData) {
        initializeLineChart(initialData);
    }
}

// ============================================================= 최다 출고 지점 차트 ===========================================================================

let outgoingChartInstance = null;

async function fetchTopOutgoingBranches(year, month, day) {
    try {
        // showLoadingSpinner();
        const response = await axios.get('/dashboard/chart/top-outgoing-branches', {
            params: { year, month, day },
        });
        // hideLoadingSpinner();

        if (response.data.status !== 200) {
            throw new Error(response.data.message || '데이터 요청 실패');
        }

        return {
            labels: response.data.data.map((item) => item.branchName),
            datasets: [
                {
                    label: '출고량',
                    data: response.data.data.map((item) => parseInt(item.outgoingQuantity, 10)),
                    backgroundColor: 'rgba(75, 192, 192, 0.7)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                },
            ],
        };
    } catch (error) {
        // hideLoadingSpinner();
        console.error('Error fetching outgoing branches data:', error);
        alert(error.response.data.message || '데이터를 불러오는 중 오류가 발생했습니다.');
        return null;
    }
}

function initializeOutgoingChart(data) {
    if (outgoingChartInstance) {
        outgoingChartInstance.destroy();
    }
    outgoingChartInstance = new Chart(document.getElementById('shipment-bar-chart').getContext('2d'), {
        type: 'bar',
        data: data,
        options: {
            responsive: true,
            scales: {
                x: { grid: { display: false } },
                y: { beginAtZero: true, grid: { color: 'rgba(0, 0, 0, 0.1)' } },
            },
            plugins: { legend: { display: false } },
        },
    });
}

async function loadInitialOutgoingChart() {
    const now = new Date();
    const year = now.getFullYear();
    // const month = String(now.getMonth() + 1).padStart(2, '0'); // 현재 월
    const initialData = await fetchTopOutgoingBranches(year, null, null);
    if (initialData) {
        initializeOutgoingChart(initialData);
    }
}


// ============================================================= 날씨 ===========================================================================
async function requestWeather() {
    try {
        const response = await axios.get('/weather');
        console.log(response);
        renderWeatherInfo(response.data.data.response.body.items.item);
    } catch (err) {
        console.error(err);
        alert(error.response.data.message || '날씨 데이터를 불러오는 중 오류가 발생했습니다.');
    }
}

function renderWeatherInfo(weatherData) {
    const iconMap = {
        sunnyDay: '/img/dashboard/weather/sunnyDay.png',
        cloudyDay: '/img/dashboard/weather/cloudyDay.png',
        grayDay: '/img/dashboard/weather/grayDay.png',
        grayNight: '/img/dashboard/weather/grayNight.png',
        overcast: '/img/dashboard/weather/overcast.png',
        rainy: '/img/dashboard/weather/rainny.png',
        snow: '/img/dashboard/weather/snow.png',
        sunnyNight: '/img/dashboard/weather/sunnyNight1.png',
    };

    let weatherIcon = '';
    let weatherDescription = '';
    const rainType = weatherData.find(item => item.category === 'PTY')?.obsrValue || '0';
    const temperature = weatherData.find(item => item.category === 'T1H')?.obsrValue || '0';
    const humidity = weatherData.find(item => item.category === 'REH')?.obsrValue || '0';

    const weatherConditions = {
        "0": { icon: iconMap.sunnyDay, description: "맑음" },
        "1": { icon: iconMap.rainy, description: "비" },
        "2": { icon: iconMap.snow, description: "눈" },
        "3": { icon: iconMap.snow, description: "눈" },
    };

    const weather = weatherConditions[rainType] || { icon: iconMap.overcast, description: "흐림" };
    weatherIcon = weather.icon;
    weatherDescription = weather.description;

    document.querySelector('.weather-card img').src = weatherIcon;
    document.querySelector('#degree').innerText = Math.round(parseInt(temperature));
    document.querySelector('#description').innerText = weatherDescription;
    document.querySelector('#humidity').innerText = humidity;
}


// ============================================================= 출고 배송 현황 ===========================================================================





// ============================================================= 초기화 ===========================================================================

document.addEventListener('DOMContentLoaded', async () => {
    // 차트 초기화
    await loadInitialChart();
    await loadInitialOutgoingChart();
    await requestWeather();

    // 날짜 선택 이벤트 리스너
    document.getElementById('year-select').addEventListener('change', () => {
        const year = document.getElementById('year-select').value;
        const month = document.getElementById('month-select').value;
        updateDayOptions(year, month);
    });

    document.getElementById('month-select').addEventListener('change', () => {
        const year = document.getElementById('year-select').value;
        const month = document.getElementById('month-select').value;
        updateDayOptions(year, month);
    });

    // 최다 출고 지점 차트 조회 버튼 이벤트 리스너
    document.getElementById('fetch-branches-btn').addEventListener('click', async () => {
        const year = document.getElementById('year-select').value;
        const month = document.getElementById('month-select').value || null;
        const day = document.getElementById('day-select').value || null;

        const chartData = await fetchTopOutgoingBranches(year, month, day);
        if (chartData) {
            initializeOutgoingChart(chartData);
        }
    });

    // 입출고 분석 차트 갱신 이벤트 리스너
    document.getElementById('show-select').addEventListener('change', async () => {
        let sort = document.querySelector('#show-select').value;
        let type = document.querySelector('#short-by-select').value;
        let data = await fetchChartData(sort, type);
        initializeLineChart(data);
    });
    document.getElementById('short-by-select').addEventListener('change', async () => {
        let sort = document.querySelector('#show-select').value;
        let type = document.querySelector('#short-by-select').value;
        let data = await fetchChartData(sort, type);
        initializeLineChart(data);
    });
});
