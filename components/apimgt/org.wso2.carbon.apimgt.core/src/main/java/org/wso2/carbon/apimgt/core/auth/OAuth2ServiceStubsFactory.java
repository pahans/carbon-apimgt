/*
 *  Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.wso2.carbon.apimgt.core.auth;

import org.wso2.carbon.apimgt.core.configuration.models.KeyMgtConfigurations;
import org.wso2.carbon.apimgt.core.internal.ServiceReferenceHolder;

/**
 * Factory class to create OAuth2 service stubs
 */
public class OAuth2ServiceStubsFactory {

    /**
     * Get OAuth2 Service Stubs
     *
     * @return {@link OAuth2ServiceStubs} object
     */
    public static OAuth2ServiceStubs getOAuth2ServiceStubs() {

        KeyMgtConfigurations keyManagerConfigs = ServiceReferenceHolder.getInstance().getAPIMConfiguration()
                .getKeyManagerConfigs();
        return new OAuth2ServiceStubs(keyManagerConfigs.getTokenEndpoint(), keyManagerConfigs.getRevokeEndpoint(),
                keyManagerConfigs.getIntrospectEndpoint(), keyManagerConfigs.getUserInfoEndpoint(), keyManagerConfigs
                .getKeyManagerCertAlias(), keyManagerConfigs.getKeyManagerCredentials().getUsername(),
                keyManagerConfigs.getKeyManagerCredentials().getPassword());
    }
}