<template>
    <nav class="navbar" role="navigation" aria-label="main navigation">
        <div v-if="showNavBurger">
            <div class="navbar-brand">
                <div class="navbar-burger"
                     v-bind:class="{'is-active': showMobileMenu}"
                     @click="toggleMobileMenu()">
                    <span></span>
                    <span></span>
                    <span></span>
                </div>
            </div>
            <div class="navbar-menu"
                 v-bind:class="{'is-active': showMobileMenu}">
                <div class="navbar-start">
                    <div class="navbar-item">
                        <div class="navbar-dropdown">
                            <router-link :to="'/home'"
                                         @click.native="toggleMobileMenu()"
                                         class="navbar-item">
                                Forside
                            </router-link>
                            <router-link :to="'/persons'"
                                         @click.native="toggleMobileMenu()"
                                         class="navbar-item">
                                Personregister
                            </router-link>
                            <router-link :to="'/membershiptypes'"
                                         @click.native="toggleMobileMenu()"
                                         class="navbar-item">
                                Medlemskapstyper
                            </router-link>
                            <router-link :to="'/users'"
                                         @click.native="toggleMobileMenu()"
                                         class="navbar-item">
                                Brukere
                            </router-link>
                            <router-link :to="'/roles'"
                                         @click.native="toggleMobileMenu()"
                                         class="navbar-item">
                                Roller
                            </router-link>
                            <a v-if="!authenticated"
                               @click="login()"
                               class="navbar-item">
                                Logg inn
                            </a>
                            <a v-if="authenticated"
                               @click="logout()"
                               class="navbar-item">
                                Logg ut
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="navbar-menu">
            <div class="navbar-start">
                <router-link :to="'/home'"
                             class="navbar-item">
                    Forside
                </router-link>
                <router-link :to="'/persons'"
                             class="navbar-item">
                    Personregister
                </router-link>
                <router-link :to="'/membershiptypes'"
                             class="navbar-item">
                    Medlemskapstyper
                </router-link>
                <router-link
                        :to="'/users'"
                        class="navbar-item">
                    Brukere
                </router-link>
                <router-link :to="'/roles'"
                             class="navbar-item">
                    Roller
                </router-link>
            </div>
            <div class="navbar-end">
                <div v-if="authenticated" class="navbar-item">
                    {{loggedInUser}}
                </div>
                <div class="navbar-item">
                    <a v-if="!authenticated"
                       @click="login()"
                       class="button is-link">
                        Logg inn
                    </a>
                    <a v-if="authenticated"
                       @click="logout()"
                       class="button is-link">
                        Logg ut
                    </a>
                </div>
            </div>
        </div>
    </nav>
</template>

<script>
import {eventBus} from '../main'

export default {
    name: 'app-menu',
    props: ['auth', 'authenticated'],
    data () {
        eventBus.$on('subjectResolved', () => {
            this.setLoggedInUser()
        })
        return {
            showMobileMenu: false,
            loggedInUser: null
        }
    },
    methods: {
        toggleMobileMenu () {
            this.showMobileMenu = !this.showMobileMenu
        },
        login () {
            this.auth.login()
        },
        logout () {
            this.auth.logout()
            this.toggleMobileMenu()
        },
        setLoggedInUser () {
            if (this.auth.getSubject()) {
                this.loggedInUser = `${this.auth.getSubject().firstName} (${this.auth.getSubject().email})`
            } else {
                return ''
            }
        }
    },
    computed: {
        showNavBurger: function () {
            // navbar-menu is hidden on touch devices < 1024px
            return document.documentElement.clientWidth < 1024
        }
    },
    created () {
        this.setLoggedInUser()
    }
}
</script>