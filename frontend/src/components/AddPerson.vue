<template>
    <div class="container">
        <h4 class="title is-4">Legg til person</h4>

        <div class="field">
            <label class="label">Navn</label>
            <p class="control has-icons-left">
                <input v-model="person.fullName"
                       v-validate="'required|max:54'"
                       name="name"
                       class="input"
                       placeholder="Navn"/>
                <span class="icon is-small is-left">
                    <i class="fa fa-user"></i>
                </span>
                <span v-show="errors.has('name')"
                      class="help is-danger">
                    {{errors.first('name')}}
                </span>
            </p>
        </div>

        <div class="field">
            <label class="label">E-post</label>
            <p class="control has-icons-left">
                <input v-model="person.email"
                       v-validate="'email|max:40'"
                       name="email"
                       class="input"
                       type="email"
                       placeholder="E-post"/>
                <span class="icon is-small is-left">
                    <i class="fa fa-envelope"></i>
                </span>
                <span v-show="errors.has('email')"
                      class="help is-danger">
                    {{errors.first('email')}}
                </span>
            </p>
        </div>

        <div class="field">
            <label class="label">Telefon</label>
            <p class="control has-icons-left">
                <input v-model="person.phone"
                       v-validate="'digits:8'"
                       name="phone"
                       class="input"
                       placeholder="Telefon"/>
                <span class="icon is-small is-left">
                    <i class="fa fa-mobile"></i>
                </span>
                <span v-show="errors.has('phone')"
                      class="help is-danger">
                    {{errors.first('phone')}}
                </span>
            </p>
        </div>

        <div class="field">
            <label class="label">Adresse</label>
            <p class="control has-icons-left">
                <input v-model="person.address"
                       v-validate="'max:50'"
                       name="address"
                       class="input"
                       placeholder="Adresse"/>
                <span class="icon is-small is-left">
                    <i class="fa fa-map-marker"></i>
                </span>
                <span v-show="errors.has('address')"
                      class="help is-danger">
                    {{errors.first('address')}}
                </span>
            </p>
        </div>
        
        <div>
            <button @click="save()"
                    class="button is-success">
                Lagre
            </button>
            <router-link :to="'/persons'"
                         class="button">
                Avbryt
            </router-link>
        </div>
    </div>
</template>

<script>
    import PersonService from '../PersonService'

    const personService = new PersonService()

    export default {
        name: 'AddPerson',
        props: ['auth'],
        data () {
            return {
                person: {
                    fullName: null,
                    email: null,
                    phone: null,
                    address: null
                }
            }
        },
        methods: {
            save () {
                this.$validator.validateAll().then((result) => {
                    if (result) {
                        personService.save(this.person, this.auth.jwt())
                    }
                })
            }
        }
    }
</script>