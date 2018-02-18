<template>
    <article v-if="isMissingRoles"
             class="message is-warning">
        <div class="message-body">
            <p>
                Du har ikke tilstrekkelige rettigheter. Denne siden krever én av rollene: {{requiredRoles.join(', ')}}.
            </p>
            <p>
                Du er registrert med rollene: {{auth.getSubject().roles.join(', ')}}
            </p>
        </div>
    </article>
</template>

<script>
export default {
    name: 'missing-roles',
    props: ['auth', 'authenticated', 'requiredRoles'],
    computed: {
        isMissingRoles: function () {
            return this.authenticated && this.auth.hasNoneOfTheRoles(this.requiredRoles)
        }
    }
}
</script>