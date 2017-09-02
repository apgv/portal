import React from 'react';
import PropTypes from 'prop-types';
import {ListItem, ListItemIcon, ListItemText} from "material-ui/List";
import {Link, Route} from "react-router-dom";

const NavLink = ({label, path, exact, icon}) => (
    <Route path={path} exact={exact}>
        {({match}) => {
            return (
                <ListItem button
                          component={Link}
                          to={path}
                >
                    <ListItemIcon>
                        {icon}
                    </ListItemIcon>
                    <ListItemText primary={label}/>
                </ListItem>
            );
        }}
    </Route>
);

NavLink.propTypes = {
    label: PropTypes.string.isRequired,
    path: PropTypes.string,
    exact: PropTypes.bool,
    icon: PropTypes.node,
};

export default NavLink;
