<template>
    <div>
        <div v-if="authenticated">

            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <h4 class="title is-4">Brukere</h4>

            <router-link :to="'/useradd'">
                Legg til bruker
            </router-link>

            <table class="table is-fullwidth is-striped is-hoverable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Fornavn</th>
                    <th>Etternavn</th>
                    <th>E-post</th>
                    <th>Telefon</th>
                    <th>Roller</th>
                    <th>Registrert av</th>
                    <th>Registrert dato</th>
                    <th>Administrer roller</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in users">
                    <td>{{user.id}}</td>
                    <td>{{user.firstName}}</td>
                    <td>{{user.lastName}}</td>
                    <td>{{user.email}}</td>
                    <td>{{user.phone}}</td>
                    <td>{{user.roles}}</td>
                    <td>{{user.createdBy}}</td>
                    <td>{{user.createdDate | formatDate}}</td>
                    <td>
                        <router-link :to="`userroles/${user.id}`">
                            Administrer roller
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
import NotAuthenticated from './NotAuthenticated'
import axios from 'axios'
import MissingRoles from './MissingRoles'
import {STYREMEDLEM} from '../auth/Roles'

export default {
    components: {
        MissingRoles,
        NotAuthenticated
    },
    name: 'users',
    props: ['auth', 'authenticated'],
    data () {
        return {
            requiredRoles: [STYREMEDLEM],
            users: []
        }
    },
    methods: {
        hasRequiredRole: function () {
            return this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles)
        },
        isAuthenticatedAndHasRequiredRole: function () {
            return this.authenticated && this.hasRequiredRole()
        },
        fetchUsers () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                axios.get('/api/users', {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    let users = response.data
                    users.forEach(user => {
                        user.roles = user.roles
                            .map(role => {
                                return role.name
                            })
                            .join(', ')
                    })
                    this.users = users
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av brukere')
                    console.log(error)
                })
            }
        }
    },
    created () {
        this.fetchUsers()
    }
}
</script>