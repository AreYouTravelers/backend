function fetchBoards() {
    // 사용자가 선택한 값을 가져옵니다.
    const country = document.querySelector('#travel').value;
    const category = document.querySelector('#guide').value;
    const mbti = document.querySelector('#mbti').value;

    fetch(`/boards?country=${country}&category=${category}&mbti=${mbti}`)
        .then(response => {
            // 성공적인 응답인지 확인
            if (!response.ok) {
                throw new Error(`Network response was not ok, status: ${response.status}`);
            }
            return response.json(); // 응답을 JSON으로 파싱
        })
        .then(data => {
            const boardContainer = document.querySelector('.row.justify-content-center');
            boardContainer.innerHTML = '';

            data.content.forEach(board => {
                const boardItem = `
                <div class="col-md-6 col-lg-3 col-lg-3-2 mb-5">
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
}
