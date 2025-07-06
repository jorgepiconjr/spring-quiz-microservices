package com.jorgepiconjr.quizservice.model;

public class Response {

    private Integer questioniId;
    private String questionResponse;

    public Response(Integer id, String response) {
        this.questioniId = id;
        this.questionResponse = response;
    }

    public Response() {

    }

    public Integer getId() {
        return questioniId;
    }

    public void setId(Integer id) {
        this.questioniId = id;
    }

    public String getResponse() {
        return questionResponse;
    }

    public void setResponse(String response) {
        this.questionResponse = response;
    }
}
