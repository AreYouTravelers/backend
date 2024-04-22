// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

// 게시물 조회 요청
fetch(`/boards/reviews/receiver`, {
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