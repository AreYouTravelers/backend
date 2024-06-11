// 사용자 목록을 가져와 테이블에 표시하는 함수
function fetchUserList(page, size) {
    const url = `/admin/profile-list?page=${page}&size=${size}`;
    const accessToken = localStorage.getItem('accessToken');

    if (!accessToken) {
        alert('로그인이 필요합니다.');
        return;
    }

    fetch(url, {
        headers: {
            'Authorization': `Bearer ${accessToken}`
        }
    })
        .then(response => response.json())
        .then(data => {
            const userList = document.getElementById('user-list');
            const pagination = document.getElementById('pagination');
            userList.innerHTML = '';
            pagination.innerHTML = '';

            console.log(data);

            data.content.forEach(user => {
                // 날짜 및 시간 형식 변경
                const createdAtDate = new Date(user.createdAt);
                const formattedDate = `${createdAtDate.getFullYear()}-${(createdAtDate.getMonth() + 1).toString().padStart(2, '0')}-${createdAtDate.getDate().toString().padStart(2, '0')}`;
                const formattedTime = `${createdAtDate.getHours().toString().padStart(2, '0')}:${createdAtDate.getMinutes().toString().padStart(2, '0')}:${createdAtDate.getSeconds().toString().padStart(2, '0')}`;

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${user.fullName}</td>
                    <td>${user.username}</td>
                    <td>${user.role}</td>
                    <td>${user.email}</td>
                    <td>${user.mbti}</td>
                    <td>${user.temperature}℃</td>
                    <td>${user.gender}</td>
                    <td>${user.birthDate}</td>
                    <td>${formattedDate} ${formattedTime}</td>
                    <td><button class="btn btn-danger btn-sm" onclick="deactivateUser('${user.username}')">탈퇴</button></td>
                `;
                userList.appendChild(row);
            });

            for (let i = 1; i <= data.totalPages; i++) {
                const li = document.createElement('li');
                const link = document.createElement('a');
                link.href = '#';
                link.style = 'margin-right : 5px';
                link.style.color ='#4Cb7ef';
                link.innerText = i;
                link.addEventListener('click', () => fetchUserList(i, size));
                li.appendChild(link);
                pagination.appendChild(li);
            }
        })
        .catch(error => {
            console.error(error);
            alert('유저 목록을 가져오는 데 문제가 발생했습니다.');
        });
}

// 페이지 로드 시 사용자 목록 가져오기
fetchUserList(1, 10);


// 사용자 탈퇴 처리 함수
function deactivateUser(username) {
    const accessToken = localStorage.getItem('accessToken');

    if (!accessToken) {
        alert('로그인이 필요합니다.');
        return;
    }

    if (!confirm('정말로 이 사용자를 탈퇴 처리하시겠습니까?')) {
        return;
    }

    const url = `/admin/deactivate`;
    const body = JSON.stringify({ username });

    fetch(url, {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${accessToken}`,
            'Content-Type': 'application/json'
        },
        body: body
    })
        .then(response => {
            if (response.ok) {
                // 성공적으로 탈퇴 처리됨
                // 여기에서 사용자 목록을 다시 불러올 수 있음
                fetchUserList(1, 10);
            } else {
                alert('사용자 탈퇴 처리 중 문제가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error(error);
            alert('사용자 탈퇴 처리 중 문제가 발생했습니다.');
        });
}
