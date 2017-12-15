<template>
    <div id="app"
         class="container">
        <app-menu :auth="auth"
                  :authenticated="authenticated">
        </app-menu>

        <vue-snotify></vue-snotify>

        <router-view
                :auth="auth"
                :authenticated="authenticated">
        </router-view>
    </div>
</template>

<script>
    import AuthService from './auth/AuthService'
    import {eventBus} from './main'
    import AppMenu from './components/AppMenu'

    const auth = new AuthService()
    const {login, logout, authenticated} = auth

    export default {
        components: {AppMenu},
        name: 'app',
        data () {
            eventBus.$on('authChange', authState => {
                this.authenticated = authState.authenticated
            })
            return {
                auth,
                authenticated
            }
        },
        methods: {
            login,
            logout
        }
    }
</script>
