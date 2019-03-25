package com.java.test.aws_serverless_sample1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.serverless.proxy.internal.LambdaContainerHandler;
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder;
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.services.lambda.runtime.Context;

public class StreamLambdaHandlerTest
{

    private static StreamLambdaHandler handler;

    private static Context lambdaContext;

    @BeforeClass
    public static void setUp()
    {
        StreamLambdaHandlerTest.handler = new StreamLambdaHandler();
        StreamLambdaHandlerTest.lambdaContext = new MockLambdaContext();
    }

    @Test
    public void ping_streamRequest_respondsWithHello()
    {
        InputStream requestStream = new AwsProxyRequestBuilder("/ping", HttpMethod.GET)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatusCode());

        Assert.assertFalse(response.isBase64Encoded());

        Assert.assertTrue(response.getBody().contains("pong"));
        Assert.assertTrue(response.getBody().contains("Hello, World!"));

        Assert.assertTrue(response.getMultiValueHeaders().containsKey(HttpHeaders.CONTENT_TYPE));
        Assert.assertTrue(
            response.getMultiValueHeaders().getFirst(HttpHeaders.CONTENT_TYPE).startsWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void invalidResource_streamRequest_responds404()
    {
        InputStream requestStream = new AwsProxyRequestBuilder("/pong", HttpMethod.GET)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON).buildStream();
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

        handle(requestStream, responseStream);

        AwsProxyResponse response = readResponse(responseStream);
        Assert.assertNotNull(response);
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    private void handle(InputStream is, ByteArrayOutputStream os)
    {
        try {
            StreamLambdaHandlerTest.handler.handleRequest(is, os, StreamLambdaHandlerTest.lambdaContext);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    private AwsProxyResponse readResponse(ByteArrayOutputStream responseStream)
    {
        try {
            return LambdaContainerHandler.getObjectMapper().readValue(responseStream.toByteArray(),
                AwsProxyResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Error while parsing response: " + e.getMessage());
        }
        return null;
    }
}
