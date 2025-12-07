package network.protocol;

import java.io.Serializable;

/**
 * RemoteCommand: Represents a command sent over the network
 * Enhanced with type safety
 */
public class RemoteCommand implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private CommandType commandType;
    private Object commandData;
    private int requestId;
    private long timestamp;
    private String sessionId;
    
    public RemoteCommand() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public RemoteCommand(CommandType commandType) {
        this.commandType = commandType;
        this.timestamp = System.currentTimeMillis();
    }
    
    public RemoteCommand(CommandType commandType, Object commandData) {
        this.commandType = commandType;
        this.commandData = commandData;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public CommandType getCommandType() {
        return commandType;
    }
    
    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
    
    public Object getCommandData() {
        return commandData;
    }
    
    public void setCommandData(Object commandData) {
        this.commandData = commandData;
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
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    @Override
    public String toString() {
        return "RemoteCommand{" +
                "commandType=" + commandType +
                ", requestId=" + requestId +
                ", timestamp=" + timestamp +
                '}';
    }
}
