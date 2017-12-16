<template>
    <div>
        <div v-if="authenticated">
            <h4 class="title is-4">Rolleadministrasjon</h4>

            <div class="columns">
                <div class="column">
                    <label class="label">Brukerinfo</label>
                    <div class="box">
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Fornavn:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{user.firstName}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Etternavn:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{user.lastName}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">E-post:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{user.email}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Telefon:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{user.phone}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <div class="field">
                        <label class="label">Roller</label>
                        <div v-for="role in userRoles"
                             class="control box">
                            <label class="checkbox">
                                <input type="checkbox"
                                       :value="role.id"
                                       v-model="role.hasRole"/>
                                <span class="has-text-weight-bold">{{role.name}}</span>, {{role.description}}
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
                <router-link :to="'/users'"
                             class="button">
                    Avbryt
                </router-link>
            </div>
        </div>

        <not-authenticated
                :auth="auth"
                :authenticated="authenticated">
        </not-authenticated>
    </div>
</template>

<script>
    import NotAuthenticated from './NotAuthenticated'
    import axios from 'axios'

    export default {
        components: {NotAuthenticated},
        name: 'user-roles',
        props: ['auth', 'authenticated', 'userId'],
        data () {
            return {
                user: {},
                roles: [],
                checkedRoles: []
            }
        },
        methods: {
            fetchUser () {
                axios.get(`/api/users/${this.userId}`, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    this.user = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av bruker')
                    console.log(error)
                })
            },
            fetchRoles () {
                axios.get('/api/roles', {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    this.roles = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av roller')
                    console.log(error)
                })
            },
            save () {
                let chosenRoles = this.userRoles
                    .filter(role => {
                        return role.hasRole === true
                    }).map(role => {
                        return role.id
                    })

                axios.post('/api/userroles', {userId: this.userId, roleIds: chosenRoles}, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(() => {
                    this.$snotify.success('Rollene ble oppdatert')
                }).catch(error => {
                    this.$snotify.error('Feil ved oppdatering av roller')
                    console.log(error)
                })
            }
        },
        computed: {
            userRoles: function () {
                let userRoles = this.user.roles
                let data = this.roles

                if (userRoles && data) {
                    let userRoleIds = userRoles.map(role => {
                        return role.id
                    })

                    data.forEach(role => {
                        role.hasRole = userRoleIds.indexOf(role.id) > -1
                    })

                    return data
                } else {
                    return []
                }
            }
        },
        created () {
            this.fetchUser()
            this.fetchRoles()
        }
    }
</script>