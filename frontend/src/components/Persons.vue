<template>
    <div class="container">
        <div v-if="authenticated">
            <h4 class="title is-4">Personregister</h4>

            <div class="columns">
                <div class="column">
                    <div class="control has-icons-left">
                        <input v-model="filterKey"
                               class="input"
                               placeholder="Søk"/>
                        <span class="icon is-left">
                            <i class="fa fa-search"></i>
                        </span>
                    </div>
                </div>
                <div class="column">
                    <div class="field is-horizontal">
                        <div class="control">
                            <label class="checkbox">
                                <input type="checkbox"
                                       :value="currentYear"
                                       v-model="membershipYears">
                                Medlem {{currentYear}}
                            </label>
                            <label class="checkbox">
                                <input type="checkbox"
                                       :value="nextYear"
                                       v-model="membershipYears">
                                Medlem {{nextYear}}
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div>membershipYears={{membershipYears}}</div>
            <router-link :to="'/addperson'">
                Legg til person
            </router-link>
            <div class="is-pulled-right">
                Viser {{filteredPersons.length}} av {{persons.length}}
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
                    <td>{{hasMembership(person.memberships) ? 'Ja' : 'Nei'}}</td>
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
                filterKey: '',
                currentYear: new Date().getFullYear(),
                nextYear: new Date().getFullYear() + 1,
                membershipYears: []
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
            },
            hasMembership (memberships) {
                let years = memberships.map(membership => {
                    return membership.year
                })

                return years.some(year => [this.currentYear, this.nextYear].includes(year))
            }
        },
        computed: {
            filteredPersons: function () {
                let data = this.persons
                let filterKey = this.filterKey && this.filterKey.toLowerCase()

                if (filterKey) {
                    data = data.filter(row => {
                        return Object.keys(row).some(key => {
                            return String(row[key]).toLowerCase().indexOf(filterKey) > -1
                        })
                    })
                }

                if (this.membershipYears.length > 0) {
                    data = data.filter(row => {
                        let years = row['memberships'].map(membership => {
                            return membership.year
                        })

                        return years.some(year => this.membershipYears.includes(year))
                    })
                }

                return data
            }
        },
        created () {
            this.fetchPersons()
        }
    }
</script>
