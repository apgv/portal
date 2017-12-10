<template>
    <div class="container">
        <div v-if="authenticated">
            <h4 class="title is-4">Brukere</h4>
            <router-link :to="'/subjectadd'">
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
                <tr v-for="subject in subjects">
                    <td>{{subject.id}}</td>
                    <td>{{subject.firstName}}</td>
                    <td>{{subject.lastName}}</td>
                    <td>{{subject.email}}</td>
                    <td>{{subject.phone}}</td>
                    <td>{{subject.roles}}</td>
                    <td>{{subject.createdBy}}</td>
                    <td>{{subject.createdDate | formatDate}}</td>
                    <td>
                        <router-link :to="`subjectroles/${subject.id}`">
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

    export default {
        components: {NotAuthenticated},
        name: 'subjects',
        props: ['auth', 'authenticated'],
        data () {
            return {
                subjects: []
            }
        },
        methods: {
            fetchUsers () {
                if (this.authenticated) {
                    axios.get('/api/subjects', {
                        headers: {'X-JWT': this.auth.jwt()}
                    }).then(response => {
                        let subjects = response.data
                        subjects.forEach(subject => {
                            subject.roles = subject.roles
                                .map(role => {
                                    return role.name
                                })
                                .join(', ')
                        })
                        this.subjects = subjects
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