<template>
    <div>
        <div v-if="authenticated">
            <h1 class="title">Personregister</h1>

            <button @click="toggleAddPersonModal()"
                    class="button">
                Legg til person
            </button>
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
        <add-person :auth="auth"
                    :authenticated="authenticated"
                    :showModal="showAddPersonModal"
                    v-on:close-modal-add-person="toggleAddPersonModal()">
        </add-person>
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
                persons: [],
                showAddPersonModal: false
            }
        },
        methods: {
            fetchPersons () {
                if (this.authenticated) {
                    axios.get('api/persons', {headers: {'X-JWT': this.auth.jwt()}})
                        .then(response => {
                            this.persons = response.data
                        })
                        .catch(error => {
                            console.log(error)
                        })
                }
            },
            toggleAddPersonModal () {
                this.showAddPersonModal = !this.showAddPersonModal
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
