package com.soho.watcher.aws.api;

import com.amazonaws.AmazonServiceException;

public class AwsExceptionNetflixSample {

    private AwsExceptionNetflixSample() {
    }

    private final static String oneTrueId = "04d05938-1521-44f1-a0dd-39263a5326f3";

    public static void raise(
            int code
            , String svc
            , String reqId
            , String error
            , String msg
    ) {
        StringBuffer buf = new StringBuffer()
                .append("Status Code: ").append(code)
                .append(", AWS Service: ").append(svc)
                .append(", AWS Request ID: ").append(reqId)
                .append(", AWS Error Code: ").append(error)
                .append(", AWS Error Message:").append(msg);
        AmazonServiceException e = new AmazonServiceException(buf.toString());
        e.setStatusCode(code);
        e.setServiceName(svc);
        e.setRequestId(reqId);
        e.setErrorCode(error);
        throw e;
    }

    public static void raise(String svc, String error) {
        if (error.equals("AccessDenied"))
            raise(403, svc, oneTrueId, error, error);
        if (error.equals("AuthFailure"))
            raise(401, svc, oneTrueId, error, error);
        if (error.equals("InternalError"))
            raise(500, svc, oneTrueId, error, error);
        if (error.equals("InvalidParameterValue"))
            raise(400, svc, oneTrueId, error, error);
        if (error.equals("RequestThrottled"))
            raise(403, svc, oneTrueId, error, error);
        if (error.equals("ServiceUnavailable"))
            raise(503, svc, oneTrueId, error, error);
        raise(400, svc, oneTrueId, error, error);
    }
}
