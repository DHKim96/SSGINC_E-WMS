

function handleResponse(response, type) {
    if (response.status === 200 && response.data.data === true) {
        alert(response.data.message);
        isValid.type = true;
    }
}

function handleError(error, type) {
    if (error.response) {
        alert(error.response.data.message);
    } else {
        alert('서버와의 통신 중 문제가 발생했습니다.');
    }

    isValid.type = false;
}

const isValid = {
    password: false,
    phone: false
}

// 인증 타이머 클래스
class AuthTimer {
    constructor(duration, timerElement, onExpire) {
        this.duration = duration;
        this.timerElement = timerElement;
        this.timerId = null;
        this.onExpire = onExpire;
    }

    start() {
        let timeLeft = this.duration;
        this.timerElement.text(this.formatTime(timeLeft));

        this.timerId = setInterval(() => {
            timeLeft--;
            if (timeLeft <= 0) {
                clearInterval(this.timerId);
                this.onExpire();
            } else {
                this.timerElement.text(this.formatTime(timeLeft));
            }
        }, 1000);
    }

    stop() {
        clearInterval(this.timerId);
    }

    formatTime(seconds) {
        const minutes = Math.floor(seconds / 60);
        const remainingSeconds = seconds % 60;
        return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
    }
}


function showLoading() {
    document.getElementById("loading-overlay").style.display = "flex";
}

function hideLoading() {
    document.getElementById("loading-overlay").style.display = "none";
}


// 핸드폰 인증 관리 클래스
class PhoneAuthentication {
    constructor() {
        this.isSending = false;
        this.authManager = new AuthTimer(180, $("#phone-auth-timer > h4"), this.onTimerExpire.bind(this));

        this.phoneInput = $("input[type='tel']");
        this.phoneInputErrMsg = $("#register-input-phone > .errMsg");
        this.authInput = $("input[name='phone-authNo']");
        this.sendButton = $("#member-phone-auth");
        this.verifyButton = $("#member-phone-auth-btn");

        this.initializeEvents();
    }

    initializeEvents() {
        this.sendButton.on("click", () => this.sendAuthNum());
        this.verifyButton.on("click", () => this.verifyAuthNum());
    }

    async sendAuthNum() {
        if (this.isSending) {
            showMsg(this.phoneInputErrMsg, "error", "이미 전송되었습니다.");
            return;
        }

        const phone = this.phoneInput.val().trim();

        if (!phone) {
            showMsg(this.phoneInputErrMsg, "error", "전화번호를 입력해주세요.");
            return;
        }

        if (!Validator.isValidFormat("phone", phone)) {
            showMsg(this.phoneInputErrMsg, "error", "잘못된 전화번호 형식입니다.");
            return;
        }

        this.isSending = true;

        showLoading();

        try {
            const response = await axios.post("/api/auth/phone", {phone});
            handleResponse(response, this.phoneInputErrMsg);

            this.authManager.start(); // 타이머 시작
            $("#phone-auth-section").css("display", "flex");
            this.authInput.prop("readonly", false).val("").focus();
        } catch (error) {
            handleError(error, this.phoneInputErrMsg);
            this.isSending = false;
            isValid.phone = false;
        } finally {
            hideLoading();
        }
    }

    async verifyAuthNum() {
        const authCode = this.authInput.val().trim();

        if (!authCode) {
            showMsg(this.phoneInputErrMsg, "error", "인증번호를 입력해주세요.");
            return;
        }

        try {
            const response = await axios.post("/api/auth/verify", {key: this.phoneInput.val(), value: authCode});
            handleResponse(response, this.phoneInputErrMsg);

            this.authManager.stop();
            $("#phone-auth-section").css("display", "none");
            this.phoneInput.prop("readonly", true);
            this.authInput.prop("readonly", true);
            this.sendButton.prop("disabled", true);
            this.verifyButton.prop("disabled", true);
            isValid.phone = true;
        } catch (error) {
            handleError(error, this.phoneInputErrMsg);
            isValid.phone = false;
        }
    }

    onTimerExpire() {
        showMsg(this.phoneInputErrMsg, "error", "인증 시간이 초과되었습니다.");
        $("#phone-auth-section").css("display", "none");
    }
}

async function verifyPassword() {
    const password = document.querySelector("#password").value.trim();

    const type = "password";

    await axios.post("/api/auth/password/verify", { password })
        .then((res) => {handleResponse(res, type)})
        .catch((err) => {handleError(err, type)});
}

async function verifyPhone() {
    const phone = document.querySelector("#phone").value.trim();

    const type = "phone";

    await axios.post("/api/auth/phone/verify", { phone })
        .then((res) => {
            handleResponse(res, type);
        })
        .catch((err) => {handleError(err, type)});
}

async function verifyAuth(phone) {
    const authCode = document.querySelector("#authCode").value.trim();

    await axios.post("/api/auth/verify", {key: phone, value: authCode})
        .then((res) => handleResponse(res))
        .catch((err) => handleError(err));
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelector("#btn-password").onclick(() => verifyPassword());
    document.querySelector("#btn-phone").onclick(() => verifyPhone());
    document.querySelector("#btn-phone-auth").onclick(() => verifyAuth());
})

