


// ================================================ 공통 유틸 ================================================

function showMsg(element, type, message) {
    if (type === "success") {
        element.html(`<p>${message}</p>`).css('color', 'green');
    } else if (type === "error") {
        element.html(`<p>${message}</p>`);
    }
}


// ================================================ 아이디 검증 ===============================================

function validateId() {
    const id = $("input[name='id']").val().trim();
    const idErrMsg = $("#register-input-id > .errMsg");
    const idReg = /^[A-Za-z0-9]{10,20}/;

    if (id == ""){
        showMsg(idErrMsg, "error", "아이디를 입력해주세요.");
    } else if (idReg.test(id)){ // 유효성 검증 성공
        // 아이디 체크
        if (!checkId()) {
            // @@@@@@@@@@@@@@@@@@@250108
        }
    } else if (id.length < 10) { // 유효성 검증 실패
        showMsg(idErrMsg, "error", "아이디는 영문, 숫자 포함 10글자 이상입니다.")
    } else if (id.length > 20) {
        showMsg(idErrMsg, "error", "아이디는 영문, 숫자 포함 20글자 미만입니다.")
    }
}

function checkId() {
    axios
        .get('/member/registration/checkId', {id : this.id})
        .then((response) => {
            if(response.data) {
                return true;
            } else {
                return false;
            }
        })
        .catch((error) => {
            console.log(error);
            return false;
        })
}


// ================================================ 이메일 검증 ===============================================

// 이메일 검증 유틸리티 클래스
class EmailValidator {
    static isValidFormat(email) {
        const reg = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return reg.test(email);
    }

    static async isUnique(email) {
        try {
            const response = await axios.get('/member/registration/checkEmail', {
                params: { email },
            });
            return response.data.isValid;
        } catch (error) {
            console.error("Email uniqueness check failed:", error);
            return false;
        }
    }
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

// 이메일 인증 관리 클래스
class EmailAuthentication {
    constructor() {
        this.isSending = false;
        this.authNo = "";
        this.timer = null;

        this.emailInput = $("input[type='email']");
        this.emailInputErrMsg = $("#register-input-email > .errMsg");
        this.authNoInputErrMsg = $("#email-auth-section > .errMsg");
        this.timerElement = $("#email-auth-timer > h4");
        this.authInput = $("input[name='email-authNo']");
        this.sendButton = $("#member-email-auth");
        this.verifyButton = $("#member-email-auth-btn");

        this.initializeEvents();
    }

    initializeEvents() {
        this.sendButton.on('click', () => this.sendAuthNum());
        this.verifyButton.on('click', () => this.verifyAuthNum());
    }

    async sendAuthNum() {
        if (this.isSending) return;

        const email = this.emailInput.val().trim();
        if (!email) {
            this.showError(this.emailInputErrMsg, "이메일을 입력해주세요.");
            return;
        }

        if (!EmailValidator.isValidFormat(email)) {
            this.showError(this.emailInputErrMsg, "잘못된 이메일 형식입니다.");
            return;
        }

        if ((await EmailValidator.isUnique(email))) {
            this.showError(this.emailInputErrMsg, "이미 가입된 이메일입니다.");
            return;
        }

        this.isSending = true;

        this.authNo = Math.floor(Math.random() * 100000).toString().padStart(6, '0');
        
    
        try {
            alert("인증번호가 발송되었습니다.");
            this.startTimer(180);
            $("#email-auth-section").css('display', 'flex');
            
            await axios.post('/member/registration/authEmail', {
                email: email,
                authNo: this.authNo,
            });
        
            
            this.authInput.prop('readonly', false).val('').focus();
        } catch (error) {
            console.error("Error sending auth number:", error);
            this.showError(this.authNoInputErrMsg, "인증번호 발송에 실패했습니다. 다시 시도해주세요.");
            this.isSending = false;
        }
    }

    startTimer(duration) {
        if (this.timer) this.timer.stop();

        this.timer = new AuthTimer(duration, this.timerElement, () => {
            this.showError(this.authNoInputErrMsg, "인증 시간이 초과되었습니다.");
            this.authInput.prop('readonly', true);
            this.verifyButton.prop('disabled', true);
        });
        this.timer.start();
    }

    verifyAuthNum() {
        const inputAuthNo = this.authInput.val().trim();
        if (!inputAuthNo) {
            this.showError("인증번호를 입력해주세요.");
            return;
        }

        if (inputAuthNo === this.authNo) {
            alert("이메일 인증이 완료되었습니다.");
            this.timer.stop();
            $("#email-auth-section").css('display', 'none');

            this.emailInput.prop('readonly', true); // 이메일 입력 필드 비활성화
            this.authInput.prop('readonly', true); // 인증번호 입력 필드 비활성화
            this.sendButton.prop('disabled', true); // 인증번호 발송 버튼 비활성화
            this.verifyButton.prop('disabled', true); // 인증번호 확인 버튼 비활성화

            this.showSuccess(this.emailInputErrMsg, "이메일 인증이 완료됐습니다.");

        } else {
            this.showError(this.authNoInputErrMsg, "인증번호가 일치하지 않습니다.");
        }
    }

    showError(element, message) {
        element.html(`<p>${message}</p>`);
    }

    showSuccess(element, message) {
        element.html(`<p>${message}</p>`).css('color', 'green');
    }
}

// DOMContentLoaded 이벤트
$(document).ready(() => {
    new EmailAuthentication();
});
