import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'
import PersonRegistry from '@/components/PersonRegistry'
import Callback from '@/components/Callback'

Vue.use(Router)
Vue.use(VueResource)

const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/home',
            name: 'Home',
            component: PersonRegistry
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
