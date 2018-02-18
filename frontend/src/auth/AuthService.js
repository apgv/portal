import auth0 from 'auth0-js'
import router from '../router'
import {eventBus} from '../main'
import axios from 'axios'
import {STYREMEDLEM} from './Roles'

export default class AuthService {
    authenticated = this.isAuthenticated()

    constructor () {
        this.login = this.login.bind(this)
        this.setSubject = this.setSubject.bind(this)
        this.getSubject = this.getSubject.bind(this)
        this.setSession = this.setSession.bind(this)
        this.logout = this.logout.bind(this)
        this.isAuthenticated = this.isAuthenticated.bind(this)
        this.jwt = this.jwt.bind(this)
        this.hasOneOfTheRoles = this.hasOneOfTheRoles.bind(this)
        this.hasNoneOfTheRoles = this.hasNoneOfTheRoles.bind(this)
        this.isSTYREMEDLEM = this.isSTYREMEDLEM.bind(this)
    }

    auth0 = new auth0.WebAuth({
        domain: process.env.AUTH0_DOMAIN,
        clientID: process.env.AUTH0_CLIENTID,
        redirectUri: process.env.AUTH0_REDIRECTURI,
        audience: process.env.AUTH0_AUDIENCE,
        responseType: process.env.AUTH0_RESPONSETYPE,
        scope: process.env.AUTH0_SCOPE
    })

    login () {
        if (router.currentRoute.path !== '/reauthenticate') {
            localStorage.setItem('current_path', router.currentRoute.path)
        }

        this.auth0.authorize()
    }

    handleAuthentication () {
        if (router.currentRoute.hash) {
            this.auth0.parseHash((err, authResult) => {
                if (authResult && authResult.accessToken && authResult.idToken) {
                    this.setSession(authResult)
                    let currentPath = localStorage.getItem('current_path')

                    if (currentPath) {
                        router.replace(currentPath)
                    } else {
                        router.replace('/home')
                    }
                } else if (err) {
                    router.replace('/home')
                    console.log(err)
                }
            })
        } else {
            router.replace('/home')
        }
    }

    setSubject (authResult) {
        axios.get(`/api/subjects/${authResult.idTokenPayload.email}`, {
            headers: {'X-JWT': this.jwt()}
        }).then(response => {
            localStorage.setItem('subject', JSON.stringify(response.data))
            eventBus.$emit('subjectResolved')
        }).catch(error => {
            this.logout()
            console.log(error)
        })
    }

    getSubject () {
        return JSON.parse(localStorage.getItem('subject'))
    }

    setSession (authResult) {
        let expiresAt = JSON.stringify(authResult.expiresIn * 1000 + new Date().getTime())
        localStorage.setItem('access_token', authResult.accessToken)
        localStorage.setItem('id_token', authResult.idToken)
        localStorage.setItem('expires_at', expiresAt)
        this.setSubject(authResult)
        eventBus.$emit('authChange', {authenticated: true})
    }

    logout () {
        localStorage.removeItem('subject')
        localStorage.removeItem('access_token')
        localStorage.removeItem('id_token')
        localStorage.removeItem('expires_at')
        this.subject = null
        eventBus.$emit('authChange', false)
        router.replace('/home')
    }

    isAuthenticated () {
        let expiresAt = JSON.parse(localStorage.getItem('expires_at'))
        return new Date().getTime() < expiresAt
    }

    jwt () {
        return localStorage.getItem('id_token')
    }

    hasOneOfTheRoles (roles) {
        return this.getSubject().roles.some(role => {
            return roles.indexOf(role) > -1
        })
    }

    hasNoneOfTheRoles (roles) {
        return !this.hasOneOfTheRoles(roles)
    }

    isSTYREMEDLEM () {
        return this.getSubject().roles.indexOf(STYREMEDLEM) > -1
    }
}
