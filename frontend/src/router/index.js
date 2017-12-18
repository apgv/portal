import Vue from 'vue'
import Router from 'vue-router'
import nbNO from 'vee-validate/dist/locale/nb_NO'
import VeeValidate, {Validator} from 'vee-validate'
import Snotify, {SnotifyPosition} from 'vue-snotify'
import Home from '@/components/Home'
import Persons from '@/components/Persons'
import PersonAddOrEdit from '@/components/PersonAddOrEdit'
import Membership from '@/components/Membership'
import MembershipTypes from '@/components/MembershipTypes'
import AddMembershipType from '@/components/AddMembershipType'
import Users from '@/components/Users'
import UserAdd from '@/components/UserAdd'
import UserRoles from '@/components/UserRoles'
import Roles from '@/components/Roles'
import ExternalRouting from '@/components/ExternalRouting'
import Auth0Callback from '@/components/Auth0Callback'
import ReAuthenticate from '@/components/ReAuthenticate'
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
            path: '/personaddoredit/:personId?',
            name: 'PersonAddOrEdit',
            component: PersonAddOrEdit,
            props: true
        },
        {
            path: '/membership/:personId',
            name: 'Membership',
            component: Membership,
            props: true
        },
        {
            path: '/membershiptypes',
            name: 'MembershipTypes',
            component: MembershipTypes
        },
        {
            path: '/addmembershiptype',
            name: 'AddMembershipType',
            component: AddMembershipType
        },
        {
            path: '/users',
            name: 'Users',
            component: Users
        },
        {
            path: '/useradd',
            name: 'UserAdd',
            component: UserAdd
        },
        {
            path: '/userroles/:userId',
            name: 'UserRoles',
            component: UserRoles,
            props: true
        },
        {
            path: '/roles',
            name: 'Roles',
            component: Roles
        },
        {
            path: '/externalrouting',
            name: 'ExternalRouting',
            component: ExternalRouting
        },
        {
            path: '/auth0callback',
            name: 'Auth0Callback',
            component: Auth0Callback
        },
        {
            path: '/reauthenticate',
            name: 'ReAuthenticate',
            component: ReAuthenticate
        },
        {
            path: '/',
            redirect: '/externalrouting'
        },
        {
            path: '*',
            redirect: '/externalrouting'
        }
    ]
})

export default router
