package com.soho.comm.http;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author kei
 * @version v1.0
 */
public class HttpResponse implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5891906128749486028L;

    /**
     * HTTP Response Code
     */
    private Integer responseCode = null;

    /**
     * HTTP Response Message
     */
    private String responseMessage = null;

    /**
     * HTTP Response Body
     */
    private String responseBody = null;

    /**
     * toString
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    // getter and setter - start
    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }
    // getter and setter - end


}
