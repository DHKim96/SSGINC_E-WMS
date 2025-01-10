
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

    // 아이디 찾기
    async function findId() {
        const email = document.getElementById('id-email').value.trim();
        if (!email) {
            alert('이메일을 입력해주세요');
            return;
        }

        const isUniqueEmail = await axios.get("/member/registration/checkEmail", {params: {email: email}}).data;
        
        console.log(isUniqueEmail);
        
        if (!isUniqueEmail) {
            alert('입력한 이메일로 아이디가 전송되었습니다.');
            
            closeModal();

            await axios.get(("/member/login/findId"), {
                params: {email: email}
            })
            .then((response) => {
                if (!response.data) {
                    console.log("검색 성공");
                } else {
                    console.log("검색 실패");
                }
            })
            .catch((error) => {
                console.log(error);
            })
            

        } else {
            alert('가입하지 않은 이메일입니다.');
        }
    }


    // 비밀번호 찾기
   async function findPassword() {
        const id = document.getElementById('pw-id').value.trim();
        if (!id) {
            alert('아이디를 입력해주세요');
            return;
        }

        const isUniqueId = await axios.get("/member/registration/checkId", {params: {id: id}}).data;
        
        if (!isUniqueId) {
            alert('가입 시 입력하신 이메일로 임시비밀번호가 전송되었습니다.');
            
            closeModal();
            
            await axios.get(("/member/login/findPw"))
            .then((response) => {
                if (!response.data) {
                    console.log("성공");
                } else {
                    console.log("실패");
                }
            })
            .catch((error) => {
                console.log(error);
            })

<<<<<<< Updated upstream

        } else {
            alert('존재하지 않는 아이디입니다.');
        }
    }
=======
    }
>>>>>>> Stashed changes
