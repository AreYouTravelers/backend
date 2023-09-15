document.addEventListener('DOMContentLoaded', function () {
    const reviewPossible = document.getElementById('go-to-reviews-possible');
    const accessToken = localStorage.getItem('accessToken');

    reviewPossible.addEventListener('click', function (event) {
        event.preventDefault();

        console.log("js 1")
        console.log(accessToken)
        fetch(`/boards/reviews-possible`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    // return document.write(response); // HTML 내용을 받아옴
                    window.location.href(response.url)
                } else {
                    alert('후기 작성 페이지로 이동 실패했습니다.');
                    console.log("response 낫오케");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});