import axios from 'axios'

export default class PersonService {
    constructor () {
        this.save = this.save.bind(this)
    }

    save (person, jwt) {
        return axios.post('api/persons', person, {headers: {'X-JWT': jwt}})
    }
}
