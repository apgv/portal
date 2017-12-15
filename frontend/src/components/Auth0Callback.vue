<template>
    <div class="has-text-centered">
        <img src="../assets/loading.svg"/>
        <div>Logger inn...</div>
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