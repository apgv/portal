import Vue from 'vue'
import PersonRegistry from '@/components/PersonRegistry'

describe('PersonRegistry.vue', () => {
    it('should render correct contents', () => {
        const Constructor = Vue.extend(PersonRegistry)
        const vm = new Constructor().$mount()
        expect(vm.$el.querySelector('.hello h1').textContent)
            .to.equal('Personregister')
    })
})
