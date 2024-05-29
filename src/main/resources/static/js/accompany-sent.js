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
            throw new Error('서버에서 응답 오류 발생');
        }
    })
    .then(data => {
        console.log('Fetched data:', data); // 데이터를 로그에 출력하여 형식을 확인합니다.

        const container = document.getElementById('accompany-container'); // 데이터를 추가할 컨테이너 요소

        if (data && Array.isArray(data.data)) {
            if (data.data.length === 0) {
                container.innerHTML = '<p>보낸 요청이 없습니다.</p>';
                return;
            }

            // 이전에 추가된 내용을 초기화
            container.innerHTML = '';

            data.data.forEach(item => {
                // 각 데이터 항목을 위한 새로운 div 요소 생성
                const colDiv = document.createElement('div');
                colDiv.classList.add('col-md-4', 'col-lg-4', 'mb-5'); // 여기에 부트스트랩 그리드 클래스를 추가합니다

                const itemDiv = document.createElement('div');
                itemDiv.classList.add('board-item', 'mx-auto');

                const itemLink = document.createElement('a');
                itemLink.classList.add('href-none');
                itemLink.href = "/accompany/sent/" + item.id;

                // board-item-header 요소 생성
                const boardItemHeaderDiv = document.createElement('div');
                boardItemHeaderDiv.classList.add('board-item-header');

                // header-left 요소 생성
                const headerLeftDiv = document.createElement('div');
                headerLeftDiv.classList.add('header-left');

                // item-date 요소 생성 및 추가
                const itemDateP = document.createElement('p');
                itemDateP.classList.add('item-date');
                const startDateElement = document.createElement('span');
                startDateElement.className = 'start-date';
                startDateElement.innerText = item.requestedBoardInfoDto.startDate;
                const endDateElement = document.createElement('span');
                endDateElement.className = 'end-date';
                endDateElement.innerText = ' ~ ' + item.requestedBoardInfoDto.endDate;
                itemDateP.appendChild(startDateElement);
                itemDateP.appendChild(endDateElement);
                headerLeftDiv.appendChild(itemDateP);

                // header-right 요소 생성
                const headerRightDiv = document.createElement('div');
                headerRightDiv.classList.add('header-right');

                // status 요소 생성 및 추가
                const statusElement = document.createElement('p');
                statusElement.className = 'status';
                statusElement.innerText =
                    item.status === 'PENDING' ? '대기' :
                        item.status === 'ACCEPTED' ? '수락' :
                            item.status === 'REJECTED' ? '거절' : '알 수 없음';
                headerRightDiv.appendChild(statusElement);

                // header 요소들을 board-item-header에 추가
                boardItemHeaderDiv.appendChild(headerLeftDiv);
                boardItemHeaderDiv.appendChild(headerRightDiv);

                // board-item-bg 요소 생성
                const boardItemBgDiv = document.createElement('div');
                boardItemBgDiv.classList.add('board-item-bg');

                // board-item-icon 요소 생성 및 추가
                const boardItemIconP = document.createElement('p');
                boardItemIconP.classList.add('board-item-icon');
                const iconImg = document.createElement('img');
                iconImg.src = '/static/images/icon/icon-location.png';
                iconImg.alt = '지도아이콘';
                const countrySpan = document.createElement('span');
                countrySpan.id = 'country';
                countrySpan.innerText = item.requestedBoardInfoDto.country;
                boardItemIconP.appendChild(iconImg);
                boardItemIconP.appendChild(countrySpan);
                boardItemBgDiv.appendChild(boardItemIconP);

                // board-item-img 요소 생성 및 추가
                const boardItemImgP = document.createElement('p');
                boardItemImgP.classList.add('board-item-img');
                const countryImg = document.createElement('img');
                countryImg.src = '/static/images/country/' + item.requestedBoardInfoDto.country + '.jpg';
                boardItemImgP.appendChild(countryImg);
                boardItemBgDiv.appendChild(boardItemImgP);

                // board-item-info 요소 생성
                const boardItemInfoDiv = document.createElement('div');
                boardItemInfoDiv.classList.add('board-item-info');

                // item-title 요소 생성 및 추가
                const itemTitleP = document.createElement('p');
                itemTitleP.classList.add('item-title');
                itemTitleP.innerText = item.requestedBoardInfoDto.title;
                itemTitleP.style.marginBottom = '18px'; // 인라인 스타일 추가
                boardItemInfoDiv.appendChild(itemTitleP);

                // item-contents 요소 생성 및 추가
                const itemContentsDiv = document.createElement('div');
                itemContentsDiv.classList.add('item-contents');

                // message 요소 생성 및 추가
                const messageElement = document.createElement('p');
                messageElement.id = 'message';
                messageElement.classList.add('message');
                messageElement.innerText = item.message;
                itemContentsDiv.appendChild(messageElement);

                // board-item-info에 item-contents 추가
                boardItemInfoDiv.appendChild(itemContentsDiv);

                // itemLink에 board-item-header와 board-item-info 추가
                itemLink.appendChild(boardItemHeaderDiv);
                itemLink.appendChild(boardItemBgDiv);
                itemLink.appendChild(boardItemInfoDiv);

                // itemDiv에 itemLink 추가
                itemDiv.appendChild(itemLink);
                colDiv.appendChild(itemDiv);

                // 새로운 div 요소를 컨테이너에 추가
                container.appendChild(colDiv);
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
