import axios from 'axios'

export default class PersonService {
    constructor () {
        this.save = this.save.bind(this)
    }

    save (person, jwt) {
        axios.post('api/persons', person, {headers: {'X-JWT': jwt}})
            .then(response => {
                console.log(response)
            })
            .catch(error => {
                console.log(error)
            })
    }
}
