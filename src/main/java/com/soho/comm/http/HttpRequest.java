package com.soho.comm.http;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author kei
 * @version v1.0
 */
public class HttpRequest implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8307502772341168799L;


    /**
     * HTTP Server Host IP or Domain
     */
    private String host = null;

    /**
     * HTTP Server Port
     */
    private String port = null;

    /**
     * HTTP Server User
     */
    private String user = null;

    /**
     * HTTP Server Password
     */
    private String password = null;

    /**
     * HTTP Method
     */
    private String method = null;

    /**
     * HTTP End Point(URL)
     */
    private String endPoint = null;

    /**
     * HTTP Request Body
     */
    private String requestBody = null;

    /**
     * HTTP Request Header
     */
    private HashMap<String, String> requestPropertyMap = null;


    // 추가
    private String encoding = "UTF-8";

    private Integer readTimeout = null;


    // Custom Setter

    /**
     * @return the requestPropertyMap
     */
    public HashMap<String, String> getRequestPropertyMap() {
        return requestPropertyMap;
    }

    /**
     * @param key
     * @param value
     */
    public void setRequestPropertyMap(String key, String value) {

        if (requestPropertyMap == null) {
            requestPropertyMap = new HashMap<String, String>();
        }

        requestPropertyMap.put(key, value);
    }

    /**
     * toString
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    // getter and setter - start
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setRequestPropertyMap(HashMap<String, String> requestPropertyMap) {
        this.requestPropertyMap = requestPropertyMap;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    // getter and setter - end

}
