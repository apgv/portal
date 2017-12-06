<template>
    <div class="container">
        <div v-if="authenticated">
            <h4 class="title is-4">Legg til rolle</h4>

            <div class="field">
                <label class="label">Navn</label>
                <p class="control has-icons-left">
                    <input v-model="role.name"
                           v-validate="'required|max:20'"
                           name="name"
                           class="input"
                           placeholder="Navn"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-tag"></i>
                    </span>
                    <span v-show="errors.has('name')"
                          class="help is-danger">
                        {{errors.first('name')}}
                    </span>
                </p>
            </div>

            <div>
                <button @click="save()"
                        class="button is-success">
                    Lagre
                </button>
                <router-link :to="'/roles'"
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
        name: 'role-add',
        props: ['auth', 'authenticated'],
        data () {
            return {
                role: {
                    name: null
                }
            }
        },
        methods: {
            save () {
                if (this.authenticated) {
                    axios.post('api/roles', this.role, {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(() => {
                        this.role = {
                            name: null
                        }
                        this.$validator.reset()
                        this.$snotify.success('Rollen ble lagret')
                    }).catch(error => {
                        this.$snotify.error('Feil ved lagring av rolle')
                        console.log(error)
                    })
                }
            }
        }
    }
</script>