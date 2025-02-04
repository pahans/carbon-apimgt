/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Grid from '@material-ui/core/Grid';
import { Typography, Paper, Box } from '@material-ui/core';
import Stepper from '@material-ui/core/Stepper';
import Step from '@material-ui/core/Step';
import StepLabel from '@material-ui/core/StepLabel';
import { FormattedMessage, injectIntl } from 'react-intl';
import CreateAppStep from './CreateAppStep';
import SubscribeToAppStep from './SubscribeToAppStep';
import GenerateKeysStep from './GenerateKeysStep';
import GenerateAccessTokenStep from './GenerateAccessTokenStep';
import CopyAccessTokenStep from './CopyAccessTokenStep';

const stepComponents = [CreateAppStep, SubscribeToAppStep, GenerateKeysStep,
    GenerateAccessTokenStep, CopyAccessTokenStep];

/**
 * Class used for wizard
 */
class Wizard extends Component {
    /**
     * @param {*} props properties
     */
    constructor(props) {
        super(props);
        const { intl } = this.props;
        this.steps = [
            intl.formatMessage({
                defaultMessage: 'Create application',
                id: 'Apis.Details.Credentials.Wizard.Wizard.create',
            }),
            intl.formatMessage({
                defaultMessage: 'Subscribe to new application',
                id: 'Apis.Details.Credentials.Wizard.Wizard.subscribe.to.new.application',
            }),
            intl.formatMessage({
                defaultMessage: 'Generate Keys',
                id: 'Apis.Details.Credentials.Wizard.Wizard.generate.keys',
            }),
            intl.formatMessage({
                defaultMessage: 'Generate Access Token',
                id: 'Apis.Details.Credentials.Wizard.Wizard.generate.access.token',
            }),
            intl.formatMessage({
                defaultMessage: 'Copy Access Token',
                id: 'Apis.Details.Credentials.Wizard.Wizard.copy.access.token',
            }),
        ];
        this.stepStatuses = {
            PROCEED: 'PROCEED',
            BLOCKED: 'BLOCKED',
        };
        this.state = {
            currentStep: 0,
            createdApp: null,
            createdToken: null,
            redirect: false,
            createdKeyType: '',
            stepStatus: 'PROCEED',
        };
    }

    /**
     * Used to set the status retured after executing each step. Used in workflow
     * scenario to evaluate wheather we can proceed to next step
     * @param {*} stepStatus status
     */
    setStepStatus = (stepStatus) => {
        this.setState({ stepStatus });
    }

    /**
     * Set the created app from step 1
     * @param {*} createdApp app created
     */
    setCreatedApp = (createdApp) => {
        this.setState({ createdApp });
    }

    /**
     * Set the created token from step 4
     * @param {*} createdToken token created
     */
    setCreatedToken = (createdToken) => {
        this.setState({ createdToken });
    }

    /**
     * Set the created keytype from step 3
     * @param {*} createdKeyType token created
     */
    setCreatedKeyType = (createdKeyType) => {
        this.setState({ createdKeyType });
    }

    /**
     * Increment the current step or next step by 1
     */
    handleNext = () => {
        this.setState(({ currentStep }) => {
            return { currentStep: currentStep + 1 };
        });
    }

    /**
     * Rest the currentStep to 0 and bring wizard back to first step
     * @memberof Wizard
     */
    handleReset = () => {
        this.setState({
            currentStep: 0,
        });
    };

    /**
     * @inheritdoc
     */
    render() {
        const { classes } = this.props;
        const { currentStep, stepStatus } = this.state;
        const CurrentStepComponent = stepComponents[currentStep];
        return (
            <Box my={2} mx='auto' display='flex' justifyContent='center'>
                <Grid item pb={1} xs={12} md={11}>
                    <Paper elevation={0}>
                        <Box pt={2} px={3} display='flex' >
                            <Typography variant='h4'>
                                <FormattedMessage
                                    id={'Apis.Details.Credentials.Credentials'
                             + '.api.credentials.generate'}
                                    defaultMessage='Key Generation Wizard'
                                />
                            </Typography>
                        </Box>
                        <Box py={1} mx='auto' display='flex' >
                            <Grid item xs={12} md={12}>
                                <Stepper activeStep={currentStep}>
                                    {this.steps.map((label) => {
                                        return (
                                            <Step key={label}>
                                                <StepLabel>{label}</StepLabel>
                                            </Step>
                                        );
                                    })}
                                </Stepper>
                            </Grid>
                        </Box>
                        <Box py={1} mx='auto' display='block' >
                            {stepStatus === this.stepStatuses.PROCEED && (
                                <React.Fragment>
                                    <CurrentStepComponent
                                        {...this.state}
                                        incrementStep={this.handleNext}
                                        setStepStatus={this.setStepStatus}
                                        stepStatuses={this.stepStatuses}
                                        classes={classes}
                                        setCreatedApp={this.setCreatedApp}
                                        setCreatedKeyType={this.setCreatedKeyType}
                                        setCreatedToken={this.setCreatedToken}
                                        handleReset={this.handleReset}
                                    />
                                </React.Fragment>
                            )}
                        </Box>
                        <Box py={1} mb={1} mx='auto' display='flex' >
                            {stepStatus === this.stepStatuses.BLOCKED && (
                                <Typography variant='h4'>
                                    <FormattedMessage
                                        id={'Apis.Details.Credentials.Wizard.Wizard.approval.request.'
                                                    + 'for.this.step.has'}
                                        defaultMessage='Approval request for this step has been Sent'
                                    />
                                </Typography>
                            )}
                        </Box>
                    </Paper>
                </Grid>
            </Box>
        );
    }
}

Wizard.propTypes = {
    classes: PropTypes.shape({
        appBar: PropTypes.string,
        toolbar: PropTypes.string,
        subscribeTitle: PropTypes.string,
        plainContent: PropTypes.string,
        root: PropTypes.string,
        instructions: PropTypes.string,
        button: PropTypes.string,
        wizardContent: PropTypes.string,
    }).isRequired,
    intl: PropTypes.func.isRequired,
    throttlingPolicyList: PropTypes.arrayOf(PropTypes.string).isRequired,
};

export default injectIntl(Wizard);
