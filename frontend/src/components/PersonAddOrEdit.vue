<template>
    <div>
        <div v-if="authenticated">
            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <h4 v-if="personId"
                class="title is-4">
                Endre person
            </h4>
            <h4 v-else
                class="title is-4">
                Legg til person
            </h4>

            <div class="field">
                <label class="label">Navn</label>
                <p class="control has-icons-left">
                    <input v-model="person.fullName"
                           v-validate="'required|max:54'"
                           name="name"
                           class="input"
                           placeholder="Navn"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-user"></i>
                    </span>
                    <span v-show="errors.has('name')"
                          class="help is-danger">
                        {{errors.first('name')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">E-post</label>
                <p class="control has-icons-left">
                    <input v-model="person.email"
                           v-validate="'email|max:40'"
                           name="email"
                           class="input"
                           type="email"
                           placeholder="E-post"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-envelope"></i>
                    </span>
                    <span v-show="errors.has('email')"
                          class="help is-danger">
                        {{errors.first('email')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">Telefon</label>
                <p class="control has-icons-left">
                    <input v-model="person.phone"
                           v-validate="'digits:8'"
                           name="phone"
                           class="input"
                           placeholder="Telefon"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-mobile"></i>
                    </span>
                    <span v-show="errors.has('phone')"
                          class="help is-danger">
                        {{errors.first('phone')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">Adresse</label>
                <p class="control has-icons-left">
                    <input v-model="person.address"
                           v-validate="'max:50'"
                           name="address"
                           class="input"
                           placeholder="Adresse"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-map-marker"></i>
                    </span>
                    <span v-show="errors.has('address')"
                          class="help is-danger">
                        {{errors.first('address')}}
                    </span>
                </p>
            </div>

            <div>
                <button @click="save()"
                        :disabled="auth.hasNoneOfTheRoles(requiredRoles)"
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
import router from '../router'
import MissingRoles from './MissingRoles'
import {STYREMEDLEM} from '../auth/Roles'

export default {
    name: 'PersonAddOrEdit',
    components: {
        MissingRoles,
        NotAuthenticated
    },
    props: ['auth', 'authenticated', 'personId'],
    data () {
        return {
            requiredRoles: [STYREMEDLEM],
            person: {
                fullName: null,
                email: null,
                phone: null,
                address: null
            }
        }
    },
    methods: {
        fetchPerson () {
            if (this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles) && this.personId) {
                axios.get(`/api/persons/${this.personId}`, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    this.person = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av person')
                    console.log(error)
                })
            }
        },
        save () {
            if (this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles)) {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        const successCallback = () => {
                            this.person = {
                                fullName: null,
                                email: null,
                                phone: null,
                                address: null
                            }

                            this.$validator.reset()
                            this.$snotify.success('Person ble lagret')
                        }

                        const errorCallback = (error) => {
                            this.$snotify.error('Feil ved lagring av person')
                            console.log(error)
                        }

                        if (this.personId) {
                            axios.put('/api/persons', this.person, {
                                headers: {'X-JWT': this.auth.jwt()}
                            }).then(successCallback)
                                .then(() => {
                                    router.replace('/persons')
                                })
                                .catch(errorCallback)
                        } else {
                            axios.post('/api/persons', this.person, {
                                headers: {'X-JWT': this.auth.jwt()}
                            }).then(successCallback)
                                .catch(errorCallback)
                        }
                    }
                })
            }
        }
    },
    created () {
        this.fetchPerson()
    }
}
</script>