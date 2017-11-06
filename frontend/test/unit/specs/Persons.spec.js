import Vue from 'vue'
import Persons from '@/components/Persons'

describe('Persons.vue', () => {
    it('should render correct contents', () => {
        const Constructor = Vue.extend(Persons)
        const vm = new Constructor().$mount()
        expect(vm.$el.querySelector('.hello h1').textContent)
            .to.equal('Personregister')
    })
})
