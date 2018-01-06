<template>
    <div>
        <div v-if="authenticated">
            <h4 class="title is-4">Legg til bruker</h4>

            <div class="field">
                <label class="label">Fornavn</label>
                <p class="control has-icons-left">
                    <input v-model="user.firstName"
                           v-validate="'required|max:20'"
                           name="firstname"
                           class="input"
                           placeholder="Fornavn"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-user"></i>
                    </span>
                    <span v-show="errors.has('firstname')"
                          class="help is-danger">
                        {{errors.first('firstname')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">Etternavn</label>
                <p class="control has-icons-left">
                    <input v-model="user.lastName"
                           v-validate="'required|max:20'"
                           name="lastname"
                           class="input"
                           placeholder="Etternavn"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-user"></i>
                    </span>
                    <span v-show="errors.has('lastname')"
                          class="help is-danger">
                        {{errors.first('lastname')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">E-post</label>
                <p class="control has-icons-left">
                    <input v-model="user.email"
                           v-validate="'required|email|max:40'"
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
                    <input v-model="user.phone"
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

            <div>
                <button @click="save()"
                        class="button is-success">
                    Lagre
                </button>
                <router-link :to="'/users'"
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
    import NotAuthenticated from './NotAuthenticated'
    import axios from 'axios'

    export default {
        components: {NotAuthenticated},
        name: 'user-add',
        props: ['auth', 'authenticated'],
        data () {
            return {
                user: {
                    firstName: null,
                    lastName: null,
                    email: null,
                    phone: null
                }
            }
        },
        methods: {
            save () {
                if (this.authenticated) {
                    this.$validator.validateAll().then((result) => {
                        if (result) {
                            axios.post('api/users', this.user, {
                                headers: {'X-JWT': this.auth.jwt()}
                            }).then(() => {
                                this.user = {
                                    firstName: null,
                                    lastName: null,
                                    email: null,
                                    phone: null
                                }
                                this.$validator.reset()
                                this.$snotify.success('Bruker ble lagret')
                            }).catch(error => {
                                this.$snotify.error('Feil ved lagring av bruker')
                                console.log(error)
                            })
                        }
                    })
                }
            }
        }
    }
</script>