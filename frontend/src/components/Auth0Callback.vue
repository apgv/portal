<template>
    <div>
        <img src="../assets/loading.svg"/>
    </div>
</template>

<script>
    export default {
        name: 'Auth0Callback',
        props: ['auth'],
        data () {
            this.auth.handleAuthentication()
            return {}
        }
    }
</script>