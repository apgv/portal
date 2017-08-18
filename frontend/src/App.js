import React, {Component} from 'react';
import NavigationDrawer from "react-md/lib/NavigationDrawers";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import NavLink from "./NavLink";
import Home from "./home/Home";
import Auth from "./auth/Auth";
import Callback from "./callback/Callback";
import Persons from "./persons/Persons";

const navItems = [{
    exact: true,
    label: 'Hjem',
    to: '/',
    icon: 'home',
}, {
    label: 'Personer',
    to: '/persons',
    icon: 'people',
}];

let auth;

const handleAuthentication = (nextState, replace) => {
    if (/access_token|id_token|error/.test(nextState.location.hash)) {
        auth.handleAuthentication(nextState.location.hash);
    }
};

class App extends Component {

    render() {
        auth = new Auth(this.props.auth0Config);

        return (
            <BrowserRouter>
                <Route
                    render={({location}) => (
                        <NavigationDrawer
                            drawerTitle="Portal"
                            toolbarTitle="Skotbu Vel"
                            navItems={navItems.map(props => <NavLink {...props} key={props.to}/>)}
                        >
                            <Switch key={location.key}>
                                <Route exact path="/" location={location} component={Home}/>
                                <Route path="/home" location={location} component={Home}/>
                                <Route path="/persons" location={location} component={Persons}/>
                                <Route path="/callback" render={(props) => {
                                    handleAuthentication(props);
                                    return <Callback {...props} />
                                }}/>
                            </Switch>
                        </NavigationDrawer>
                    )}
                />
            </BrowserRouter>
        );
    }
}

export default App;