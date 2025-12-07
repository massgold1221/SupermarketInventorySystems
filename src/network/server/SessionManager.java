package network.server;

import java.util.*;
import java.util.concurrent.*;

/**
 * SessionManager: Manages client sessions
 */
public class SessionManager {
    private ConcurrentHashMap<String, ClientSession> activeSessions;
    private ScheduledExecutorService sessionTimeout;
    private static final long SESSION_TIMEOUT_MS = 30 * 60 * 1000; // 30 minutes
    
    public SessionManager() {
        this.activeSessions = new ConcurrentHashMap<>();
        this.sessionTimeout = Executors.newScheduledThreadPool(1);
        startSessionTimeoutChecker();
    }
    
    /**
     * Create a new session
     */
    public String createSession(String userId) {
        String sessionId = UUID.randomUUID().toString();
        ClientSession session = new ClientSession(sessionId, userId);
        activeSessions.put(sessionId, session);
        System.out.println("[SESSION] Created session: " + sessionId + " for user: " + userId);
        return sessionId;
    }
    
    /**
     * Validate a session
     */
    public boolean isSessionValid(String sessionId) {
        ClientSession session = activeSessions.get(sessionId);
        if (session == null) {
            return false;
        }
        
        if (System.currentTimeMillis() - session.getLastActivityTime() > SESSION_TIMEOUT_MS) {
            activeSessions.remove(sessionId);
            return false;
        }
        
        session.updateActivity();
        return true;
    }
    
    /**
     * End a session
     */
    public void endSession(String sessionId) {
        activeSessions.remove(sessionId);
        System.out.println("[SESSION] Ended session: " + sessionId);
    }
    
    /**
     * Get active sessions count
     */
    public int getActiveSessions() {
        return activeSessions.size();
    }
    
    /**
     * Start periodic session timeout checker
     */
    private void startSessionTimeoutChecker() {
        sessionTimeout.scheduleAtFixedRate(() -> {
            List<String> expiredSessions = new ArrayList<>();
            for (Map.Entry<String, ClientSession> entry : activeSessions.entrySet()) {
                if (System.currentTimeMillis() - entry.getValue().getLastActivityTime() > SESSION_TIMEOUT_MS) {
                    expiredSessions.add(entry.getKey());
                }
            }
            expiredSessions.forEach(activeSessions::remove);
        }, 5, 5, TimeUnit.MINUTES);
    }
    
    /**
     * Shutdown session manager
     */
    public void shutdown() {
        sessionTimeout.shutdown();
        activeSessions.clear();
    }
    
    /**
     * Inner class for session information
     */
    private static class ClientSession {
        private String sessionId;
        private String userId;
        private long createdTime;
        private long lastActivityTime;
        
        public ClientSession(String sessionId, String userId) {
            this.sessionId = sessionId;
            this.userId = userId;
            this.createdTime = System.currentTimeMillis();
            this.lastActivityTime = System.currentTimeMillis();
        }
        
        public void updateActivity() {
            this.lastActivityTime = System.currentTimeMillis();
        }
        
        public long getLastActivityTime() {
            return lastActivityTime;
        }
    }
}
