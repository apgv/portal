<template>
    <div>
        <div v-if="authenticated">
            <h1 class="title">Personregister</h1>

            <table class="table is-striped is-hoverable">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Fornavn</td>
                    <td>Etternavn</td>
                    <td>E-post</td>
                    <td>Telefon</td>
                    <td>Medlem</td>
                </tr>
                </thead>
                <tbody>
                <tr v-for="person in persons">
                    <td>{{person.id}}</td>
                    <td>{{person.firstName}}</td>
                    <td>{{person.lastName}}</td>
                    <td>{{person.email}}</td>
                    <td>{{person.phone}}</td>
                    <td>{{person.member ? 'Ja' : 'Nei'}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div v-if="!authenticated">
            Du er ikke logget inn. Vennligst <a @click="auth.login()">logg inn</a> for å fortsette.
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Persons',
        props: ['auth', 'authenticated'],
        data () {
            return {
                persons: []
            }
        },
        methods: {
            fetchPersons () {
                let jwt = localStorage.getItem('id_token')
                if (this.authenticated) {
                    this.$http.get('api/persons', {headers: {'X-JWT': jwt}}).then(response => {
                        this.persons = response.body
                    })
                }
            }
        },
        created () {
            this.fetchPersons()
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
