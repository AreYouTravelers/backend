const getJwt = () => localStorage.getItem('accessToken')

const fetchLoggedIn = function () {
    if (!getJwt())
        return new Promise(resolve => {
            resolve(false)
        })
    else return fetch("/users/is-logged-in", {
        headers: {
            'Authorization': `Bearer ${getJwt()}`
        }
    }).then(response => {
        if (response.ok) return response.json()
        else return false
    })
}