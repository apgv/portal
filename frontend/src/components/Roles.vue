<template>
    <div class="container">
        <div v-if="authenticated">
            <h4 class="title is-4">Roller</h4>

            <router-link :to="'/roleadd'">
                Legg til rolle
            </router-link>

            <table class="table is-fullwidth is-striped is-hoverable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Rollenavn</th>
                    <th>Rollen er aktiv</th>
                    <th>Registrert av</th>
                    <th>Registrert dato</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="role in roles">
                    <td>{{role.id}}</td>
                    <td>{{role.name}}</td>
                    <td>{{role.active? 'Ja':'Nei'}}</td>
                    <td>{{role.createdBy}}</td>
                    <td>{{role.createdDate | formatDate}}</td>
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
    import NotAuthenticated from './NotAuthenticated'
    import axios from 'axios'

    export default {
        components: {NotAuthenticated},
        name: 'roles',
        props: ['auth', 'authenticated'],
        data () {
            return {
                roles: []
            }
        },
        methods: {
            fetchRoles () {
                if (this.authenticated) {
                    axios.get('api/roles', {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(response => {
                        this.roles = response.data
                    }).catch(error => {
                        this.$snotify.error('Feil ved henting av roller')
                        console.log(error)
                    })
                }
            }
        },
        created () {
            this.fetchRoles()
        }
    }
</script>