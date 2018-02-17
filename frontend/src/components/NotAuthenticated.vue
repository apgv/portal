<template>
    <div v-if="!authenticated">
        Du er ikke logget inn. Vennligst <a @click="auth.login()">logg inn</a> for å fortsette.
    </div>
</template>

<script>
export default {
    name: 'not-authenticated',
    props: ['auth', 'authenticated']
}
</script>