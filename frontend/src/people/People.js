import React, {Component} from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import {withStyles} from "material-ui/styles";
import keycode from "keycode";
import Table, {TableHead, TableBody, TableRow, TableCell, TableSortLabel} from "material-ui/Table";
import Checkbox from "material-ui/Checkbox";
import {IconButton, Paper, Toolbar, Typography} from "material-ui";
import DeleteIcon from "material-ui-icons/Delete";
import FilterListIcon from "material-ui-icons/FilterList";

let counter = 0;

function createData(firstName, lastName, email, phone, membership) {
    counter += 1;
    return {id: counter, firstName: firstName, lastName: lastName, email: email, phone: phone, membership: membership};
}

const columnData = [
    {id: 'firstName', numeric: false, disablePadding: true, label: 'Fornavn'},
    {id: 'lastName', numeric: false, disablePadding: true, label: 'Etternavn'},
    {id: 'email', numeric: false, disablePadding: true, label: 'E-post'},
    {id: 'phone', numeric: false, disablePadding: true, label: 'Telefon'},
    {id: 'membership', numeric: false, disablePadding: true, label: 'Medlemskap'},
];

class EnhancedTableHead extends Component {
    static propTypes = {
        numSelected: PropTypes.number.isRequired,
        onRequestSort: PropTypes.func.isRequired,
        onSelectAllClick: PropTypes.func.isRequired,
        order: PropTypes.string.isRequired,
        orderBy: PropTypes.string.isRequired,
    };

    createSortHandler = property => event => {
        this.props.onRequestSort(event, property);
    };

    render() {
        const {onSelectAllClick, order, orderBy, numSelected} = this.props;

        return (
            <TableHead>
                <TableRow>
                    <TableCell checkbox>
                        <Checkbox
                            indeterminate={numSelected > 0 && numSelected < 5}
                            checked={numSelected === 5}
                            onChange={onSelectAllClick}
                        />
                    </TableCell>
                    {columnData.map(column => {
                        return (
                            <TableCell
                                key={column.id}
                                numeric={column.numeric}
                                disablePadding={column.disablePadding}
                            >
                                <TableSortLabel
                                    active={orderBy === column.id}
                                    direction={order}
                                    onClick={this.createSortHandler(column.id)}
                                >
                                    {column.label}
                                </TableSortLabel>
                            </TableCell>
                        );
                    }, this)}
                </TableRow>
            </TableHead>
        );
    }
}

const toolbarStyles = theme => ({
    root: {
        paddingRight: 2,
    },
    highlight:
        theme.palette.type === 'light'
            ? {
                color: theme.palette.secondary.A700,
                backgroundColor: theme.palette.secondary.A100,
            }
            : {
                color: theme.palette.secondary.A100,
                backgroundColor: theme.palette.secondary.A700,
            },
    spacer: {
        flex: '1 1 100%',
    },
    actions: {
        color: theme.palette.text.secondary,
    },
    title: {
        flex: '0 0 auto',
    },
});

let EnhancedTableToolbar = props => {
    const {numSelected, classes} = props;

    return (
        <Toolbar
            className={classNames(classes.root, {
                [classes.highlight]: numSelected > 0,
            })}
        >
            <div className={classes.title}>
                {numSelected > 0 ? (
                    <Typography type="subheading">{numSelected} selected</Typography>
                ) : (
                    <Typography type="title">Nutrition</Typography>
                )}
            </div>
            <div className={classes.spacer}/>
            <div className={classes.actions}>
                {numSelected > 0 ? (
                    <IconButton aria-label="Delete">
                        <DeleteIcon/>
                    </IconButton>
                ) : (
                    <IconButton aria-label="Filter list">
                        <FilterListIcon/>
                    </IconButton>
                )}
            </div>
        </Toolbar>
    );
};

EnhancedTableToolbar.propTypes = {
    classes: PropTypes.object.isRequired,
    numSelected: PropTypes.number.isRequired,
};

EnhancedTableToolbar = withStyles(toolbarStyles)(EnhancedTableToolbar);

const styles = theme => ({
    paper: {
        width: '100%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
    },
});

class People extends Component {
    state = {
        order: 'asc',
        orderBy: 'firstName',
        selected: [],
        data: [
            createData('Scott', 'Tiger', 'scott.tiger@oracle.com', '44444444', 'FAMILY'),
            createData('Adams', 'Wood', 'adams.wood@oracle.com', '44444444', 'FAMILY'),
            createData('Jones', 'Steel', 'jones.steel@oracle.com', '44444444', 'FAMILY'),
            createData('Clark', 'Cloth', 'clark.cloth@oracle.com', '44444444', 'FAMILY'),
            createData('Blake', 'Paper', 'blake.paper@oracle.com', '44444444', 'FAMILY'),
        ],
    };

    handleRequestSort = (event, property) => {
        const orderBy = property;
        let order = 'desc';

        if (this.state.orderBy === property && this.state.order === 'desc') {
            order = 'asc';
        }

        const data = this.state.data.sort(
            (a, b) => (order === 'desc' ? b[orderBy] > a[orderBy] : a[orderBy] > b[orderBy]),
        );

        this.setState({data, order, orderBy});
    };

    handleSelectAllClick = (event, checked) => {
        if (checked) {
            this.setState({selected: this.state.data.map(n => n.id)});
            return;
        }
        this.setState({selected: []});
    };

    handleKeyDown = (event, id) => {
        if (keycode(event) === 'space') {
            this.handleClick(event, id);
        }
    };

    handleClick = (event, id) => {
        const {selected} = this.state;
        const selectedIndex = selected.indexOf(id);
        let newSelected = [];

        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selected, id);
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selected.slice(1));
        } else if (selectedIndex === selected.length - 1) {
            newSelected = newSelected.concat(selected.slice(0, -1));
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                selected.slice(0, selectedIndex),
                selected.slice(selectedIndex + 1),
            );
        }

        this.setState({selected: newSelected});
    };

    isSelected = id => this.state.selected.indexOf(id) !== -1;

    render() {
        const classes = this.props.classes;
        const {data, order, orderBy, selected} = this.state;

        return (
            <Paper className={classes.paper}>
                <EnhancedTableToolbar numSelected={selected.length}/>
                <Table>
                    <EnhancedTableHead
                        numSelected={selected.length}
                        order={order}
                        orderBy={orderBy}
                        onSelectAllClick={this.handleSelectAllClick}
                        onRequestSort={this.handleRequestSort}
                    />
                    <TableBody>
                        {data.map(n => {
                            const isSelected = this.isSelected(n.id);
                            return (
                                <TableRow
                                    hover
                                    onClick={event => this.handleClick(event, n.id)}
                                    onKeyDown={event => this.handleKeyDown(event, n.id)}
                                    role="checkbox"
                                    aria-checked={isSelected}
                                    tabIndex="-1"
                                    key={n.id}
                                    selected={isSelected}
                                >
                                    <TableCell checkbox>
                                        <Checkbox checked={isSelected}/>
                                    </TableCell>
                                    <TableCell disablePadding>{n.firstName}</TableCell>
                                    <TableCell disablePadding>{n.lastName}</TableCell>
                                    <TableCell disablePadding>{n.email}</TableCell>
                                    <TableCell disablePadding>{n.phone}</TableCell>
                                    <TableCell disablePadding>{n.membership}</TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </Paper>
        );
    }
}

People.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(People);