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
        const email = document.getElementById('pw-email').value.trim();

        if (!id) {
            alert('아이디를 입력해주세요');
            return;
        }

        if (!email) {
            alert('이메일을 입력해주세요');
            return;
        }

        const isUniqueId = await axios.get("/member/registration/checkId", {params: {id: id}}).data;

       if (isUniqueId) {
           alert('존재하지 않는 아이디입니다.');
           return;
       }

        const isRightEmail = await axios.get("/member/login/findPw/checkEmail",
                                                {params:
                                                        {
                                                            id: id,
                                                            email: email
                                                        }
                                                });

        if (!isRightEmail.data) {
            alert('가입 시 입력한 이메일과 일치하지 않습니다.');
            return;
        }

        if (!isUniqueId && isRightEmail) {
            alert('가입 시 입력하신 이메일로 임시비밀번호가 전송되었습니다.');
            
            closeModal();
            
            await axios.get(("/member/login/findPw"), {params: {id: id}})
            .then((response) => {
                if (response.data) {
                    console.log("성공");
                } else {
                    console.log("실패");
                }
            })
            .catch((error) => {
                console.log(error);
            })
        }

    }