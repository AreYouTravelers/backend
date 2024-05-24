// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

fetch(`/api/accompany/sent`, {
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
            alert("보낸동행 조회에 실패했습니다.");
        }
    })
    .then(data => {
        console.log('Fetched data:', data); // 데이터를 로그에 출력하여 형식을 확인합니다.

        const container = document.getElementById('accompany-container'); // 데이터를 추가할 컨테이너 요소

        if (data && Array.isArray(data.data)) {
            if (data.data.length === 0) {
                container.innerHTML = '<p>데이터가 없습니다.</p>';
                return;
            }

            // 이전에 추가된 내용을 초기화
            container.innerHTML = '';

            data.data.forEach(item => {
                // 각 데이터 항목을 위한 새로운 div 요소 생성
                const itemDiv = document.createElement('div');
                itemDiv.classList.add('accompany-item');

                // start date 요소 생성 및 추가
                const startDateElement = document.createElement('span');
                startDateElement.innerText = item.startDate;
                itemDiv.appendChild(startDateElement);

                // end date 요소 생성 및 추가
                const endDateElement = document.createElement('span');
                endDateElement.innerText = item.endDate;
                itemDiv.appendChild(endDateElement);

                // status 요소 생성 및 추가
                const statusElement = document.createElement('p');
                statusElement.innerText =
                    item.status === 'PENDING' ? '대기' :
                        item.status === 'ACCEPTED' ? '수락' :
                            item.status === 'REJECTED' ? '거절' : '알 수 없음';
                itemDiv.appendChild(statusElement);

                // country 요소 생성 및 추가
                const countryElement = document.createElement('span');
                endDateElement.innerText = item.endDate;
                itemDiv.appendChild(endDateElement);

                // country image 요소 생성 및 추가

                // board title 요소 생성 및 추가

                // message 요소 생성 및 추가
                const messageElement = document.createElement('p');
                messageElement.innerText = item.message;
                itemDiv.appendChild(messageElement);

                // 새로운 div 요소를 컨테이너에 추가
                container.appendChild(itemDiv);
            });
        } else {
            console.error('Invalid data format:', data);
            alert('서버에서 받은 데이터 형식이 올바르지 않습니다.');
        }
    })
    .catch(error => {
        console.error('Network error:', error);
        alert('네트워크 오류가 발생했습니다.');
    });