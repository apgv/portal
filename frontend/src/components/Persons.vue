<template>
    <div class="container">
        <div v-if="authenticated">
            <h1 class="title">Personregister</h1>

            <router-link :to="'/addperson'">
                Legg til person
            </router-link>

            <table class="table is-striped is-hoverable">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Navn</td>
                    <td>E-post</td>
                    <td>Telefon</td>
                    <td>Adresse</td>
                    <td>Registrert av</td>
                    <td>Registrert dato</td>
                    <td>Medlem</td>
                    <td>Administrer medlemskap</td>
                </tr>
                </thead>
                <tbody>
                <tr v-for="person in persons">
                    <td>{{person.id}}</td>
                    <td>{{person.fullName}}</td>
                    <td>{{person.email}}</td>
                    <td>{{person.phone}}</td>
                    <td>{{person.address}}</td>
                    <td>{{person.createdBy}}</td>
                    <td>{{person.createdDate | formatDate}}</td>
                    <td>{{person.member ? 'Ja' : 'Nei'}}</td>
                    <td>
                        <router-link :to="`/membership/${person.id}`">
                            Medlemskap
                        </router-link>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <not-authenticated :auth="auth"
                           :authenticated="authenticated">
        </not-authenticated>
    </div>
</template>

<script>
    import NotAuthenticated from './NotAuthenticated.vue'
    import AddPerson from './AddPerson.vue'
    import axios from 'axios'

    export default {
        components: {
            AddPerson,
            NotAuthenticated
        },
        name: 'Persons',
        props: ['auth', 'authenticated'],
        data () {
            return {
                persons: []
            }
        },
        methods: {
            fetchPersons () {
                if (this.authenticated) {
                    axios.get('/api/persons', {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(response => {
                        this.persons = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                }
            }
        },
        created () {
            this.fetchPersons()
        }
    }
</script>
