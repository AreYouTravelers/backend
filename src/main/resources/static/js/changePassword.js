document.addEventListener('DOMContentLoaded', function () {
    const changePasswordForm = document.getElementById('change-password-form');
    const accessToken = localStorage.getItem('accessToken');

    // 비밀번호 변경 폼에 이벤트 활성화
    changePasswordForm.addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 제출 동작 방지

        // 입력된 데이터 가져오기
        const currentPassword = document.getElementById('current-password').value;
        const newPassword = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        // 비밀번호 확인
        if (newPassword !== confirmPassword) {
            if (confirm('비밀번호 재확인이 일치하지 않습니다. 변경을 취소하시겠습니까?')) {
                window.location.reload(); // 변경 취소
            }
            return;
        }

        // 확인 다이얼로그를 띄우기
        if (confirm('정말로 비밀번호를 변경하시겠습니까?')) {
            // PATCH 요청 데이터 설정
            const data = {
                currentPassword: currentPassword,
                changePassword: newPassword,
                changePasswordCheck: confirmPassword
            };

            // PATCH 요청 보내기
            fetch('/users/password', {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + accessToken
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (response.ok) {
                        // 성공적으로 변경되면 로그아웃 후 로그인 페이지로 이동
                        fetch('/users/logout', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': 'Bearer ' + accessToken
                            }
                        }).then(response => {
                            if (response.ok) {
                                alert('비밀번호가 성공적으로 변경되었습니다. 다시 로그인해주세요.');
                                localStorage.removeItem('accessToken'); // 로그아웃
                                window.location.href = '/login'; // 로그인 페이지로 이동
                            } else throw new Error(response.status.toString())
                        }).catch(e => {
                            console.error('비밀번호 변경 중 오류가 발생했습니다.', e)
                            alert('비밀번호 변경 중 오류가 발생했습니다.');
                        })
                    } else {
                        alert('입력된 정보가 올바르지 않습니다.');
                        window.location.reload();
                    }
                })
                .catch(error => {
                    console.error('비밀번호 변경 중 오류 발생:', error);
                    alert('비밀번호 변경 중 오류가 발생했습니다.');
                });
        }
    });
});
