// 최종 검증을 위한 플래그
const isValid = {
    id: false,
    pw: false,
    name: false,
    email: false,
    confirmPw: false,
    phone: false,
    addr: false,
    birth: false
};


// ================================================ 공통 유틸 ================================================

/**
 * 서버 응답 메시지를 지정된 HTML 요소에 표시.
 * @param {Object} response - 서버 응답 객체.
 * @param {jQuery} element - 메시지를 표시할 대상 HTML 요소.
 */
function handleResponse(response, element) {
    if (response.status === 200) {
        showMsg(element, "success", response.data.message);
    }
}

/**
 * 서버 에러 메시지를 지정된 HTML 요소에 표시.
 * @param {Object} error - 에러 객체.
 * @param {jQuery} element - 메시지를 표시할 대상 HTML 요소.
 */
function handleError(error, element) {
    if (error.response) {
        showMsg(element, "error", error.response.data.message);
    } else {
        alert('서버와의 통신 중 문제가 발생했습니다.');
    }
}

// 메시지 유틸리티
function showMsg(element, type, message) {
    element.html(`<p>${message}</p>`).css('color', type === "success" ? 'green' : 'red');
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

// 검증 유틸리티 클래스
class Validator {
    static isValidFormat(type, value) {
        const patterns = {
            email: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            phone: /^01[0-9](-\d{3,4})-\d{4}$/,
        };

        if (!patterns[type]) {
            throw new Error(`Unsupported validation type: ${type}`);
        }
        const regex = patterns[type];
        console.log("TYPE : " + type + "VALUE : " + value);
        console.log("전화번호 검증 결과 : " + regex.test(value));
        return regex.test(value); // 정규식 검증 결과 반환
    }
}

function showLoading() {
    document.getElementById("loading-overlay").style.display = "flex";
}

function hideLoading() {
    document.getElementById("loading-overlay").style.display = "none";
}

// ================================================ 아이디 검증 ===============================================

async function validateId() {
    const id = $("input[name='memberId']").val().trim();
    const idErrMsg = $("#register-input-id > .errMsg");
    const idReg = /^[a-z0-9]{4,20}$/;

    if (!id) {
        showMsg(idErrMsg, "error", "아이디를 입력해주세요.");
        isValid.id = false;
    } else if (idReg.test(id)) { // 유효성 검증 성공

        const isUniqueId = await axios
            .get('/registration/checkId', {
                params: {id: id}
            })
            .then((response) => {
                return response.data;
            })
            .catch((error) => {
                console.log(error);
                return false;
            });

        if (isUniqueId) {
            showMsg(idErrMsg, "success", "사용 가능한 아이디입니다.");
            isValid.id = true;
        } else {
            showMsg(idErrMsg, "error", "이미 사용 중인 아이디입니다.");
            isValid.id = false;
        }

    } else if (id.length < 4) { // 유효성 검증 실패
        showMsg(idErrMsg, "error", "아이디는 영문(소문자), 숫자 포함 4글자 이상입니다.");
        isValid.id = false;
    } else if (id.length > 20) {
        showMsg(idErrMsg, "error", "아이디는 영문(소문자), 숫자 포함 20글자 미만입니다.");
        isValid.id = false;
    } else {
        showMsg(idErrMsg, "error", "아이디는 영문(소문자)과 숫자로만 구성되어야 합니다.");
        isValid.id = false;
    }
}

// ================================================ 이메일 검증 ===============================================

class EmailAuthentication {
    constructor() {
        this.isSending = false;
        this.authManager = new AuthTimer(180, $("#email-auth-timer > h4"), this.onTimerExpire.bind(this));

        this.emailInput = $("input[type='email']");
        this.emailInputErrMsg = $("#register-input-email > .errMsg");
        this.authInput = $("input[name='email-authNo']");
        this.sendButton = $("#member-email-auth");
        this.verifyButton = $("#member-email-auth-btn");

        this.initializeEvents();
    }

    initializeEvents() {
        this.sendButton.on("click", () => this.sendAuthNum());
        this.verifyButton.on("click", () => this.verifyAuthNum());
    }

    async sendAuthNum() {
        if (this.isSending) {
            showMsg(this.emailInputErrMsg, "error", "이미 전송되었습니다.");
            return;
        }

        const email = this.emailInput.val().trim();

        if (!email) {
            showMsg(this.emailInputErrMsg, "error", "이메일을 입력해주세요.");
            return;
        }

        if (!Validator.isValidFormat("email", email)) {
            showMsg(this.emailInputErrMsg, "error", "잘못된 이메일 형식입니다.");
            return;
        }

        this.isSending = true;

        try {

            showLoading();

            const response = await axios.post("/api/auth/email", { email });

            handleResponse(response, this.emailInputErrMsg);

            this.authManager.start(); // 타이머 시작
            $("#email-auth-section").css("display", "flex");
            this.authInput.prop("readonly", false).val("").focus();
        } catch (error) {
            handleError(error, this.emailInputErrMsg);
            this.isSending = false;
            isValid.email = false;
        } finally {
            hideLoading();
        }
    }

    async verifyAuthNum() {
        const authCode = this.authInput.val().trim();

        if (!authCode) {
            showMsg(this.emailInputErrMsg, "error", "인증번호를 입력해주세요.");
            isValid.email = false;
            return;
        }

        try {
            const response = await axios.post("/api/auth/verify", {key: this.emailInput.val(), value: authCode});
            handleResponse(response, this.emailInputErrMsg);

            this.authManager.stop();
            $("#email-auth-section").css("display", "none");
            this.emailInput.prop("readonly", true);
            this.authInput.prop("readonly", true);
            this.sendButton.prop("disabled", true);
            this.verifyButton.prop("disabled", true);
            isValid.email = true;
        } catch (error) {
            isValid.email = false;
            handleError(error, this.emailInputErrMsg);
        }
    }

    onTimerExpire() {
        showMsg(this.emailInputErrMsg, "error", "인증 시간이 초과되었습니다.");
        $("#email-auth-section").css("display", "none");
    }
}

// ================================================ 비밀번호 검증 ===============================================


function validatePw() {
    const password = $("input[name='memberPw']").val().trim();
    const pwErrMsg = $("#register-input-pw > .errMsg");
    const pwReg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;

    if (password === "") {
        showMsg(pwErrMsg, "error", "비밀번호를 입력해주세요.");
        isValid.pw = false;
    } else if (pwReg.test(password)) {
        showMsg(pwErrMsg, "success", "사용 가능한 비밀번호입니다.");
        isValid.pw = true;
    } else {
        showMsg(pwErrMsg, "error", "비밀번호는 영문, 숫자, !@#$%^&* 중 하나를 포함하여<br>8~20자로 구성되어야 합니다.");
        isValid.pw = false;
    }
}

function confirmPw() {
    const confirmPw = $("#confirm-pw").val().trim();
    const confirmPwErrMsg = $("#register-input-confirm-pw > .errMsg");
    const password = $("input[name='memberPw']").val().trim();

    if (confirmPw === "") {
        showMsg(confirmPwErrMsg, "error", "비밀번호를 다시 입력해주세요.");
        isValid.confirmPw = false;
    } else if (confirmPw === password) {
        showMsg(confirmPwErrMsg, "success", "비밀번호가 일치합니다.");
        isValid.confirmPw = true;
    } else {
        showMsg(confirmPwErrMsg, "error", "비밀번호가 일치하지 않습니다.");
        isValid.confirmPw = false;
    }
}

// ================================================ 이름 검증 ===============================================

function validateName() {
    const name = $("input[name='memberName']").val().trim();
    const nameMsg = $("#register-input-name > .errMsg");
    const nameReg = /^[a-zA-Z가-힣\s'-]{2,30}$/;

    if (!name) {
        showMsg(nameMsg, "error", "이름을 입력해주세요.");
        isValid.name = false;
    } else if (nameReg.test(name)) {
        nameMsg.html("");
        isValid.name = true;
    } else {
        showMsg(nameMsg, "error", "적절하지 않은 형식입니다.");
        isValid.name = false;
    }
}


// ================================================ 전화번호 검증 ===============================================


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


// ================================================ 주소 검증 ===============================================


class AddressValidation {

    constructor() {
        this.searchBtn = $("#addr-search-btn");
        this.initializeEvents();
    }

    initializeEvents() {
        this.searchBtn.on('click', () => this.daumPostCode());
    }

    daumPostCode() {

        console.log("daum 클릭");

        new daum.Postcode({
            oncomplete: (data) => {
                // 팝업에서 검색결과 항목을 클릭 시 실행할 코드를 작성하는 부분
                // 각 주소의 노출 규칙에 따라 주소 조합
                // 내려오는 변수가 값이 없을 경우 공백값을 가지므로 이를 참고하여 분기
                let addr = ""; // 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져 옴
                if (data.userSelectedType === 'R') { // 도로명주소 선택 시
                    addr = data.roadAddress;
                } else { // 지번 주소(J) 선택 시
                    addr = data.jibunAddress;
                }


                if (addr === "") {
                    return;
                }

                this.drawAddrInput();

                // 우편번호와 주소 정보를 해당 필드에 넣음
                $("#sample6_postcode").val(data.zonecode);
                $("#sample6_address").val(addr);

                // 커서를 상세주소 필드로 이동
                $("#sample6_detailAddress").focus();

                // 주소와 상세주소 합치기
                $("#totalAddress").val(addr + " " + $("#sample6_detailAddress").val());

                $("#sample6_detailAddress").on('blur', () => this.validateAddr());
            }
        }).open();
    }

    drawAddrInput() {
        // 버튼 없애고 새로운 input 창들 생성 후 위 메소드의 결과값 띄우기
        this.searchBtn.css('display', 'none');

        const addressSection = document.querySelector("#register-input-addr");

        // 기존 searchResult 제거 후 생성
        let searchResult = document.getElementById("address-search-result");
        if (!searchResult) {
            searchResult = document.createElement('div');
            addressSection.appendChild(searchResult);
            searchResult.setAttribute("id", "address-search-result");
            searchResult.setAttribute("class", "flex flex-col");

            searchResult.innerHTML = `
                <input type="text" class="register-input" id="sample6_postcode" name="memberPost" style="width: 160px;" placeholder="우편번호" readonly required>
                <input type="text" class="register-input" id="sample6_address" placeholder="주소" readonly required>
                <input type="text" class="register-input" id="sample6_detailAddress" placeholder="상세 주소를 입력해주세요" required>
                <div class="errMsg"></div>
            `;
        }

        // hidden input 생성
        let totalAddress = document.getElementById("totalAddress");
        if (!totalAddress) {
            totalAddress = document.createElement('input');
            searchResult.appendChild(totalAddress);
            totalAddress.setAttribute("type", "hidden");
            totalAddress.setAttribute("name", "memberAddr");
            totalAddress.setAttribute("id", "totalAddress");
        }

        // 다시 검색 버튼 생성
        let researchBth = document.getElementById("addr-research-btn");
        if (!researchBth) {
            researchBth = document.createElement('button');
            addressSection.appendChild(researchBth);
            researchBth.setAttribute("id", "addr-research-btn");
            researchBth.setAttribute("class", "btn");
            researchBth.textContent = "다시 검색";

            // 버튼 클릭 시 daumPostCode() 호출
            researchBth.addEventListener('click', () => {
                this.daumPostCode();
            });
        }
    }

    validateAddr() {
        const detailAddr = $("#sample6_detailAddress").val().trim();
        const addrMsg = $("#address-search-result > .errMsg");

        if (!detailAddr) {
            showMsg(addrMsg, "error", "상세 주소를 입력해주세요");
            isValid.addr = false;
        } else {
            addrMsg.html("");
            isValid.addr = true;
        }
    }
}


// ================================================ 생년월일 검증 ===============================================

function validateBirth() {
    const birthInput = $("input[type='date']");
    const birth = birthInput.val();
    const birthMsg = $("#register-input-birth > .errMsg")
    const birthReg = /^\d{4}-\d{2}-\d{2}$/;

    if (!birthInput.val()) {
        showMsg(birthMsg, "error", "생년월일을 입력해주세요.");
        isValid.birth = false;
    } else if (!birthReg.test(birth)) {
        showMsg(birthMsg, "error", "올바른 날짜 형식을 입력해주세요.");
        isValid.birth = false;
    } else if (birth > birthInput.attr("max") || birth < birthInput.attr("min")) {
        showMsg(birthMsg, "error", "올바른 범위가 아닙니다.");
        isValid.birth = false;
    } else {
        birthMsg.html(""); // 메시지 초기화
        isValid.birth = true;
    }
}



// =============================================== 이벤트 리스너 ================================================

// DOMContentLoaded 이벤트
$(document).ready(() => {

    new EmailAuthentication();
    new PhoneAuthentication();
    new AddressValidation();

    $("#btn-check-id").on('click', () => validateId());
    $("input[name='memberName']").on('blur', () => validateName());
    $("input[name='memberPw']").on('keyup', () => validatePw());
    $("#confirm-pw").on('keyup', () => confirmPw());
    $("input[type='date']").on('blur', () => validateBirth());

    $("#registration-form-btn").on('click', (e) => {
        e.preventDefault(); // 기존 폼 제출 동작 방지용

        // 잘못된 항목 찾기
        const invalidFields = Object.keys(isValid).filter(key => !isValid[key]);

        if (invalidFields.length === 0) {
            // 모든 값이 유효한 경우
            const nameField = $("input[name='memberName']");
            const addressField = $("input[name='memberAddr']");

            // 이름과 주소의 공백을 언더바로 변환
            if (nameField.val()) {
                nameField.val(nameField.val().replace(/\s+/g, '_'));
            }
            if (addressField.val()) {
                addressField.val(addressField.val().replace(/\s+/g, '_'));
            }

            $("#registration-form").submit();
        } else {
            // 잘못된 항목 알림
            const fieldNames = {
                id: "아이디",
                pw: "비밀번호",
                email: "이메일",
                addr: "주소",
                birth: "생년월일",
                confirmPw: "비밀번호 확인",
                name: "이름"
            };

            const invalidFieldNames = invalidFields.map(field => fieldNames[field]);
            alert(`다음 항목을 확인해주세요: ${invalidFieldNames.join(', ')}`);
        }
    });
});
