<template>
    <div>
        <div v-if="authenticated">

            <missing-roles :auth="auth"
                           :authenticated="authenticated"
                           :requiredRoles="requiredRoles">
            </missing-roles>

            <h4 class="title is-4">Legg til medlemskapstype</h4>

            <div class="field">
                <label class="label">År</label>
                <p class="control has-icons-left">
                    <input v-model="membershipType.year"
                           v-validate="'required|digits:4'"
                           name="year"
                           class="input"
                           placeholder="År"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-calendar-o"></i>
                    </span>
                    <span v-show="errors.has('year')"
                          class="help is-danger">
                        {{errors.first('year')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">Type</label>
                <p class="control has-icons-left">
                    <input v-model="membershipType.type"
                           v-validate="'required|max:25'"
                           name="type"
                           class="input"
                           placeholder="Type"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-tag"></i>
                    </span>
                    <span v-show="errors.has('type')"
                          class="help is-danger">
                        {{errors.first('type')}}
                    </span>
                </p>
            </div>

            <div class="field">
                <label class="label">Pris</label>
                <p class="control has-icons-left">
                    <input v-model="membershipType.price"
                           v-validate="'required|numeric'"
                           name="price"
                           class="input"
                           placeholder="Pris"/>
                    <span class="icon is-small is-left">
                        <i class="fa fa-dollar"></i>
                    </span>
                    <span v-show="errors.has('price')"
                          class="help is-danger">
                        {{errors.first('price')}}
                    </span>
                </p>
            </div>


            <div>
                <button @click="save()"
                        :disabled="!hasRequiredRole()"
                        class="button is-success">
                    Lagre
                </button>
                <router-link :to="'/membershiptypes'"
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
import MissingRoles from './MissingRoles'
import {KASSERER, STYRELEDER} from '../auth/Roles'

export default {
    components: {
        MissingRoles,
        NotAuthenticated
    },
    name: 'AddMembershipType',
    props: ['auth', 'authenticated'],
    data () {
        return {
            requiredRoles: [KASSERER, STYRELEDER],
            membershipType: {
                year: null,
                type: null,
                price: null
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
        save () {
            if (this.isAuthenticatedAndHasRequiredRole()) {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        axios.post('api/membershiptypes', this.membershipType, {
                            headers: {'X-JWT': this.auth.jwt()}
                        }).then(() => {
                            this.membershipType = {
                                year: null,
                                type: null,
                                price: null
                            }
                            this.$validator.reset()
                            this.$snotify.success('Medlemskapstype ble lagret')
                        }).catch(error => {
                            this.$snotify.error('Feil ved lagring av medlemskapstype')
                            console.log(error)
                        })
                    }
                })
            }
        }
    }
}
</script>