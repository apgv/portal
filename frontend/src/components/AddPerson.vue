<template>
    <div class="modal"
         :class="{'is-active': showModal}">
        <div class="modal-background">
        </div>
        <div class="modal-card">
            <header class="modal-card-head">
                <p class="modal-card-title">Legg til person</p>
                <button @click="closeModal()"
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
                                <input v-model="person.name"
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
                    </div>
                </div>
                <div class="field is-horizontal">
                    <div class="field-label">
                        <label class="label">E-post</label>
                    </div>
                    <div class="field-body">
                        <div class="field">
                            <p class="control has-icons-left">
                                <input v-model="person.email"
                                       v-validate="'required|email|max:40'"
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
                    </div>
                </div>
                <div class="field is-horizontal">
                    <div class="field-label">
                        <label class="label">Telefon</label>
                    </div>
                    <div class="field-body">
                        <div class="field">
                            <p class="control has-icons-left">
                                <input v-model="person.phone"
                                       v-validate="'required|digits:8'"
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
                                    <input v-model="person.member.checked"
                                           type="checkbox"/>
                                    Medlem
                                </label>
                            </div>
                        </div>
                        <div v-if="person.member.checked"
                             class="field">
                            <p class="control">
                                <input v-model="person.member.year"
                                       v-validate="'required|date_format:YYYY|date_between:2017,2018,[]'"
                                       name="year_membership"
                                       class="input"
                                       placeholder="Årstall medlemsskap"/>
                            </p>
                            <span v-show="errors.has('year_membership')"
                                  class="help is-danger">
                                {{errors.first('year_membership')}}
                            </span>
                        </div>
                    </div>
                </div>
            </section>
            <footer class="modal-card-foot">
                <button @click="closeModal()"
                        class="button is-success">
                    Lagre
                </button>
                <button @click="closeModal()"
                        class="button">
                    Avbryt
                </button>
            </footer>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'add-person',
        props: ['auth', 'authenticated', 'showModal'],
        data () {
            return {
                person: {
                    name: '',
                    email: '',
                    phone: '',
                    member: {
                        checked: false,
                        year: ''
                    }
                }
            }
        },
        methods: {
            resetModel () {
                this.person = {
                    name: '',
                    email: '',
                    phone: '',
                    member: {
                        checked: false,
                        year: ''
                    }
                }
            },
            closeModal () {
                this.resetModel()
                this.$validator.errors.clear()
                this.$emit('close-modal-add-person')
            }
        }
    }
</script>