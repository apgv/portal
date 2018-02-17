<script>
import ExternalRoutingService from '../external-routing/ExternalRoutingService'

const externalRoutingService = new ExternalRoutingService()

export default {
    name: 'external-routing',
    data () {
        externalRoutingService.handleRouting()
        return {}
    }
}
</script>
