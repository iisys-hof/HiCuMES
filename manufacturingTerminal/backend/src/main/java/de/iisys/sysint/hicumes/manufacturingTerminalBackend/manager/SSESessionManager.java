package de.iisys.sysint.hicumes.manufacturingTerminalBackend.manager;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities.SSESession;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;
import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventHeartBeat;

import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SSESessionManager
{
    private static final List<SSESession> SessionPool = new CopyOnWriteArrayList<>();

    private static final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    private static volatile SSESessionManager INSTANCE;

    private SSESessionManager() {
        es.scheduleAtFixedRate(this::broadcastHeartbeat, 0, 10, TimeUnit.SECONDS);
    }

    public static SSESessionManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SSESessionManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SSESessionManager();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void registerAnonymousClient(Sse sse, SseEventSink eventSink) {
        var sseSession = new SSESession(sse, eventSink);
        sseSession.getSseBroadcaster().onClose(sseEventSink -> removeClient(sseSession));
        sseSession.getSseBroadcaster().onError((sseEventSink, throwable) -> removeClient(sseSession));
        SessionPool.add(sseSession);
    }

    private void removeClient(SSESession sseSession) {
        sseSession.closeSession();
        SessionPool.remove(sseSession);
    }

    public void destroy() {
        for (var session : SessionPool) {
            closeSession(session);
        }
        SessionPool.clear();
        es.shutdown();
    }

    private void closeSession(SSESession sseSession) {
        sseSession.closeSession();
    }

    public synchronized void broadcast(EventEntity event) {
        for (var sseSession : SessionPool) {
            if (sseSession != null && !sseSession.sinkIsClosed()) {
                sseSession.sendEvent(event);
            }
        }
        SessionPool.removeIf(SSESession::sinkIsClosed);
        //System.out.println(SessionPool.size());
    }

    private void broadcastHeartbeat() {
        broadcast(new EventHeartBeat());
    }

    public void clearPoolNice() {
        for (var session : SessionPool) {
            closeSession(session);
        }
        SessionPool.clear();
    }

    public void clearPoolDirty() {
        SessionPool.clear();
    }
}
