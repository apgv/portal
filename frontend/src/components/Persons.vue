<template>
    <div class="container">
        <div v-if="authenticated">
            <h1 class="title">Personregister</h1>

            <router-link :to="'/addperson'">
                Legg til person
            </router-link>
            <br>
            <div class="control has-icons-left">
                <input v-model="searhQuery"
                       class="input"
                       placeholder="Søk i tabellen"/>
                <span class="icon is-left">
                    <i class="fa fa-search"></i>
                </span>
            </div>
            <table class="table is-striped is-hoverable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Navn</th>
                    <th>E-post</th>
                    <th>Telefon</th>
                    <th>Adresse</th>
                    <th>Registrert av</th>
                    <th>Registrert dato</th>
                    <th>Medlem</th>
                    <th>Administrer medlemskap</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="person in filteredPersons">
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
                persons: [],
                searhQuery: ''
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
        computed: {
            filteredPersons: function () {
                return this.persons.filter(row => {
                    return Object.keys(row).some(key => {
                        return String(row[key]).toLowerCase().indexOf(this.searhQuery.toLowerCase()) > -1
                    })
                })
            }
        },
        created () {
            this.fetchPersons()
        }
    }
</script>
