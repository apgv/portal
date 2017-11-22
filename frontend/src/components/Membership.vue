<template>
    <div class="container">
        <div v-if="authenticated">
            <h1 class="title">Medlemskap</h1>

            <router-link :to="'/persons'">
                Til personregister
            </router-link>

            <div class="columns">
                <div class="column is-one-third">
                    <label class="label">Personinfo</label>
                    <div>Navn: {{person.fullName}}</div>
                </div>
                <div class="column">
                    <div class="field">
                        <label class="label">Medlemskapsinfo</label>
                        <div class="control box">
                            <label class="radio">
                                <input
                                        type="radio"
                                        name="memberYear"
                                        value="2017"
                                        v-model="membership.year"/>
                                {{new Date().getFullYear()}}
                            </label>
                            <label class="radio">
                                <input
                                        type="radio"
                                        name="memberType"
                                        value="regular"
                                        v-model="membership.type"/>
                                Ordinært (kr. 300)
                            </label>
                            <label class="radio">
                                <input
                                        type="radio"
                                        name="memberType"
                                        value="family"
                                        v-model="membership.type"/>
                                Familie (kr. 400)
                            </label>
                        </div>
                        <div class="control box">
                            <label class="radio">
                                <input
                                        type="radio"
                                        name="memberYear"
                                        value="2018"
                                        v-model="membership.year"/>
                                {{new Date().getFullYear() + 1}}
                            </label>
                            <label class="radio">
                                <input
                                        type="radio"
                                        name="memberType"
                                        value="regular"
                                        v-model="membership.type"/>
                                Ordinært (kr. 300)
                            </label>
                            <label class="radio">
                                <input
                                        type="radio"
                                        name="memberType"
                                        value="family"
                                        v-model="membership.type"/>
                                Familie (kr. 400)
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
                membership: {
                    year: '',
                    type: ''
                }
            }
        },
        methods: {
            fetchPerson () {
                if (this.authenticated) {
                    axios.get(`/api/persons/${this.personId}`, {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(response => {
                        this.person = response.data[0]
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
        }
    }
</script>