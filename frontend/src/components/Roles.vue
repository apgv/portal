<template>
    <div>
        <div v-if="authenticated">

            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <h4 class="title is-4">Roller</h4>

            <table class="table is-fullwidth is-striped is-hoverable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Navn</th>
                    <th>Beskrivelse</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="role in roles">
                    <td>{{role.id}}</td>
                    <td>{{role.name}}</td>
                    <td>{{role.description}}</td>
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
    name: 'roles',
    props: ['auth', 'authenticated'],
    data () {
        return {
            requiredRoles: [STYREMEDLEM],
            roles: []
        }
    },
    methods: {
        hasRequiredRole: function () {
            return this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles)
        },
        isAuthenticatedAndHasRequiredRole: function () {
            return this.authenticated && this.hasRequiredRole()
        },
        fetchRoles () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
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