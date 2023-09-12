document.addEventListener('DOMContentLoaded', function () {
    const deleteAccountForm = document.getElementById('delete-account-form');
    const accessToken = localStorage.getItem('accessToken');

    // 회원 탈퇴 버튼 클릭 시 경고창 표시
    deleteAccountForm.addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 제출 동작 방지

        if (confirm('정말로 회원을 탈퇴하시겠습니까?')) {
            // 확인 버튼 클릭 시 비밀번호 입력 폼 표시
            const passwordInput = document.createElement('input');
            passwordInput.type = 'password';
            passwordInput.placeholder = '비밀번호 입력';
            passwordInput.className = 'form-control mt-2';
            passwordInput.required = true;

            const submitButton = document.createElement('button');
            submitButton.type = 'submit';
            submitButton.className = 'btn btn-danger mt-2';
            submitButton.textContent = '탈퇴 확인';

            deleteAccountForm.innerHTML = ''; // 폼 내용 초기화
            deleteAccountForm.appendChild(passwordInput);
            deleteAccountForm.appendChild(submitButton);

            // 비밀번호 입력 후 탈퇴 요청
            deleteAccountForm.addEventListener('submit', function (event) {
                event.preventDefault();

                const password = passwordInput.value;

                // DELETE 요청 데이터 설정
                const data = {
                    password: password,
                };

                // DELETE 요청 보내기
                fetch('/users/deactivate', {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + accessToken
                    },
                    body: JSON.stringify(data)
                })
                    .then(response => {
                        if (response.ok) {
                            // 성공적으로 탈퇴되면 로그아웃 후 홈페이지로 이동
                            alert('회원 탈퇴가 완료되었습니다.');
                            localStorage.removeItem('accessToken'); // 로그아웃
                            window.location.href = '/'; // 홈페이지로 이동
                        } else {
                            // 탈퇴 실패 시 오류 메시지 표시
                            alert('비밀번호가 올바르지 않습니다. 다시 시도해주세요.');
                        }
                    })
                    .catch(error => {
                        console.error('회원 탈퇴 중 오류 발생:', error);
                        alert('회원 탈퇴 중 오류가 발생했습니다.');
                    });
            });
        }
    });
});
