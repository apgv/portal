<template>
    <div v-if="authenticated"
         class="hello">
        <h1>Personregister</h1>

        <table>
            <thead>
            <tr>
                <td>ID</td>
                <td>Fornavn</td>
                <td>Etternavn</td>
                <td>E-post</td>
                <td>Telefon</td>
                <td>Medlem</td>
            </tr>
            </thead>
            <tbody>
            <tr v-for="person in persons">
                <td>{{person.id}}</td>
                <td>{{person.firstName}}</td>
                <td>{{person.lastName}}</td>
                <td>{{person.email}}</td>
                <td>{{person.phone}}</td>
                <td>{{person.member ? 'Ja' : 'Nei'}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: 'Persons',
        props: ['authenticated'],
        data () {
            return {
                persons: []
            }
        },
        methods: {
            fetchPersons () {
                if (this.authenticated) {
                    this.$http.get('api/persons').then(response => {
                        this.persons = response.body
                    })
                }
            }
        },
        created () {
            this.fetchPersons()
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    h1, h2 {
        font-weight: normal;
    }

    ul {
        list-style-type: none;
        padding: 0;
    }

    li {
        display: inline-block;
        margin: 0 10px;
    }

    a {
        color: #42b983;
    }

    table {
        border-collapse: collapse;
        width: 100%;
    }

    th, td {
        padding: 8px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    tr:hover {
        background-color: #f5f5f5
    }
</style>
