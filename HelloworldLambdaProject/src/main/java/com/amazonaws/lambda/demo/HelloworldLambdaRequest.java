package com.amazonaws.lambda.demo;

public class HelloworldLambdaRequest
{

    String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "HelloworldLambdaRequest [name=" + name + "]";
    }

}
