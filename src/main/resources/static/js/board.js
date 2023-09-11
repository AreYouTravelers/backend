// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
var boardId = /*[[${dto.id}]]*/ 'default_value';

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
    const deleteForm = document.getElementById("deleteForm");
    const deleteButton = document.getElementById("deleteButton");

    updateButton.addEventListener('click', function (event) {
        event.preventDefault(); // 폼 제출을 막습니다.
        // 폼 데이터를 캡처합니다.
        const formData = new FormData(updateForm);

        // 서버로 PUT 요청을 보냅니다.
        fetch(`/boards/${dto.id}`, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    // 성공적으로 처리됐을 때의 작업을 수행합니다.
                    alert('게시물이 업데이트되었습니다.');
                    // 선택적으로 사용자를 다른 페이지로 리디렉션할 수 있습니다.
                    window.location.href = '/boards' + boardId;
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

    // 버튼을 클릭할 때 실행되는 함수를 정의합니다.
    deleteButton.addEventListener("click", function (event) {
    // function confirmDelete() {
        event.preventDefault();
        // 사용자에게 삭제 여부를 물어보고 확인을 누르면 삭제 작업을 수행합니다.
        if (window.confirm("삭제하시겠습니까?")) {
            // const formData = new FormData(deleteForm);
            fetch(`/boards/${dto.id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                    'Content-Type': 'application/json'
                }
                // body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
            })
                .then(response => {
                    if (response.ok) {
                        // 댓글 삭제 완료 시 메시지를 표시하거나 다른 작업을 수행할 수 있습니다.
                        alert("삭제되었습니다.");
                        // location.reload();
                        window.location.href = '/accompany';
                    } else {
                        alert("삭제 중 오류가 발생했습니다.");
                    }
                })
                .catch(error => {
                    // 네트워크 오류 처리
                    console.error('Network error:', error);
                });
        } else {
            // 사용자가 취소한 경우 아무 작업도 수행하지 않습니다.
            alert("삭제가 취소되었습니다."); // 예시: 삭제 취소 시 메시지를 표시
        }
    // }
    });
});

