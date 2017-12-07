import Vue from 'vue'
import Router from 'vue-router'
import nbNO from 'vee-validate/dist/locale/nb_NO'
import VeeValidate, {Validator} from 'vee-validate'
import Snotify, {SnotifyPosition} from 'vue-snotify'
import Home from '@/components/Home'
import Persons from '@/components/Persons'
import AddPerson from '@/components/AddPerson'
import Membership from '@/components/Membership'
import MembershipTypes from '@/components/MembershipTypes'
import AddMembershipType from '@/components/AddMembershipType'
import Subjects from '@/components/Subjects'
import SubjectAdd from '@/components/SubjectAdd'
import SubjectAdminRoles from '@/components/SubjectAdminRoles'
import Roles from '@/components/Roles'
import RoleAdd from '@/components/RoleAdd'
import Auth0Callback from '@/components/Auth0Callback'
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
            path: '/subjects',
            name: 'Subjects',
            component: Subjects
        },
        {
            path: '/subjectadd',
            name: 'SubjectAdd',
            component: SubjectAdd
        },
        {
            path: '/subjectadminroles/:subjectId',
            name: 'SubjectAdminRoles',
            component: SubjectAdminRoles,
            props: true
        },
        {
            path: '/roles',
            name: 'Roles',
            component: Roles
        },
        {
            path: '/roleadd',
            name: 'RoleAdd',
            component: RoleAdd
        },
        {
            path: '/auth0callback',
            name: 'Auth0Callback',
            component: Auth0Callback
        },
        {
            path: '/',
            redirect: '/auth0callback'
        },
        {
            path: '*',
            redirect: '/home'
        }
    ]
})

export default router
