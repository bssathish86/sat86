package com.amazonaws.lambda.demo;

import java.util.Date;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<HelloworldLambdaRequest, HelloworldLambdaResponse>
{

    @Override
    public HelloworldLambdaResponse handleRequest(HelloworldLambdaRequest input, Context context)
    {
        context.getLogger().log("Input: " + input);
        HelloworldLambdaResponse lambdaResponse = new HelloworldLambdaResponse();
        try {
            lambdaResponse.setResponseMessage("Hello " + input.getName() + " Response Time : " + new Date());
            lambdaResponse.setTransactionID(UUID.randomUUID().toString());
        } catch (Exception e) {
            e.printStackTrace();
            lambdaResponse.setResponseMessage(e.getMessage());
        }
        context.getLogger().log("Response : " + lambdaResponse);
        return lambdaResponse;
    }
}
