import Vue from 'vue'
import Router from 'vue-router'
import nbNO from 'vee-validate/dist/locale/nb_NO'
import VeeValidate, {Validator} from 'vee-validate'
import Snotify, {SnotifyPosition} from 'vue-snotify'
import Home from '@/components/Home'
import Persons from '@/components/Persons'
import AddPerson from '@/components/AddPerson'
import Membership from '@/components/Membership'
import Callback from '@/components/Callback'
import moment from 'moment'

Vue.use(Router)
Vue.use(VeeValidate)
Vue.use(Snotify, {
    toast: {
        position: SnotifyPosition.centerTop
    }
})

Validator.localize('nb_NO', nbNO)
moment.locale('nb')

Vue.filter('formatDate', (value) => {
    if (value) {
        return moment(value).format('L')
    }
})

const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/home',
            name: 'Home',
            component: Home
        },
        {
            path: '/persons',
            name: 'Persons',
            component: Persons
        },
        {
            path: '/addperson',
            name: 'AddPerson',
            component: AddPerson
        },
        {
            path: '/membership/:personId',
            name: 'Membership',
            component: Membership,
            props: true
        },
        {
            path: '/callback',
            name: 'Callback',
            component: Callback
        },
        {
            path: '*',
            redirect: '/home'
        }
    ]
})

export default router
