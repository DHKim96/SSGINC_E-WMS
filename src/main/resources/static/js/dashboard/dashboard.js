// ============================================================= 입출고 분석 차트 ===========================================================================

let chartInstance = null; // Chart.js 인스턴스를 저장할 변수

// 입출고 분석 차트
const ctx = document.getElementById('line-chart').getContext('2d');

// 차트 옵션
const options = {
    responsive: true,
    maintainAspectRatio: true,
    plugins: {
        legend: {
            display: false,
        },
    },
    scales: {
        x: {
            grid: {
                display: false,
            },
        },
        y: {
            grid: {
                color: 'rgba(0, 0, 0, 0.1)',
            },
            ticks: {
                stepSize: 10,
            },
        },
    },
};

// 초기 차트 생성 함수
function initializeChart(data) {
    if (chartInstance) {
        chartInstance.destroy(); // 기존 차트 삭제
    }
    chartInstance = new Chart(ctx, {
        type: 'line',
        data: data,
        options: options,
    });
}

// REST API 요청 함수 (axios 사용)
async function fetchChartData(sort, type) {
    try {

        const response = sort === "income" ? await axios.get(`/dashboard/chart/income/${type}`) : await axios.get(`/dashboard/chart/outgoing/${type}`) ;

        if (response.data.status !== 200) {
            throw new Error(response.data.message || '데이터 요청 실패');
        }

        // 데이터 포맷팅
        const chartData = {
            labels: sort === "income" ? response.data.data.map(item => item.incomeDate) : response.data.data.map(item => item.incomeDate),
            datasets: [
                {
                    label: sort === "income" ? "입고량" : "출고량",
                    data: sort === "income" ? response.data.data.map(item => parseInt(item.cumulativeSum)) : response.data.data.map(item => parseInt(item.incomeQuantity, 10)),
                    borderColor: 'rgba(74, 144, 226, 1)',
                    backgroundColor: 'rgba(74, 144, 226, 0.2)',
                    fill: true,
                    tension: 0.4,
                    borderWidth: 2,
                },
            ],
        };

        return chartData;

    } catch (error) {
        console.error('Error fetching chart data:', error);
        alert('데이터를 불러오는 중 오류가 발생했습니다.');
        return null;
    }
}

// 초기 차트 데이터 설정 및 렌더링
async function loadInitialChart() {
    const initialData = await fetchChartData('income', 'monthly');
    if (initialData) {
        initializeChart(initialData);
    }
}

// Show Select 이벤트 리스너
document.getElementById('show-select').addEventListener('change', async (event) => {
    const selectedSort = event.target.value; // 입고량/출고량
    const selectedType = document.querySelector("#short-by-select").value;

    try {
        // 데이터 fetch
        const chartData = await fetchChartData(selectedSort, selectedType);
        if (chartData) {
            initializeChart(chartData); // 차트 갱신
        }
    } catch (error) {
        console.error("Error fetching chart data:", error);
        alert("데이터를 가져오는 중 문제가 발생했습니다.");
    }
});


// Short By Select 이벤트 리스너
document.getElementById('short-by-select').addEventListener('change', async (event) => {
    const selectedType = event.target.value; // 일자별, 월별, 년도별 선택
    const selectedSort = document.querySelector("#show-select").value;

    try {
        // 서버에서 데이터 요청
        const chartData = await fetchChartData(selectedSort, selectedType);
        if (chartData) {
            initializeChart(chartData); // 차트 업데이트
        }
    } catch (error) {
        console.error("Error fetching chart data:", error);
        alert("데이터를 가져오는 중 문제가 발생했습니다.");
    }
});

// 페이지 로드 시 초기 차트 로드
loadInitialChart();

// ============================================================= 출고 지점 차트 ===========================================================================

// 출고 지정 바 차트
const ctx2 = document.getElementById('shipment-bar-chart').getContext('2d');
new Chart(ctx2, {
    type: 'bar',
    data: {
        labels: ['Miami', 'New York', 'Washington', 'California', 'Chicago'],
        datasets: [{
            label: '출고 지정',
            data: [40, 35, 30, 25, 20],
            backgroundColor: 'rgba(75, 192, 192, 0.7)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1,
        }]
    },
    options: {
        indexAxis: 'y', // Horizontal bar
        scales: {
            x: {
                beginAtZero: true,
            },
        },
        plugins: {
            legend: {
                display: false,
            },
        },
    }
});

async function requestWeather(){


    // T1H : 기온
    // RN1 : 1시간 강수량
    // REH : 습도
    // PTY : 강수형태
    // VEC : 풍향
    // WSD : 풍속
    // UUU : 동서바람성분
    // VVV : 남북바람성분

    // 이미지 :
     await axios.get("/weather")
        .then((res) => {
            console.log(res);
            renderWeatherInfo(res.data.data.response.body.items.item);
        })
        .catch((err) => {
            console.log(err);
            alert(err.data.message);
        })
}

function renderWeatherInfo(weatherData) {

    console.log(weatherData);

    const categoryMap = {
        PTY: "강수형태",
        T1H: "기온",
        REH: "습도",
        RN1: "1시간 강수량",
    };

    const iconMap = {
        sunnyDay: "/img/dashboard/weather/sunnyDay.png",
        cloudyDay: "/img/dashboard/weather/cloudyDay.png",
        grayDay: "/img/dashboard/weather/grayDay.png",
        grayNight: "/img/dashboard/weather/grayNight.png",
        overcast: "/img/dashboard/weather/overcast.png",
        rainy: "/img/dashboard/weather/rainny.png",
        snow: "/img/dashboard/weather/snow.png",
        sunnyNight: "/img/dashboard/weather/sunnyNight1.png",
    };

    let weatherIcon = "";
    let weatherDescription = "";
    const rainType = weatherData.find((item) => item.category === "PTY").obsrValue || "0";
    const temperature = weatherData.find((item) => item.category === "T1H").obsrValue || "0";
    const humidity = weatherData.find((item) => item.category === "REH").obsrValue || "0";

    // 날씨 조건에 따라 아이콘 및 설명 설정
    if (rainType === "0") {
        if (temperature > 0) {
            weatherIcon = iconMap.sunnyDay;
            weatherDescription = "맑음";
        } else if (temperature <= 0) {
            weatherIcon = iconMap.snow;
            weatherDescription = "눈";
        }
    } else if (rainType === "1") {
        weatherIcon = iconMap.rainy;
        weatherDescription = "비";
    } else if (rainType === "2" || rainType === "3") {
        weatherIcon = iconMap.snow;
        weatherDescription = "눈";
    } else {
        weatherIcon = iconMap.overcast;
        weatherDescription = "흐림";
    }

    // HTML 요소 업데이트
    document.querySelector(".weather-card img").src = weatherIcon;
    document.querySelector("#degree").innerText = temperature;
    document.querySelector(".weather-card p:nth-of-type(2)").innerText = weatherDescription;
    document.querySelector(
        ".weather-card .flex.flex-col p:nth-of-type(2) span:last-of-type"
    ).innerText = humidity;
}

document.addEventListener(('DOMContentLoaded'), () => {
    requestWeather();
})