// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
// var boardId = /*[[${dto.id}]]*/ 'default_value';
const boardId = window.location.pathname.split("/")[2];
console.log(boardId);



document.addEventListener('DOMContentLoaded', function () {
    const updateForm = document.getElementById('updateForm');
    const updateButton = document.getElementById('updateButton');
    const deleteForm = document.getElementById("deleteForm");
    const deleteButton = document.getElementById("deleteButton");

    updateButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(updateForm);

        fetch(`/boards/${boardId}`, {  // <-- dto.id를 boardId로 변경했습니다.
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries()))
        })
            .then(response => {
                if (response.ok) {
                    alert('게시물이 업데이트되었습니다.');
                    window.location.href = '/boards/' + boardId;  // <-- dto.id를 boardId로 변경했습니다.
                } else {
                    alert('게시물 업데이트에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

    deleteButton.addEventListener("click", function (event) {
        event.preventDefault();
        if (confirm("게시물을 삭제하시겠습니까?")) {
            fetch(`/boards/${boardId}`, {  // <-- dto.id를 boardId로 변경했습니다.
                method: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("게시물이 삭제되었습니다.");
                        window.location.href = '/accompany';
                    } else {
                        alert("게시물 삭제를 실패했습니다.");
                    }
                })
                .catch(error => {
                    console.error('Network error:', error);
                });
        } else {
            alert("게시물 삭제가 취소되었습니다.");
        }
    });
});