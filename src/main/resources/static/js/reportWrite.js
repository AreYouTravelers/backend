// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

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
    const postForm = document.getElementById('postForm');
    const postButton = document.getElementById('postButton');

    updateCreatedAtField()
    // 작성일 업데이트 간격 설정 (예: 1분마다 업데이트)
    setInterval(updateCreatedAtField, 60000); // 60000 밀리초 = 1분

    postButton.addEventListener("click", function (event) {
        event.preventDefault();
        const formData = new FormData(postForm);

        fetch(`/reports/write`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries()))
        })
            .then(response => {
                if (response.ok) {
                    alert('게시물이 등록되었습니다.');
                    window.location.href = '/reports/write';
                } else {
                    alert('게시물 등록에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

});