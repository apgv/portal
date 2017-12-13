import router from '../router'

export default class ExternalRoutingService {
    constructor () {
        this.handleRouting = this.handleRouting.bind(this)
    }

    handleRouting () {
        if (router.currentRoute.fullPath.indexOf('#access_token') > -1) {
            router.replace('/auth0callback' + router.currentRoute.hash)
        } else if (router.currentRoute.query.unknown_api_path) {
            router.replace(router.currentRoute.query.unknown_api_path)
        } else {
            router.replace('/home')
        }
    }
}
