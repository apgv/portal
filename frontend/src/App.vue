<template>
    <div id="app">
        <router-link :to="'/'">
            Home
        </router-link>

        <router-link :to="'/persons'">
            Personregister
        </router-link>
        <button
                v-if="!authenticated"
                @click="login()">
            Log in
        </button>
        <button
                v-if="authenticated"
                @click="logout()">
            Log out
        </button>
        <router-view
                :auth="auth"
                :authenticated="authenticated">
        </router-view>
    </div>
</template>

<script>
    import AuthService from './auth/AuthService'

    const auth = new AuthService()
    const {login, logout, authenticated, authNotifier} = auth

    export default {
        name: 'app',
        data () {
            authNotifier.on('authChange', authState => {
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
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
