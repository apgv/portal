<script>
    export default {
        name: 're-authenticate',
        props: ['auth'],
        data () {
            this.auth.login()
            return {}
        }
    }
</script>