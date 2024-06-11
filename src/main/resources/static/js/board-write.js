// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

document.addEventListener('DOMContentLoaded', function () {
    const postForm = document.getElementById('postForm');
    const postButton = document.getElementById('postButton');

    postButton.addEventListener("click", function (event) {
        event.preventDefault();
        const formData = new FormData(postForm);

        // 선택된 값을 Long 형으로 파싱
        const selectedCountry = document.getElementById("country").value;
        const countryId = parseInt(selectedCountry);
        const selectedCategory = document.getElementById("category").value;
        const categoryId = parseInt(selectedCategory);

        // FormData에 Long 형식의 'country' 값을 추가
        formData.append('countryId', countryId);
        formData.append('categoryId', categoryId);

        fetch(`/boards/write`, {
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
                    window.location.href = '/boards';
                } else {
                    alert('게시물 등록에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

});