/*
 * Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.carbon.apimgt.rest.api.store.v1.impl;

import org.wso2.carbon.apimgt.api.APIConsumer;
import org.wso2.carbon.apimgt.api.APIManagementException;
import org.wso2.carbon.apimgt.api.model.APIProduct;
import org.wso2.carbon.apimgt.rest.api.store.v1.impl.ApiProductsApiServiceImpl;
import org.wso2.carbon.apimgt.rest.api.store.v1.mappings.APIMappingUtil;
import org.wso2.carbon.apimgt.rest.api.store.v1.*;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.*;


import org.wso2.carbon.apimgt.rest.api.store.v1.dto.ErrorDTO;
import org.wso2.carbon.apimgt.rest.api.util.RestApiConstants;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestAPIStoreUtils;
import org.wso2.carbon.apimgt.rest.api.util.utils.RestApiUtil;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DocumentDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.DocumentListDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.APIProductDTO;
import org.wso2.carbon.apimgt.rest.api.store.v1.dto.APIProductListDTO;

import java.util.List;

import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

import javax.ws.rs.core.Response;

public class ApiProductsApiServiceImpl extends ApiProductsApiService {
    private static final Log log = LogFactory.getLog(ApiProductsApiServiceImpl.class);
    @Override
    public Response apiProductsApiProductIdDocumentsDocumentIdContentGet(String apiProductId,String documentId,String ifNoneMatch){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response apiProductsApiProductIdDocumentsDocumentIdGet(String apiProductId,String documentId,String xWSO2Tenant,String ifNoneMatch){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response apiProductsApiProductIdDocumentsGet(String apiProductId,Integer limit,Integer offset,String xWSO2Tenant,String ifNoneMatch){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response apiProductsApiProductIdGet(String apiProductId,String ifNoneMatch,String xWSO2Tenant){
        String requestedTenantDomain = RestApiUtil.getRequestedTenantDomain(xWSO2Tenant);
        try {
            APIConsumer apiConsumer = RestApiUtil.getLoggedInUserConsumer();

            if (!RestApiUtil.isTenantAvailable(requestedTenantDomain)) {
                RestApiUtil.handleBadRequest("Provided tenant domain '" + xWSO2Tenant + "' is invalid", log);
            }

            APIProduct product = apiConsumer.getAPIProduct(apiProductId, requestedTenantDomain);
            if (product == null) {
                RestApiUtil.handleResourceNotFoundError(RestApiConstants.RESOURCE_API_PRODUCT, apiProductId, log);
            }
            if(!RestAPIStoreUtils.isUserAccessAllowedForAPIProduct(product)) {
                RestApiUtil.handleAuthorizationFailure(RestApiConstants.RESOURCE_API_PRODUCT, apiProductId, log);
            }
            APIProductDTO productToReturn = APIMappingUtil.fromAPIProductToDTO(product);
            return Response.ok().entity(productToReturn).build();
        } catch (APIManagementException e) {
            String errorMessage = "Error while retrieving API Product : " + apiProductId;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        } catch (UserStoreException e) {
            String errorMessage = "Error while checking availability of tenant " + requestedTenantDomain;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        } 
        return null;
    }
    @Override
    public Response apiProductsApiProductIdSwaggerGet(String apiProductId,String ifNoneMatch,String xWSO2Tenant){

        String requestedTenantDomain = RestApiUtil.getRequestedTenantDomain(xWSO2Tenant);
        try {
            APIConsumer apiConsumer = RestApiUtil.getLoggedInUserConsumer();

            if (!RestApiUtil.isTenantAvailable(requestedTenantDomain)) {
                RestApiUtil.handleBadRequest("Provided tenant domain '" + xWSO2Tenant + "' is invalid", log);
            }

            APIProduct product = apiConsumer.getAPIProduct(apiProductId, requestedTenantDomain);
            if (product == null) {
                RestApiUtil.handleResourceNotFoundError(RestApiConstants.RESOURCE_API_PRODUCT, apiProductId, log);
            }
            if(!RestAPIStoreUtils.isUserAccessAllowedForAPIProduct(product)) {
                RestApiUtil.handleAuthorizationFailure(RestApiConstants.RESOURCE_API_PRODUCT, apiProductId, log);
            }
            String apiSwagger = "";
            if (!StringUtils.isEmpty(product.getDefinition())) {
                apiSwagger = product.getDefinition();
            } 
            return Response.ok().entity(apiSwagger).build();
        } catch (APIManagementException e) {
            String errorMessage = "Error while retrieving API Product : " + apiProductId;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        } catch (UserStoreException e) {
            String errorMessage = "Error while checking availability of tenant " + requestedTenantDomain;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        } 
        return null;
    }
    @Override
    public Response apiProductsApiProductIdThumbnailGet(String apiProductId,String xWSO2Tenant,String ifNoneMatch){
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response apiProductsGet(Integer limit,Integer offset,String xWSO2Tenant,String query,String ifNoneMatch){

        //TODO implement pagination
        String requestedTenantDomain = RestApiUtil.getRequestedTenantDomain(xWSO2Tenant);
        APIProductListDTO apiProductListDTO = new APIProductListDTO();
        try {
            String username = RestApiUtil.getLoggedInUsername();
            APIConsumer apiConsumer = RestApiUtil.getConsumer(username);

            if (!RestApiUtil.isTenantAvailable(requestedTenantDomain)) {
                RestApiUtil.handleBadRequest("Provided tenant domain '" + xWSO2Tenant + "' is invalid", log);
            }
            List<APIProduct> product = apiConsumer.getStoreVisibleAPIProductsForUser(username, requestedTenantDomain);
            APIProductListDTO list = APIMappingUtil.fromAPIProductListToDTO(product);
            //add pagination
            return Response.ok().entity(list).build();

        } catch (APIManagementException e) {
            if (RestApiUtil.rootCauseMessageMatches(e, "start index seems to be greater than the limit count")) {
                // this is not an error of the user as he does not know the total number of apis available. Thus sends
                // an empty response
                apiProductListDTO.setCount(0);
                apiProductListDTO.setNext("");
                apiProductListDTO.setPrevious("");
                return Response.ok().entity(apiProductListDTO).build();
            } else {
                String errorMessage = "Error while retrieving APIs";
                RestApiUtil.handleInternalServerError(errorMessage, e, log);
            }
        } catch (UserStoreException e) {
            String errorMessage = "Error while checking availability of tenant " + requestedTenantDomain;
            RestApiUtil.handleInternalServerError(errorMessage, e, log);
        }
        return null;
    }
}