import React, {Component} from "react";
import PropTypes from "prop-types";
import {withStyles} from "material-ui/styles";
import Table, {TableBody, TableCell, TableHead, TableRow, TableSortLabel} from "material-ui/Table";
import {Paper, Toolbar, Typography} from "material-ui";

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
        onRequestSort: PropTypes.func.isRequired,
        order: PropTypes.string.isRequired,
        orderBy: PropTypes.string.isRequired,
    };

    createSortHandler = property => event => {
        this.props.onRequestSort(event, property);
    };

    render() {
        const {order, orderBy} = this.props;

        return (
            <TableHead>
                <TableRow>
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
    title: {
        flex: '0 0 auto',
    },
});

let EnhancedTableToolbar = props => {
    const {classes} = props;

    return (
        <Toolbar>
            <div className={classes.title}>
                <Typography type="title">Personer</Typography>
            </div>
        </Toolbar>
    );
};

EnhancedTableToolbar.propTypes = {
    classes: PropTypes.object.isRequired,
};

EnhancedTableToolbar = withStyles(toolbarStyles)(EnhancedTableToolbar);

const styles = theme => ({
    paper: {
        width: '100%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
    },
    table: {
        paddingLeft: 24,
    },
});

class People extends Component {
    state = {
        order: 'asc',
        orderBy: 'firstName',
        data: [
            createData('Scott', 'Tiger', 'scott.tiger@oracle.com', '44444444', 'FAMILY'),
            createData('Adams', 'Wood', 'adams.wood@oracle.com', '44994499', 'REGULAR'),
            createData('Jones', 'Steel', 'jones.steel@oracle.com', '44449999', 'REGULAR'),
            createData('Clark', 'Cloth', 'clark.cloth@oracle.com', '99999999', 'REGULAR'),
            createData('Blake', 'Paper', 'blake.paper@oracle.com', '99449944', 'REGULAR'),
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

    render() {
        const classes = this.props.classes;
        const {data, order, orderBy} = this.state;

        return (
            <Paper className={classes.paper}>
                <EnhancedTableToolbar/>
                <div className={classes.table}>
                    <Table>
                        <EnhancedTableHead
                            order={order}
                            orderBy={orderBy}
                            onRequestSort={this.handleRequestSort}
                        />
                        <TableBody>
                            {data.map(n => {
                                return (
                                    <TableRow
                                        hover
                                        tabIndex="-1"
                                        key={n.id}
                                    >
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
                </div>
            </Paper>
        );
    }
}

People.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(People);