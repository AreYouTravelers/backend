// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

// 게시물 조회 요청
fetch(`/boards/${dto.id}`, {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ` + accessToken, // JWT 토큰을 포함
        'Content-Type': 'application/json'
    }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('서버에서 응답 오류 발생');
        }
    })
    .then(data => {
        console.log('서버로부터 받은 데이터:', data);
        // 원하는 동작 수행
    })
    .catch(error => {
        console.error('에러:', error);
    });

document.addEventListener('DOMContentLoaded', function () {
    const updateForm = document.getElementById('updateForm');
    const updateButton = document.getElementById('updateButton');

    updateButton.addEventListener('click', function (event) {
        event.preventDefault(); // 폼 제출을 막습니다.

        // 폼 데이터를 캡처합니다.
        const formData = new FormData(updateForm);

        // 서버로 PUT 요청을 보냅니다.
        fetch(`/boards/${dto.id}`, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    // 성공적으로 처리됐을 때의 작업을 수행합니다.
                    alert('게시물이 업데이트되었습니다.');
                    // 선택적으로 사용자를 다른 페이지로 리디렉션할 수 있습니다.
                    window.location.href = '/boards';
                } else {
                    // 오류 시의 작업을 수행합니다.
                    alert('게시물 업데이트에 실패했습니다.');
                }
            })
            .catch(error => {
                // 네트워크 오류 처리
                console.error('Network error:', error);
            });
    });
});