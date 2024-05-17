let accessToken = localStorage.getItem('accessToken');

fetch(`/boards/reviews/receiver/data`, {
    method: 'GET',
    headers: {
    'Authorization': 'Bearer ' + accessToken,
    'Content-Type': 'application/json'
    }
})
    .then(response => {
    if (response.ok) {
        return response.json();
} else {
    alert("받은 후기 조회에 실패했습니다.");
    }
})
    .then(data => {
        const userName1 = document.getElementById('user-name1')
        const userName2 = document.getElementById('user-name2')
        const temperature = document.getElementById('temperature')
        const reviewWriter = document.getElementById('review-writer')
        const rating = document.getElementById('rating')
        const backgroundImage = document.getElementById('background-image')
        const boardTitle = document.getElementById('board-title')
        const boardContent = document.getElementById('board-content')
        const reviewContent = document.getElementById('review-content')

        userName1.innerText = data.receiver.username + " 님이 받은 후기";
        userName2.innerText = data.receiver.username + " 님이 받은 후기";
        temperature.innerText = data.receiver.temperature;

    })
    .catch(error => {
    console.error('Network error:', error);
});
















