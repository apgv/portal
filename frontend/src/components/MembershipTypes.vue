<template>
    <div>
        <div v-if="authenticated">

            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <h4 class="title is-4">Medlemskapstyper</h4>

            <router-link :to="'/addmembershiptype'">
                Legg til medlemskapstype
            </router-link>
            <table class="table is-fullwidth is-striped is-hoverable">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>År</th>
                    <th>Type</th>
                    <th>Pris</th>
                    <th>Aktiv medlemskapstype</th>
                    <th>Registrert av</th>
                    <th>Registrert dato</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="membershipType in membershipTypes">
                    <td>{{membershipType.id}}</td>
                    <td>{{membershipType.year}}</td>
                    <td>{{membershipType.type}}</td>
                    <td>{{membershipType.price}}</td>
                    <td>{{membershipType.active? 'Ja': 'Nei'}}</td>
                    <td>{{membershipType.createdBy}}</td>
                    <td>{{membershipType.createdDate|formatDate}}</td>
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
    name: 'MembershipTypes',
    components: {
        MissingRoles,
        NotAuthenticated
    },
    props: ['auth', 'authenticated'],
    data () {
        return {
            requiredRoles: [STYREMEDLEM],
            membershipTypes: []
        }
    },
    methods: {
        hasRequiredRole: function () {
            return this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles)
        },
        isAuthenticatedAndHasRequiredRole: function () {
            return this.authenticated && this.hasRequiredRole()
        },
        fetchMembershipTypes () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                axios.get('/api/membershiptypes', {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    this.membershipTypes = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av medlemskapstyper')
                    console.log(error)
                })
            }
        }
    },
    created () {
        this.fetchMembershipTypes()
    }
}
</script>