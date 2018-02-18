<template>
    <div>
        <div v-if="authenticated">
            <h4 class="title is-4">Medlemskap</h4>

            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <div class="columns">
                <div class="column">
                    <label class="label">Personinfo</label>
                    <div class="box">
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Navn:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.fullName}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">E-post:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.email}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Telefon:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.phone}}
                                </div>
                            </div>
                        </div>
                        <div class="field is-horizontal">
                            <div class="field-label">
                                <label class="label">Adresse:</label>
                            </div>
                            <div class="field-body">
                                <div class="field">
                                    {{person.address}}
                                </div>
                            </div>
                        </div>
                    </div>

                    <label class="label">Eksisterende medlemskap</label>
                    <div v-for="membership in person.memberships"
                         class="columns">
                        <div class="column">
                            {{membership.year}} {{membership.type}}
                        </div>
                        <div class="column">
                            <a @click="deleteMembership(membership)"
                               :disabled="!hasRequiredRole()"
                               class="button icon">
                                <i class="fa fa-trash"></i>
                            </a>
                        </div>
                        <div class="column"></div>
                    </div>
                </div>
                <div class="column">
                    <div class="field">
                        <label class="label">Velg medlemskap</label>
                        <div v-for="membershipType in membershipTypes"
                             class="control box">
                            <label class="radio">
                                <input type="radio"
                                       :value="membershipType"
                                       v-model="membership"/>
                                {{membershipType.year}} {{membershipType.type}} (kr. {{membershipType.price}}).
                            </label>
                            <label>
                                Innbetalingsdato
                                <flat-pickr v-model="membershipType.paymentDate"
                                            :config="flatPickrConfig"
                                            placeholder="Velg dato">
                                </flat-pickr>
                                <span class="icon">
                                    <i class="fa fa-calendar"></i>
                                </span>
                            </label>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <button @click="save()"
                        :disabled="!formIsValid || !hasRequiredRole()"
                        class="button is-success">
                    Lagre
                </button>
                <router-link :to="'/persons'"
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
import NotAuthenticated from './NotAuthenticated.vue'
import axios from 'axios'
import flatPickr from 'vue-flatpickr-component'
import {Norwegian} from 'flatpickr/dist/l10n/no'
import 'flatpickr/dist/flatpickr.css'
import moment from 'moment'
import MissingRoles from './MissingRoles'
import {KASSERER, STYRELEDER} from '../auth/Roles'

export default {
    name: 'Membership',
    components: {
        MissingRoles,
        NotAuthenticated,
        flatPickr
    },
    props: ['auth', 'authenticated', 'personId'],
    data () {
        return {
            requiredRoles: [KASSERER, STYRELEDER],
            person: {
                fullName: null,
                email: null,
                phone: null
            },
            membershipTypes: [],
            membership: null,
            flatPickrConfig: {
                locale: Norwegian,
                dateFormat: 'd.m.Y'
            }
        }
    },
    methods: {
        hasRequiredRole: function () {
            return this.authenticated && this.auth.hasOneOfTheRoles(this.requiredRoles)
        },
        isAuthenticatedAndHasRequiredRole: function () {
            return this.authenticated && this.hasRequiredRole()
        },
        fetchPerson () {
            if (this.authenticated && this.auth.isSTYREMEDLEM()) {
                axios.get(`/api/persons/${this.personId}`, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(response => {
                    this.person = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av person')
                    console.log(error)
                })
            }
        },
        fetchMembershipTypes () {
            if (this.authenticated && this.auth.isSTYREMEDLEM()) {
                axios.get('/api/membershiptypes', {
                    headers: {'X-JWT': this.auth.jwt()},
                    params: {'active': true}
                }).then(response => {
                    this.membershipTypes = response.data
                }).catch(error => {
                    this.$snotify.error('Feil ved henting av medlemskapstyper')
                    console.log(error)
                })
            }
        },
        save () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                let norwegianDate = moment(this.membership.paymentDate, 'DD.MM.YYYY')

                let membership = {
                    personId: this.person.id,
                    membershipTypeId: this.membership.id,
                    paymentDate: norwegianDate.format('YYYY-MM-DD')
                }

                axios.post('/api/memberships', membership, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(() => {
                    this.membership.paymentDate = null
                    this.membership = null
                    this.$snotify.success('Medlemskap ble lagret')
                    this.fetchPerson()
                }).catch(error => {
                    this.$snotify.error('Feil ved lagring av medlemskap')
                    console.log(error)
                })
            }
        },
        deleteMembership (membership) {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                axios.delete(`/api/memberships/${membership.id}`, {
                    headers: {'X-JWT': this.auth.jwt()}
                }).then(() => {
                    let index = this.person.memberships.indexOf(membership)

                    if (index > -1) {
                        this.person.memberships.splice(index, 1)
                    }

                    this.$snotify.success('Medlemskap ble slettet')
                }).catch(error => {
                    this.$snotify.error('Feil ved sletting av medlemskap')
                    console.log(error)
                })
            }
        }
    },
    computed: {
        formIsValid: function () {
            return this.membership && this.membership.paymentDate
        }
    },
    created () {
        this.fetchPerson()
        this.fetchMembershipTypes()
    }
}
</script>