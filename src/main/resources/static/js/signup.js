import * as userModule from './user.js';

const lastUrl = new URLSearchParams(location.search).get('next') ?? '/login'

userModule.fetchLoggedIn().then(userInfo =>{
    if (userInfo) location.href = '/'
})
document.getElementById('signup-form').addEventListener('submit', e => {
    e.preventDefault()
    const data = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        passwordCheck: document.getElementById('password-check').value,
        email: document.getElementById('email').value,
        mbti: document.getElementById('mbti').value,
        gender: document.getElementById('gender').value,
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        birthDate: document.getElementById('birthDate').value
    }
    fetch('/users', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(response => {
        if (response.ok) {
            alert('회원가입에 성공하였습니다.' + '\n' + '로그인 페이지로 이동합니다.')
            location.href = lastUrl
        }
        else throw new Error(response.status.toString())
    }).catch(e => {
        console.log(e.message)
        alert('회원가입에 실패하였습니다.')
    })
})