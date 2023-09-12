// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[2];
const reviewId = window.location.pathname.split("/")[4];

// 게시물 조회 요청
fetch(`/boards/${boardId}/reviews/${reviewId}`, {
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
    // const updateForm = document.getElementById('updateForm');
    // const updateButton = document.getElementById('updateButton');
    // const deleteButton = document.getElementById('deleteButton');

    createButton.addEventListener('click', function (event) {
        event.preventDefault();

        const formData = new FormData(createForm);

        fetch(`/boards/${boardId}/reviews`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    alert("후기가 작성 되었습니다.");
                    window.location.href = '/index';
                } else {
                    alert("후기 작성에 실패하였습니다.");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

    // updateButton.addEventListener('click', function (event) {
    //     event.preventDefault(); // 폼 제출을 막습니다.
    //
    //     // 폼 데이터를 캡처합니다.
    //     const formData = new FormData(updateForm);
    //
    //     // 서버로 PUT 요청을 보냅니다.
    //     fetch(`/boards/${boardId}/reviews/${reviewId}`, {
    //         method: 'PUT',
    //         headers: {
    //             'Authorization': 'Bearer ' + accessToken,
    //             'Content-Type': 'application/json'
    //         },
    //         body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
    //     })
    //         .then(response => {
    //             if (response.ok) {
    //                 // 성공적으로 처리됐을 때의 작업을 수행합니다.
    //                 alert("후기가 수정 되었습니다.");
    //                 // 선택적으로 사용자를 다른 페이지로 리디렉션할 수 있습니다.
    //                 window.location.href = '/index';
    //             } else {
    //                 // 오류 시의 작업을 수행합니다.
    //                 alert('후기 수정에 실패했습니다.');
    //             }
    //         })
    //         .catch(error => {
    //             // 네트워크 오류 처리
    //             console.error('Network error:', error);
    //         });
    // });

    // deleteButton.addEventListener("click", function (event) {
    //     event.preventDefault();
    //     if (confirm("게시물을 삭제하시겠습니까?")) {
    //         fetch(`/boards/${boardId}/reviews/${reviewId}`, {
    //             method: 'DELETE',
    //             headers: {
    //                 'Authorization': 'Bearer ' + accessToken,
    //                 'Content-Type': 'application/json'
    //             }
    //         })
    //             .then(response => {
    //                 if (response.ok) {
    //                     alert("후기가 삭제되었습니다.");
    //                     window.location.href = '/index';
    //                 } else {
    //                     alert("후기 삭제에 실패했습니다.");
    //                 }
    //             })
    //             .catch(error => {
    //                 console.error('Network error:', error);
    //             });
    //     } else {
    //         alert("후기 삭제가 취소되었습니다.");
    //     }
    // });
});