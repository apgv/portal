<template>
    <div class="hello">
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
        name: 'PersonRegistry',
        data () {
            return {
                persons: [
                    {
                        id: 1,
                        firstName: 'Ben Redic',
                        lastName: 'Fy Fazan',
                        email: 'ben.redic.fy.fazan@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 2,
                        firstName: 'Frimand',
                        lastName: 'Pløsen',
                        email: 'frimand.plosen@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 3,
                        firstName: 'Hallstein',
                        lastName: 'Bronskimlet d.a.y.',
                        email: 'hallstein.bronskimlet.day@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 4,
                        firstName: 'Ludvig',
                        lastName: '',
                        email: 'ludvig@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 5,
                        firstName: 'Melvind',
                        lastName: 'Snerken',
                        email: 'melvind.snerken@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 6,
                        firstName: 'Myrullbråten',
                        lastName: '',
                        email: 'myrullbraten@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 7,
                        firstName: 'Mysil',
                        lastName: 'Bergsprekken',
                        email: 'mysil.bergsprekken@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 8,
                        firstName: 'Reodor',
                        lastName: 'Felgen',
                        email: 'reodor.felgen@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 9,
                        firstName: 'Rudolf',
                        lastName: 'Blodstrupmoen',
                        email: 'rudolf.blodstrupmoen@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 10,
                        firstName: 'Sindre',
                        lastName: 'Piltingsrud',
                        email: 'sindre.piltingsrud@example.com',
                        phone: '41234567',
                        member: false
                    },
                    {
                        id: 11,
                        firstName: 'Solan',
                        lastName: 'Gundersen',
                        email: 'solan.gundersen@example.com',
                        phone: '41234567',
                        member: false

                    }]
            }
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
