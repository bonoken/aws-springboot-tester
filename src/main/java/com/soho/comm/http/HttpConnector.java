package com.soho.comm.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Set;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author kei
 * @version v1.0
 */
@Component
public class HttpConnector {

    /**
     * log
     */
    private static final Logger logger = LogManager.getLogger(HttpConnector.class);

    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";

    public static final String HTTP_HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HTTP_HEADER_JSON = "application/json";


    public static final String HTTP_HEADER_ACCEPT = "accept";

    private int readTimeOut;

    @Value("${app.http.read.timeout.default}")
    private void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    /**
     * @param HttpRequest
     * @throws IOException
     * @throws Exception
     */
    public HttpResponse connection(HttpRequest request) throws Exception {

        HttpRequestBase httpRequest = null;

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse httpResponse = null;


        // rtn object
        HttpResponse response = new HttpResponse();

        try {
            String uri = request.getEndPoint();
            String method = request.getMethod();

            switch (method) {
                case HTTP_METHOD_GET:
                    httpRequest = new HttpGet(uri);
                    break;
                case HTTP_METHOD_POST:
                    httpRequest = new HttpPost(uri);
                    break;
                case HTTP_METHOD_PUT:
                    httpRequest = new HttpPut(uri);
                    break;
                case HTTP_METHOD_DELETE:
                    httpRequest = new HttpDelete(uri);
                    break;
                default:
                    throw new RuntimeException("Invalid HTTP request type: " + request.getMethod());
            }

            /* header */

            // header
            setRequestProperties(httpRequest, request.getRequestPropertyMap());

            // read timeout
            int timeout = readTimeOut;
            if (null != request.getReadTimeout()) {
                timeout = request.getReadTimeout();
                setReadTimeOut(timeout);
                RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout).setSocketTimeout(timeout).build();
                httpRequest.setConfig(config);
            }

            if (method.equals(HTTP_METHOD_POST)) {
                StringEntity stringEntity = new StringEntity(request.getRequestBody(), request.getEncoding());
                ((HttpPost) httpRequest).setEntity(stringEntity);
            } else if (method.endsWith(HTTP_METHOD_PUT)) {
                StringEntity stringEntity = new StringEntity(request.getRequestBody(), request.getEncoding());
                ((HttpPut) httpRequest).setEntity(stringEntity);
            }


            httpResponse = httpClient.execute(httpRequest);
            Integer responseCode = null;

            if (null != httpResponse.getStatusLine()) {
                responseCode = httpResponse.getStatusLine().getStatusCode();
                response.setResponseCode(responseCode);
                response.setResponseMessage(httpResponse.getStatusLine().getReasonPhrase());
            }

            if (null != httpResponse.getEntity()) {
                response.setResponseBody(receiveResponse(httpResponse.getEntity().getContent()));
            }


        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            if (httpClient != null) {
                httpClient.close();
            }

            logger.debug("code : " + response.getResponseCode());
            logger.debug("message : " + response.getResponseMessage());
            logger.debug("body : " + response.getResponseBody());

        }
        return response;
    }

    /**
     * @param urlConnection
     * @param requestPropertyHashObj
     */
    private static void setRequestProperties(HttpRequestBase httpRequest, HashMap<String, String> requestPropertyMap) {
        Set<String> keySet = requestPropertyMap.keySet();
        String value = null;
        for (String key : keySet) {
            value = requestPropertyMap.get(key);
            httpRequest.addHeader(key, value);
        }
    }


    /**
     * @param inputStream
     * @return String
     * @throws IOException
     */
    private static String receiveResponse(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);

        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while (null != (line = br.readLine())) {
                sb.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                br.close();
                isr.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

}