// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[3];

document.addEventListener('DOMContentLoaded', function () {
    const editButton = document.getElementById('edit-button');
    const updateForm = document.getElementById('updateForm');
    const updateButton = document.getElementById('update-button');
    const cancelButton = document.getElementById('cancel-button');
    const listButton = document.getElementById('list-button');
    const deleteButton = document.getElementById('delete-button');
    const guideButton = document.getElementById('guide-button');
    const commentButton = document.getElementById('comment-button');
    const maxPeople = document.getElementById('maxPeople');
    const title = document.getElementById('title');
    const message = document.getElementById('message');

    updateButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(updateForm);

        // 선택된 값을 Long 형으로 파싱
        const selectedCountry = document.getElementById("country").value;
        const countryId = parseInt(selectedCountry);

        // FormData에 Long 형식의 'country' 값을 추가
        formData.append('countryId', countryId);

        fetch(`/boards/guide/${boardId}`, {
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
                    window.location.href = `/boards/guide/${boardId}`;
                } else {
                    alert('게시물 업데이트에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

    editButton.addEventListener('click', function (event) {
        event.preventDefault();
        originalMessage = message.value; // 현재 메시지 값을 저장
        maxPeople.removeAttribute('disabled');
        title.removeAttribute('disabled');
        message.removeAttribute('disabled');
        message.style.backgroundColor = ''; // 배경색을 원래대로 되돌림
        message.style.cursor = 'text'; // 커서를 텍스트 입력 가능하도록 변경
        editButton.style.visibility = 'hidden';
        cancelButton.style.display = 'block';
        updateButton.style.display = 'block';
        listButton.style.display = 'none';
        guideButton.style.display = 'none';
        commentButton.style.display = 'none';
        deleteButton.style.display = 'none';
        console.log("editButton clicked");
    });

    // 취소 버튼 클릭 시 입력 비활성화 및 버튼 원래 상태로 복귀
    cancelButton.addEventListener('click', function (event) {
        event.preventDefault();
        maxPeople.setAttribute('disabled', 'disabled');
        title.setAttribute('disabled', 'disabled');
        message.setAttribute('disabled', 'disabled');
        message.style.backgroundColor = '#e9ecef'; // 회색 배경
        message.value = originalMessage; // 원래 메시지 값으로 복원
        editButton.style.visibility = 'visible';
        cancelButton.style.display = 'none';
        updateButton.style.display = 'none';
        listButton.style.display = 'block';
        guideButton.style.display = 'block';
        commentButton.style.display = 'block';
        deleteButton.style.display = 'block';
    });

    deleteButton.addEventListener("click", function (event) {
        event.preventDefault();
        if (confirm("게시물을 삭제하시겠습니까?")) {
            fetch(`/boards/guide/${boardId}`, {  // <-- dto.id를 boardId로 변경했습니다.
                method: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("게시물이 삭제되었습니다.");
                        window.location.href = '/boards/guide';
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