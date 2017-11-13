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
                member: false
            }
        },
        methods: {
            closeModal () {
                this.$emit('close-modal-add-person')
            }
        }
    }
</script>