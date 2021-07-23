package com.se.fileserver.v1.account.application.dto.common;

public class Response<E> {
  private int status;
  private String code;
  private String message;
  private E data;

  public static class Builder<E> {
    private int status;
    private String code;
    private String message;
    private E data = null;

    public Builder(){}

    public Builder status(int status){
      this.status = status;
      return this;
    }
    public Builder code(String code){
      this.code = code;
      return this;
    }
    public Builder message(String message){
      this.message = message;
      return this;
    }
    public Builder data(E data){
      this.data = data;
      return this;
    }
    public Response build(){
      return new Response(this);
    }
  }

  public Response() {}

  private Response(Builder builder){
    this.status = builder.status;
    this.code = builder.code;
    this.message = builder.message;
    this.data = (E) builder.data;
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public E getData() {
    return data;
  }
}
