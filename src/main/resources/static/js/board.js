// const dataToSend = {
//     // DTO 필드에 해당하는 데이터를 여기에 포함
//     title: "게시글 제목",
//     content: "게시글 내용",
//     // 다른 필드들도 포함 가능
// };
//
// fetch('/boards', {
//     method: 'POST',
//     headers: {
//         'Content-Type': 'application/json',
//     },
//     body: JSON.stringify(dataToSend),
// })
//     .then(response => response.json())
//     .then(data => {
//         console.log('서버로부터 받은 데이터:', data);
//         // 원하는 동작 수행
//     })
//     .catch(error => {
//         console.error('에러:', error);
//     });

let accessToken = localStorage.getItem('accessToken');

const boardId = 1; // 조회할 게시물의 ID를 여기에 넣으세요

// 게시물 조회 요청
fetch(`/boards/${boardId}`, {
    method: 'GET',
    headers: {
        'Authorization': 'Bearer ' + accessToken, // JWT 토큰을 포함
        'Content-Type': 'application/json'
    }
})
    .then(response => response.json())
    .then(data => {
        console.log('서버로부터 받은 데이터:', data);
        // 원하는 동작 수행
    })
    .catch(error => {
        console.error('에러:', error);
    });