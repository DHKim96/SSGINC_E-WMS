/**
 * 서버 응답 메시지를 지정된 HTML 요소에 표시.
 * @param {Object} response - 서버 응답 객체.
 */
function handleResponse(response) {
    if (response.status === 200) {
        alert(response.data.message);
    }
}

/**
 * 서버 에러 메시지를 지정된 HTML 요소에 표시.
 * @param {Object} error - 에러 객체.
 */
function handleError(error) {
    if (error.response) {
        alert(error.response.data.message);
    } else {
        alert('서버와의 통신 중 문제가 발생했습니다.');
    }
}

// 모달 열기
function openModal(modalId) {
    document.getElementById('modal-overlay').style.display = 'flex';
    document.getElementById(modalId).style.display = 'block';
}

// 모달 닫기
function closeModal() {
    document.getElementById('modal-overlay').style.display = 'none';
    document.querySelectorAll('.modal').forEach(modal => modal.style.display = 'none');
}

function showLoading() {
    document.getElementById("loading-overlay").style.display = "flex";
}

function hideLoading() {
    document.getElementById("loading-overlay").style.display = "none";
}

// 아이디 찾기
async function findId() {
    const email = document.getElementById('id-email').value.trim();

    if (!email) {
        alert('이메일을 입력해주세요');
        return;
    }

    showLoading();

    await axios.get("api/auth/id/find", {
            params: { email }
        })
        .then((response) => {
            handleResponse(response);
        })
        .catch((error) => {
            handleError(error);
        })
        .finally(() => {
            hideLoading();
            closeModal();
        });
}


// 비밀번호 찾기
async function findPassword() {
    const id = document.getElementById('pw-id').value.trim();
    const email = document.getElementById('pw-email').value.trim();

    if (!id) {
        alert('아이디를 입력해주세요');
        return;
    }

    if (!email) {
        alert('이메일을 입력해주세요');
        return;
    }

    showLoading();

    await axios.post("api/auth/password/find", {id: id, email: email})
        .then((response) => {
            handleResponse(response);
        })
        .catch((error) => {
            handleError(error);
        })
        .finally(() => {
            hideLoading();
            closeModal();
        });
}
