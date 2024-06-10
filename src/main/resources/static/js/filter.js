// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');


document.addEventListener("DOMContentLoaded", function () {
    // 사용자가 선택한 값을 가져옵니다.
    // const form = document.querySelector("form");
    // const country = document.querySelector('#travel').value;
    // const category = document.querySelector('#guide').value;
    // const mbti = document.querySelector('#mbti').value;
    const getForm = document.getElementById('getForm');
    const getButton = document.getElementById('getButton');
    const boardContainer = document.getElementById('boardContainer'); // 검색 결과를 표시할 컨테이너

    getButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(getForm);
        const mbtiCriteria = document.getElementById("mbti").value();

        // 선택된 값을 Long 형으로 파싱
        const selectedCountry = parseInt(document.getElementById("country").value);
        const selectedCategory = parseInt(document.getElementById("category").value);

        formData.append('mbtiCriteria', mbtiCriteria);
        formData.append('country', selectedCountry);
        formData.append('category', selectedCategory);

        fetch(`/boards/filter?country=${selectedCountry}&category=${selectedCategory}&mbti=${mbtiCriteria}`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: formData
        })
            .then(response => {
                // 성공적인 응답인지 확인
                if (!response.ok) {
                    throw new Error(`Network response was not ok, status: ${response.status}`);
                }
                return response.json(); // 응답을 JSON으로 파싱
            })
            .then(data => {
                const boardContainer = document.querySelector('.row.justify-content-start');
                boardContainer.innerHTML = '';

                data.content.forEach(board => {
                    const boardItem = `<div class="col-md-6 col-lg-3 col-lg-3-2 mb-5">
                    <h3>${board.title}</h3>
                    <p>${board.content}</p>
                    <!-- 필요한 다른 필드들도 여기에 추가 가능 -->
                </div>`;
                    boardContainer.insertAdjacentHTML('beforeend', boardItem);
                });
            })
            .catch(error => {
                // 오류를 콘솔에 로그
                console.error('There was a problem with the fetch operation:', error);
            });
    });
});