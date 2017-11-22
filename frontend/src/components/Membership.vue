<template>
    <div class="container">
        <div v-if="authenticated">
            <h1 class="title">Medlemskap</h1>

            <router-link :to="'/persons'">
                Til personregister
            </router-link>

            <div class="columns">
                <div class="column">
                    <label class="label">Personinfo</label>
                    <div class="box">
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Navn:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.fullName}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">E-post:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.email}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Telefon:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.phone}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="field">
                        <label class="label">Velg medlemskap</label>
                        <div v-for="membershipType in membershipTypes"
                             class="control box">
                            <label class="radio">
                                <input
                                        type="radio"
                                        :value="membershipType"
                                        v-model="membership"/>
                                {{membershipType.year}} {{membershipType.type}} (kr. {{membershipType.price}})
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <button @click="save()"
                        class="button is-success">
                    Lagre
                </button>
                <router-link :to="'/persons'"
                             class="button">
                    Avbryt
                </router-link>
            </div>
        </div>

        <not-authenticated :auth="auth"
                           :authenticated="authenticated">
        </not-authenticated>
    </div>
</template>

<script>
    import NotAuthenticated from './NotAuthenticated.vue'
    import axios from 'axios'

    export default {
        name: 'Membership',
        components: {NotAuthenticated},
        props: ['auth', 'authenticated', 'personId'],
        data () {
            return {
                person: {
                    fullName: null,
                    email: null,
                    phone: null
                },
                membershipTypes: [],
                membership: null
            }
        },
        methods: {
            fetchPerson () {
                if (this.authenticated) {
                    axios.get(`/api/persons/${this.personId}`, {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(response => {
                        this.person = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            fetchMembershipTypes () {
                if (this.authenticated) {
                    axios.get('/api/membershiptypes', {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(response => {
                        this.membershipTypes = response.data
                    }).catch(error => {
                        console.log(error)
                    })
                }
            },
            save () {
                alert('Not implemented yet')
            }
        },
        created () {
            this.fetchPerson()
            this.fetchMembershipTypes()
        }
    }
</script>