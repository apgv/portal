import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from 'material-ui/styles';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Home from "./home/Home";
import Auth from "./auth/Auth";
import Callback from "./callback/Callback";
import People from "./people/People";
import {AppBar, Divider, Drawer, List, Toolbar, Typography} from "material-ui";
import NavLink from "./NavLink";
import PeopleIcon from "material-ui-icons/People";
import HomeIcon from "material-ui-icons/Home";

const navItems = [{
    exact: true,
    label: 'Hjem',
    path: '/',
    icon: <HomeIcon/>,
}, {
    label: 'Personer',
    path: '/people',
    icon: <PeopleIcon/>,
}];

const drawerWidth = 240;

const styles = theme => ({
    root: {
        width: '100%',
        height: '100%',
        zIndex: 1,
        overflow: 'hidden',
    },
    appFrame: {
        position: 'relative',
        display: 'flex',
        width: '100%',
        height: '100%',
    },
    appBar: {
        position: 'absolute',
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: drawerWidth,
        order: 1,
    },
    drawerPaper: {
        position: 'relative',
        height: '100%',
        width: drawerWidth,
    },
    drawerHeader: {
        height: 56,
        [theme.breakpoints.up('sm')]: {
            height: 64,
        },
    },
    content: {
        backgroundColor: theme.palette.background.default,
        width: '100%',
        padding: theme.spacing.unit * 3,
        height: 'calc(100% - 56px)',
        marginTop: 56,
        [theme.breakpoints.up('sm')]: {
            height: 'calc(100% - 64px)',
            marginTop: 64,
        },
    },
});

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

                        < div className={this.props.classes.root}>
                            <div className={this.props.classes.appFrame}>
                                <AppBar className={this.props.classes.appBar}>
                                    <Toolbar>
                                        <Typography
                                            type="title"
                                            color="inherit"
                                            noWrap>
                                            Skotbu Vel
                                        </Typography>
                                    </Toolbar>
                                </AppBar>
                                <Drawer
                                    type="permanent"
                                    classes={{
                                        paper: this.props.classes.drawerPaper,
                                    }}
                                >
                                    <div className={this.props.classes.drawerHeader}/>
                                    <Divider/>
                                    <List>
                                        {navItems.map(props => <NavLink {...props} key={props.path}/>)}
                                    </List>
                                </Drawer>
                                <main className={this.props.classes.content}>
                                    <Typography type="body2" noWrap>
                                        <Switch key={location.key}>
                                            <Route exact path="/" location={location} component={Home}/>
                                            <Route path="/home" location={location} component={Home}/>
                                            <Route path="/people" location={location} component={People}/>
                                            <Route path="/callback" render={(props) => {
                                                handleAuthentication(props);
                                                return <Callback {...props} />
                                            }}/>
                                        </Switch>
                                    </Typography>
                                </main>
                            </div>
                        </div>
                    )}
                />
            </BrowserRouter>
        );
    }
}

App.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(App);