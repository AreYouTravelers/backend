const getJwt = () => localStorage.getItem('accessToken')

const fetchLoggedIn = function () {
    if (!getJwt())  // 토큰이 없으면 로그인하지 않은 상태로 처리
        return new Promise(resolve => {
            resolve(false)
        })
    else return fetch("/users/my-profile", {
        headers: {
            'Authorization': `Bearer ${getJwt()}`
        }
    })
        .then(response => {
        if (response.ok) return response.json()
        else return false  // 로그인 상태가 아님
    })
        .catch(error => {
            console.error(error);
            return false; // 오류 발생시 로그인 상태가 아님
        })
}