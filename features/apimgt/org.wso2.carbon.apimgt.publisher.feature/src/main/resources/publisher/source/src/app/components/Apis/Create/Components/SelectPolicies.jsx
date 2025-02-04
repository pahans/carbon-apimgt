import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import ListItemText from '@material-ui/core/ListItemText';
import Checkbox from '@material-ui/core/Checkbox';
import CircularProgress from '@material-ui/core/CircularProgress';
import { FormattedMessage } from 'react-intl';
import API from 'AppData/api';
import { makeStyles } from '@material-ui/core/styles';
import ClickAwayListener from '@material-ui/core/ClickAwayListener';

const useStyles = makeStyles(theme => ({
    mandatoryStar: {
        color: theme.palette.error.main,
        marginLeft: theme.spacing(0.1),
    },
}));

/**
 * Trottling Policies dropdown selector used in minimized API Create form
 * @export
 * @param {*} props
 * @returns {React.Component}
 */
export default function SelectPolicies(props) {
    const {
        onChange, policies: selectedPolicies, multiple, helperText, isAPIProduct, validate,
    } = props;
    const [policies, setPolicies] = useState({});
    const classes = useStyles();
    useEffect(() => {
        API.policies('subscription').then(response => setPolicies(response.body));
    }, []);
    const onClickAway = ({ target: { value } }) => {
        validate('policies', value);
    };
    if (!policies.list) {
        return <CircularProgress />;
    } else {
        return (
            <ClickAwayListener onClickAway={onClickAway}>
                <TextField
                    fullWidth
                    select
                    label={
                        <React.Fragment>
                            <FormattedMessage
                                id='Apis.Create.Components.SelectPolicies.busimess.plans'
                                defaultMessage='Business plan(s)'
                            />
                            {isAPIProduct && (<sup className={classes.mandatoryStar}>*</sup>)}
                        </React.Fragment>
                    }
                    value={selectedPolicies}
                    name='policies'
                    onChange={onChange}
                    SelectProps={{
                        multiple,
                        renderValue: selected => (Array.isArray(selected) ? selected.join(', ') : selected),
                    }}
                    helperText={isAPIProduct ? helperText + 'API Product' : helperText + 'API'}
                    margin='normal'
                    variant='outlined'
                    InputProps={{
                        id: 'itest-id-apipolicies-input',
                    }}
                >
                    {policies.list.map(policy => (
                        <MenuItem
                            dense
                            disableGutters={multiple}
                            id={policy.name}
                            key={policy.name}
                            value={policy.displayName}
                        >
                            {multiple && <Checkbox color='primary' checked={selectedPolicies.includes(policy.name)} />}
                            <ListItemText primary={policy.displayName} secondary={policy.description} />
                        </MenuItem>
                    ))}
                </TextField>
            </ClickAwayListener>
        );
    }
}

SelectPolicies.defaultProps = {
    policies: [],
    multiple: true,
    required: false,
    isAPIProduct: PropTypes.bool.isRequired,
    helperText: 'Select one or more throttling policies for the ',
};
