<template>
    <div id="app">
        <nav class="navbar">
            <div class="container">
                <div class="navbar-menu">
                    <div class="navbar-start">
                        <router-link :to="'/home'"
                                     class="navbar-item">
                            Home
                        </router-link>
                        <router-link :to="'/persons'"
                                     class="navbar-item">
                            Personregister
                        </router-link>
                    </div>
                    <div class="navbar-end">
                        <div class="navbar-item">
                            <button
                                    v-if="!authenticated"
                                    @click="login()"
                                    class="button is-link">
                                Log in
                            </button>
                            <a
                                    v-if="authenticated"
                                    @click="logout()"
                                    class="button is-link">
                                Log out
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </nav>

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

    const auth = new AuthService()
    const {login, logout, authenticated} = auth

    export default {
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

<style>
</style>
