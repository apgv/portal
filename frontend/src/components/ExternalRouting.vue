<script>
    import RoutingService from '../external-routing/RoutingService'

    const routingService = new RoutingService()

    export default {
        name: 'external-routing',
        data () {
            routingService.handleRouting()
            return {}
        }
    }
</script>
