// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

document.addEventListener('DOMContentLoaded', function () {
    const boardDetail = document.getElementById("board-detail");
    const boardId = document.getElementById("board-detail").value;

    fetch(`/boards/${boardId}`, {  // <-- dto.id를 boardId로 변경했습니다.
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + accessToken,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(Object.fromEntries(formData.entries()))
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/boards/' + boardId;
            } else {
                alert('게시물 조회에 실패했습니다.');
            }
        })
    .catch(error => {
        console.error('Network error:', error);
    });
});