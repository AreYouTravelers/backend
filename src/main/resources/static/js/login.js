import * as userModule from './user.js';

const lastUrl = new URLSearchParams(location.search).get('next') ?? '/'

userModule.fetchLoggedIn().then(userInfo => {
    if (userInfo) {
        location.href = lastUrl
        alert("이미 로그인 상태입니다.") // 로그인되어있어야되고
    }
})
document.getElementById('login-form').addEventListener('submit', e => {
    e.preventDefault()
    const data = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    }
    fetch('/users/login', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => {
        if (response.ok) return response.json()
        else throw new Error(response.status.toString())
    }).then(bodyJson => {
        localStorage.setItem('accessToken', bodyJson.accessToken)
        location.href = lastUrl
    }).catch(e => {
        console.log(e.message)
        alert('로그인 정보가 일치하지 않습니다.')
    })
})