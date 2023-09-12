document.addEventListener('DOMContentLoaded', function () {
    const mbtiInput = document.getElementById('mbti');
    const editButton = document.getElementById('edit-mbti-button');
    const saveButton = document.getElementById('save-mbti-button');
    const cancelButton = document.getElementById('cancel-mbti-button');
    const accessToken = localStorage.getItem('accessToken');

    // 수정 버튼 클릭 시 입력 활성화
    editButton.addEventListener('click', function () {
        mbtiInput.removeAttribute('disabled');
        editButton.style.display = 'none';
        saveButton.style.display = 'block';
        cancelButton.style.display = 'block';
    });

    // 취소 버튼 클릭 시 입력 비활성화
    cancelButton.addEventListener('click', function () {
        mbtiInput.setAttribute('disabled', 'disabled');
        editButton.style.display = 'block';
        saveButton.style.display = 'none';
        cancelButton.style.display = 'none';
    });

    // 저장 버튼 클릭 시 mbti 수정을 서버에 반영
    saveButton.addEventListener('click', function () {
        const newMbti = mbtiInput.value;

        // PATCH 요청을 보내서 mbti 수정
        fetch('/users/mbti', {
            method: 'PATCH',
            body: JSON.stringify({ mbti: newMbti }), // 수정된 mbti 값을 서버로 전달
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    alert('MBTI가 성공적으로 수정되었습니다.');
                    mbtiInput.setAttribute('disabled', 'disabled');
                    editButton.style.display = 'block';
                    saveButton.style.display = 'none';
                    cancelButton.style.display = 'none';
                } else {
                    throw new Error('서버에서 응답 오류 발생');
                }
            })
            .catch(error => {
                console.error('에러:', error);
                alert('MBTI 수정에 실패했습니다.');
            });
    });
});
