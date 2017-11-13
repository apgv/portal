<template>
    <div>
        <div v-if="authenticated">
            <h1 class="title">Personregister</h1>

            <div class="modal"
                 :class="{'is-active': showModal}">
                <div class="modal-background">
                </div>
                <div class="modal-card">
                    <header class="modal-card-head">
                        <p class="modal-card-title">Registrer person</p>
                        <button @click="toggleModal()"
                                class="delete"
                                aria-label="close"></button>
                    </header>
                    <section class="modal-card-body">
                        <!-- Content ... -->
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Navn</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input class="input" placeholder="Navn"/>
                                        <span class="icon is-small is-left">
                                    <i class="fa fa-user"></i>
                                </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">E-post</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input class="input" type="email" placeholder="E-post"/>
                                        <span class="icon is-small is-left">
                                            <i class="fa fa-envelope"></i>
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Telefon</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    <p class="control has-icons-left">
                                        <input class="input" placeholder="Telefon"/>
                                        <span class="icon is-small is-left">
                                            <i class="fa fa-mobile"></i>
                                        </span>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Medlemsskap</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    <div class="control">
                                        <label class="checkbox">
                                            <input type="checkbox" v-model="member"/>
                                            Medlem
                                        </label>
                                    </div>
                                </div>
                                <div v-if="member"
                                     class="field">
                                    <p class="control">
                                        <input class="input" placeholder="Årstall medlemsskap"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </section>
                    <footer class="modal-card-foot">
                        <button @click="toggleModal()"
                                class="button is-success">
                            Lagre
                        </button>
                        <button @click="toggleModal()"
                                class="button">
                            Avbryt
                        </button>
                    </footer>
                </div>
            </div>
            <button @click="toggleModal()"
                    class="button">
                Registrer
            </button>
            <table class="table is-striped is-hoverable">
                <thead>
                <tr>
                    <td>ID</td>
                    <td>Fornavn</td>
                    <td>Etternavn</td>
                    <td>E-post</td>
                    <td>Telefon</td>
                    <td>Medlem</td>
                </tr>
                </thead>
                <tbody>
                <tr v-for="person in persons">
                    <td>{{person.id}}</td>
                    <td>{{person.firstName}}</td>
                    <td>{{person.lastName}}</td>
                    <td>{{person.email}}</td>
                    <td>{{person.phone}}</td>
                    <td>{{person.member ? 'Ja' : 'Nei'}}</td>
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
    import NotAuthenticated from './NotAuthenticated.vue'

    export default {
        components: {NotAuthenticated},
        name: 'Persons',
        props: ['auth', 'authenticated'],
        data () {
            return {
                persons: [],
                showModal: true,
                member: false
            }
        },
        methods: {
            fetchPersons () {
                let jwt = localStorage.getItem('id_token')
                if (this.authenticated) {
                    this.$http.get('api/persons', {headers: {'X-JWT': jwt}}).then(response => {
                        this.persons = response.body
                    })
                }
            },
            toggleModal () {
                this.showModal = !this.showModal
            }
        },
        created () {
            this.fetchPersons()
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
