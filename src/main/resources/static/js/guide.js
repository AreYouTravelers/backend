// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[3];

function updateCreatedAtField() {
    var createdAtField = document.getElementById("createdAt");
    var currentDateTime = new Date();
    var year = currentDateTime.getFullYear();
    var month = String(currentDateTime.getMonth() + 1).padStart(2, "0");
    var day = String(currentDateTime.getDate()).padStart(2, "0");
    var hours = String(currentDateTime.getHours()).padStart(2, "0");
    var minutes = String(currentDateTime.getMinutes()).padStart(2, "0");
    var formattedDateTime = year + "-" + month + "-" + day + " " + hours + ":" + minutes;
    createdAtField.value = formattedDateTime;
}

document.addEventListener('DOMContentLoaded', function () {
    const updateForm = document.getElementById('updateForm');
    const updateButton = document.getElementById('updateButton');
    const deleteForm = document.getElementById('deleteForm');
    const deleteButton = document.getElementById('deleteButton');

    updateCreatedAtField()
    // 작성일 업데이트 간격 설정 (예: 1분마다 업데이트)
    setInterval(updateCreatedAtField, 60000); // 60000 밀리초 = 1분

    updateButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(updateForm);

        // 선택된 값을 Long 형으로 파싱
        const selectedCountry = document.getElementById("country").value;
        const countryId = parseInt(selectedCountry);
        const selectedCategory = document.getElementById("category").value;
        const categoryId = parseInt(selectedCategory);

        console.log("Selected Country ID:", countryId);
        console.log("Selected Category ID:", categoryId);

        // FormData에 Long 형식의 'country' 값을 추가
        formData.append('countryId', countryId);
        formData.append('categoryId', categoryId);

        fetch(`/boards/guide/${boardId}`, {  // <-- dto.id를 boardId로 변경했습니다.
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
                    window.location.href = '/boards/' + boardId;
                    // location.reload();
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