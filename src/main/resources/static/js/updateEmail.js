document.addEventListener('DOMContentLoaded', function () {
    const emailInput = document.getElementById('email');
    const editButton = document.getElementById('edit-email-button');
    const saveButton = document.getElementById('save-email-button');
    const cancelButton = document.getElementById('cancel-email-button');
    const accessToken = localStorage.getItem('accessToken');

    // 수정 버튼 클릭 시 입력 활성화
    editButton.addEventListener('click', function () {
        emailInput.removeAttribute('disabled');
        editButton.style.display = 'none';
        saveButton.style.display = 'block';
        cancelButton.style.display = 'block';
    });

    // 취소 버튼 클릭 시 입력 비활성화
    cancelButton.addEventListener('click', function () {
        emailInput.setAttribute('disabled', 'disabled');
        editButton.style.display = 'block';
        saveButton.style.display = 'none';
        cancelButton.style.display = 'none';
    });

    // 저장 버튼 클릭 시 이메일 수정을 서버에 반영
    saveButton.addEventListener('click', function () {
        const newEmail = emailInput.value;

        // PATCH 요청을 보내서 이메일 수정
        fetch('/users/email', {
            method: 'PATCH',
            body: JSON.stringify({ email: newEmail }), // 수정된 이메일 값을 서버로 전달
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('이메일이 성공적으로 수정되었습니다.');
                    emailInput.setAttribute('disabled', 'disabled');
                    editButton.style.display = 'block';
                    saveButton.style.display = 'none';
                    cancelButton.style.display = 'none';
                } else {
                    throw new Error('서버에서 응답 오류 발생');
                }
            })
            .catch(error => {
                console.error('에러:', error);
                alert('이메일 수정에 실패했습니다.');
            });
    });
});
