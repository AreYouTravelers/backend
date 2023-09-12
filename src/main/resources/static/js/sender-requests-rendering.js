// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[2];
const senderRequestsId = window.location.pathname.split("/")[4];

// 동행 요청 조회 단일 조회
fetch(`/boards/${boardId}/sender-requests/${senderRequestsId}`, {
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
    const createForm = document.getElementById('createForm');
    const createButton = document.getElementById('createButton');

    createButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(createForm);
        fetch(`/boards/${boardId}/sender-requests`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    alert("동행 요청이 되었습니다.");
                    window.location.href = '/boards/{boardId}/sender-requests/{senderRequestsId}';
                } else {
                    alert("동행 요청에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});