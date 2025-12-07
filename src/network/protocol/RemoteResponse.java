package network.protocol;

import java.io.Serializable;

/**
 * RemoteResponse: Represents a response sent over the network
 * Enhanced with error codes and detailed information
 */
public class RemoteResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public enum ResponseStatus {
        SUCCESS(200),
        CREATED(201),
        BAD_REQUEST(400),
        UNAUTHORIZED(401),
        FORBIDDEN(403),
        NOT_FOUND(404),
        SERVER_ERROR(500),
        SERVICE_UNAVAILABLE(503);
        
        private final int code;
        
        ResponseStatus(int code) {
            this.code = code;
        }
        
        public int getCode() {
            return code;
        }
    }
    
    private ResponseStatus status;
    private Object responseData;
    private String message;
    private int requestId;
    private long timestamp;
    private String errorDetails;
    
    public RemoteResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public RemoteResponse(ResponseStatus status) {
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
    
    public RemoteResponse(ResponseStatus status, Object responseData) {
        this.status = status;
        this.responseData = responseData;
        this.timestamp = System.currentTimeMillis();
    }
    
    public RemoteResponse(ResponseStatus status, String message, Object responseData) {
        this.status = status;
        this.message = message;
        this.responseData = responseData;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public ResponseStatus getStatus() {
        return status;
    }
    
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
    
    public Object getResponseData() {
        return responseData;
    }
    
    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getRequestId() {
        return requestId;
    }
    
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getErrorDetails() {
        return errorDetails;
    }
    
    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
    
    public boolean isSuccess() {
        return status == ResponseStatus.SUCCESS || status == ResponseStatus.CREATED;
    }
    
    @Override
    public String toString() {
        return "RemoteResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", requestId=" + requestId +
                '}';
    }
}
