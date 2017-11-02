import auth0 from 'auth0-js'
import EventEmitter from 'EventEmitter'
import router from '../router'

export default class AuthService {
    authenticated = this.isAuthenticated()
    authNotifier = new EventEmitter()

    constructor () {
        this.login = this.login.bind(this)
        this.setSession = this.setSession.bind(this)
        this.logout = this.logout.bind(this)
        this.isAuthenticated = this.isAuthenticated.bind(this)
    }

    auth0 = new auth0.WebAuth({
        domain: 'skotbuvel.eu.auth0.com',
        clientID: 'Py44O-Jo44CCQsEyBpBOLrIL1ozDJmZ5',
        redirectUri: 'http://localhost:8080/callback',
        audience: 'https://skotbuvel.eu.auth0.com/userinfo',
        responseType: 'token id_token',
        scope: 'openid'
    })

    login () {
        this.auth0.authorize()
    }

    handleAuthentication () {
        this.auth0.parseHash((err, authResult) => {
            if (authResult && authResult.accessToken && authResult.idToken) {
                this.setSession(authResult)
                router.replace('home')
            } else if (err) {
                router.replace('home')
                console.log(err)
            }
        })
    }

    setSession (authResult) {
        let expiresAt = JSON.stringify(authResult.expiresIn * 1000 + new Date().getTime())
        localStorage.setItem('access_token', authResult.accessToken)
        localStorage.setItem('id_token', authResult.idToken)
        localStorage.setItem('expires_at', expiresAt)
        this.authNotifier.emit('authChange', {authenticated: true})
    }

    logout () {
        localStorage.removeItem('access_token')
        localStorage.removeItem('id_token')
        localStorage.removeItem('expires_at')
        this.userProfile = null
        this.authNotifier.emit('authChange', false)
        router.replace('home')
    }

    isAuthenticated () {
        let expiresAt = JSON.parse(localStorage.getItem('expires_at'))
        return new Date().getTime() < expiresAt
    }
}
