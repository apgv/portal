<template>
    <div>
        <div v-if="authenticated">

            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <h4 class="title is-4">Personregister</h4>

            <div class="columns">
                <div class="column">
                    <div class="control has-icons-left">
                        <input v-model="filterKey"
                               class="input"
                               placeholder="Søk"/>
                        <span class="icon is-left">
                            <i class="fa fa-search"></i>
                        </span>
                    </div>
                </div>
                <div class="column">
                    <div class="field is-horizontal">
                        <div class="control">
                            <label class="checkbox">
                                <input type="checkbox"
                                       :value="currentYear"
                                       v-model="membershipYearsFilter">
                                Medlem {{currentYear}}
                            </label>
                            <label class="checkbox">
                                <input type="checkbox"
                                       :value="nextYear"
                                       v-model="membershipYearsFilter">
                                Medlem {{nextYear}}
                            </label>
                            <label class="checkbox">
                                <input type="checkbox"
                                       v-model="noMembershipFilter">
                                Uten medlemskap
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <router-link :to="'/personaddoredit'">
                Legg til person
            </router-link>
            <div class="is-pulled-right">
                Viser {{filteredPersons.length}} av {{persons.length}}
            </div>
            <table class="table is-fullwidth is-striped is-hoverable">
                <thead>
                <tr>
                    <th>Navn</th>
                    <th>E-post</th>
                    <th>Telefon</th>
                    <th>Adresse</th>
                    <th>Endre</th>
                    <th>Slett</th>
                    <th>Medlem</th>
                    <th>Administrer medlemskap</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="person in filteredPersons">
                    <td>{{person.fullName}}</td>
                    <td>{{person.email}}</td>
                    <td>{{person.phone}}</td>
                    <td>{{person.address}}</td>
                    <td>
                        <router-link :to="`/personaddoredit/${person.id}`">
                            Endre
                        </router-link>
                    </td>
                    <td>
                        <a @click="showDeleteModal(person)"
                           :disabled="!hasRequiredRole()"
                           class="button icon is-text">
                            <i class="fa fa-trash"></i>
                        </a>
                    </td>
                    <td>{{hasMembership(person.memberships) ? 'Ja' : 'Nei'}}</td>
                    <td>
                        <router-link :to="`/membership/${person.id}`">
                            Medlemskap
                        </router-link>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <confirm-delete-modal :showConfirmDeleteModal="showConfirmDeleteModal"
                              :deleteFunction="deletePerson"
                              :toBeDeleted="personToDelete"
                              :toBeDeletedDisplayName="toBeDeletedDisplayName"
                              @close="hideDeleteModal">
        </confirm-delete-modal>

        <not-authenticated :auth="auth"
                           :authenticated="authenticated">
        </not-authenticated>
    </div>
</template>

<script>
import NotAuthenticated from './NotAuthenticated.vue'
import PersonAddOrEdit from './PersonAddOrEdit.vue'
import axios from 'axios'
import MissingRoles from './MissingRoles'
import {STYREMEDLEM} from '../auth/Roles'
import ConfirmDeleteModal from './ConfirmDeleteModal'

export default {
    components: {
        ConfirmDeleteModal,
        PersonAddOrEdit,
        MissingRoles,
        NotAuthenticated
    },
    name: 'Persons',
    props: ['auth', 'authenticated'],
    data () {
        return {
            requiredRoles: [STYREMEDLEM],
            persons: [],
            filterKey: '',
            currentYear: new Date().getFullYear(),
            nextYear: new Date().getFullYear() + 1,
            membershipYearsFilter: [],
            noMembershipFilter: false,
            showConfirmDeleteModal: false,
            personToDelete: null
        }
    },
    methods: {
        hasRequiredRole: function () {
            return this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles)
        },
        isAuthenticatedAndHasRequiredRole: function () {
            return this.authenticated && this.hasRequiredRole()
        },
        fetchPersons () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                axios.get('/api/persons', {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    this.persons = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av personer')
                    console.log(error)
                })
            }
        },
        hasMembership (memberships) {
            let years = memberships.map(membership => {
                return membership.year
            })

            return years.some(year => [this.currentYear, this.nextYear].includes(year))
        },
        showDeleteModal (person) {
            this.personToDelete = person
            this.showConfirmDeleteModal = true
        },
        hideDeleteModal () {
            this.personToDelete = null
            this.showConfirmDeleteModal = false
        },
        deletePerson () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                axios.delete(`/api/persons/${this.personToDelete.id}`, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(() => {
                    let index = this.persons.indexOf(this.personToDelete)

                    if (index > -1) {
                        this.persons.splice(index, 1)
                    }

                    this.hideDeleteModal()
                    this.$snotify.success('Person ble slettet')
                }).catch(error => {
                    this.hideDeleteModal()
                    this.$snotify.error('Feil ved sletting av person. NB En person som skal slettes kan ikke ha noen medlemskap knyttet til seg. Slett alle medlemskap først.')
                    console.log(error)
                })
            }
        }
    },
    computed: {
        filteredPersons: function () {
            let data = this.persons
            let filterKey = this.filterKey && this.filterKey.toLowerCase()

            if (filterKey) {
                data = data.filter(person => {
                    return person.fullName.toLowerCase().indexOf(filterKey) > -1 ||
                        person.email.toLowerCase().indexOf(filterKey) > -1 ||
                        person.phone.indexOf(filterKey) > -1 ||
                        person.address.toLowerCase().indexOf(filterKey) > -1
                })
            }

            if (this.membershipYearsFilter.length > 0) {
                data = data.filter(person => {
                    let years = person.memberships.map(membership => {
                        return membership.year
                    })

                    return years.some(year => this.membershipYearsFilter.includes(year))
                })
            }

            if (this.noMembershipFilter) {
                data = data.filter(person => {
                    return !this.hasMembership(person.memberships)
                })
            }

            return data
        },
        toBeDeletedDisplayName: function () {
            return this.personToDelete ? this.personToDelete.fullName : ''
        }
    },
    created () {
        this.fetchPersons()
    }
}
</script>
