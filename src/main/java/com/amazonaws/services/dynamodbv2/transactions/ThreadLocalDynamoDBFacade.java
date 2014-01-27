/**
 * Copyright 2014-2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License"). 
 * You may not use this file except in compliance with the License. 
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed 
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, express 
 * or implied. See the License for the specific language governing permissions 
 * and limitations under the License. 
 */
package com.amazonaws.services.dynamodbv2.transactions;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemResult;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;

/**
 * Necessary to work around a limitation of the mapper. The mapper always gets
 * created with a fresh reflection cache, which is expensive to repopulate.
 * Using this class to route to a different facade for each request allows us to
 * reuse the mapper and its underlying cache for each call to the mapper from a
 * transaction or the transaction manager.
 */
public class ThreadLocalDynamoDBFacade implements AmazonDynamoDB {

    private final ThreadLocal<AmazonDynamoDB> backend = new ThreadLocal<AmazonDynamoDB>();

    private AmazonDynamoDB getBackend() {
        if (backend.get() == null) {
            throw new RuntimeException("No backend to proxy");
        }
        return backend.get();
    }

    public void setBackend(AmazonDynamoDB newBackend) {
        backend.set(newBackend);
    }

    @Override
    public BatchGetItemResult batchGetItem(BatchGetItemRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().batchGetItem(request);
    }

    @Override
    public BatchWriteItemResult batchWriteItem(BatchWriteItemRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().batchWriteItem(request);
    }

    @Override
    public CreateTableResult createTable(CreateTableRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().createTable(request);
    }

    @Override
    public DeleteItemResult deleteItem(DeleteItemRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().deleteItem(request);
    }

    @Override
    public DeleteTableResult deleteTable(DeleteTableRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().deleteTable(request);
    }

    @Override
    public DescribeTableResult describeTable(DescribeTableRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().describeTable(request);
    }

    @Override
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return getBackend().getCachedResponseMetadata(request);
    }

    @Override
    public GetItemResult getItem(GetItemRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().getItem(request);
    }

    @Override
    public ListTablesResult listTables() throws AmazonServiceException, AmazonClientException {
        return getBackend().listTables();
    }

    @Override
    public ListTablesResult listTables(ListTablesRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().listTables(request);
    }

    @Override
    public PutItemResult putItem(PutItemRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().putItem(request);
    }

    @Override
    public QueryResult query(QueryRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().query(request);
    }

    @Override
    public ScanResult scan(ScanRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().scan(request);
    }

    @Override
    public void setEndpoint(String request) throws IllegalArgumentException {
        getBackend().setEndpoint(request);
    }

    @Override
    public void setRegion(Region request) throws IllegalArgumentException {
        getBackend().setRegion(request);
    }

    @Override
    public void shutdown() {
        getBackend().shutdown();
    }

    @Override
    public UpdateItemResult updateItem(UpdateItemRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().updateItem(request);
    }

    @Override
    public UpdateTableResult updateTable(UpdateTableRequest request) throws AmazonServiceException, AmazonClientException {
        return getBackend().updateTable(request);
    }

}