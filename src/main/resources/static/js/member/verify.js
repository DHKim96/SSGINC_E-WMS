const isValid = {
    password: false,
    phone: false,
};

// 서버 응답 처리
function handleResponse(response, type) {
    const result = response?.data?.data?.result; // 안전한 접근
    if (response.status === 200 && result === true) {
        alert(response.data.message);
        isValid[type] = true;
    }
}

// 서버 에러 처리
function handleError(error, type) {
    console.error("에러 디버깅:", error);

    if (error.response) {
        alert(error.response.data.message || "서버 오류가 발생했습니다.");
    } else if (error.request) {
        alert("서버로부터 응답이 없습니다.");
    } else {
        alert("서버와의 통신 중 문제가 발생했습니다.");
    }

    isValid[type] = false;
}

// 로딩 화면 표시
function showLoading() {
    const loadingOverlay = document.getElementById("loading-overlay");
    if (loadingOverlay) {
        loadingOverlay.style.display = "flex";
    }
}

// 로딩 화면 숨김
function hideLoading() {
    const loadingOverlay = document.getElementById("loading-overlay");
    if (loadingOverlay) {
        loadingOverlay.style.display = "none";
    }
}

// 비밀번호 인증
async function verifyPassword() {
    const password = document.querySelector("#password").value.trim();
    if (!password) {
        alert("비밀번호를 입력해주세요.");
        return;
    }

    showLoading();

    try {
        const response = await axios.post("/api/auth/password/verify", { password });
        handleResponse(response, "password");
        document.querySelector("#btn-password").disabled = true;
    } catch (err) {
        handleError(err, "password");
    } finally {
        hideLoading();
    }
}

// 전화번호 인증 요청
async function verifyPhone() {
    const phone = document.querySelector("#phone").value.trim();
    if (!phone) {
        alert("전화번호를 입력해주세요.");
        return;
    }

    showLoading();

    try {
        const response = await axios.post("/api/auth/phone/verify", { phone });
        handleResponse(response, "phone");
        document.querySelector("#btn-phone").disabled = true;
        // 인증 코드 입력 필드와 버튼 활성화
        document.querySelector("#authCode").parentElement.style.display = "flex";
    } catch (err) {
        handleError(err, "phone");
    } finally {
        hideLoading();
    }
}

// 인증번호 확인
async function verifyAuth() {
    const phone = document.querySelector("#phone").value.trim();
    const authCode = document.querySelector("#authCode").value.trim();

    if (!authCode) {
        alert("인증번호를 입력해주세요.");
        return;
    }

    showLoading();

    try {
        const response = await axios.post("/api/auth/verify", { key: phone, value: authCode });
        handleResponse(response, "phone");
        document.querySelector("#btn-phone").disabled = true;
        alert("전화번호 인증이 완료되었습니다.");
        // 인증 완료 후 입력 필드 비활성화
        document.querySelector("#phone").readOnly = true;
        document.querySelector("#authCode").readOnly = true;
    } catch (err) {
        handleError(err, "phone");
    } finally {
        hideLoading();
    }
}

// 모든 인증 완료 후 이동
function proceedIfValid() {
    // 모든 인증이 완료되었는지 확인
    const allValid = Object.values(isValid).every((value) => value === true);

    if (allValid) {
        alert("모든 인증이 완료되었습니다.");
        window.location.href = "/modify"; // 다음 페이지로 이동
    } else {
        alert("모든 인증을 완료해주세요.");
    }
}

// DOMContentLoaded 이벤트로 이벤트 리스너 등록
window.onload = () => {
    document.querySelector("#btn-password").addEventListener("click", verifyPassword);
    document.querySelector("#btn-phone").addEventListener("click", verifyPhone);
    document.querySelector("#btn-phone-auth").addEventListener("click", verifyAuth);
    document.querySelector(".submit-btn").addEventListener("click", proceedIfValid);
};
