import router from '../router'

export default class RoutingService {
    constructor () {
        this.handleRouting = this.handleRouting.bind(this)
    }

    handleRouting () {
        if (router.currentRoute.fullPath.indexOf('#access_token') > -1) {
            router.replace('/auth0callback')
        } else if (router.currentRoute.query.unknown_path) {
            router.replace(router.currentRoute.query.unknown_path)
        } else {
            router.replace('/home')
        }
    }
}
