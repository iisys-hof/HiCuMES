package de.iisys.sysint.hicumes.manufacturingTerminalBackend.entities;

import de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus.EventEntity;

import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.concurrent.TimeUnit;

public class SSESession
{
    private final String sessionId;
    private final SseBroadcaster sseBroadcaster;
    private final SseEventSink eventSink;
    private final Sse sse;

    public SSESession(String sessionId, Sse sse, SseEventSink eventSink) {
        this.sse = sse;
        this.sseBroadcaster = this.sse.newBroadcaster();
        this.eventSink = eventSink;
        this.sessionId = sessionId;
    }

    public SSESession(Sse sse, SseEventSink eventSink) {
        this.sse = sse;
        this.sseBroadcaster = this.sse.newBroadcaster();
        this.eventSink = eventSink;
        this.sessionId = "";
    }


    public String getSessionId() {
        return this.sessionId;
    }

    public SseBroadcaster getSseBroadcaster() {
        return this.sseBroadcaster;
    }

    public void broadcastEvent(EventEntity event)
    {
        sseBroadcaster.broadcast(sse.newEvent(event.toString()));
        sseBroadcaster.close();
    }

    public void sendEvent(EventEntity event)
    {
        if(sse != null && event != null && eventSink != null) {
            try {
                eventSink.send(sse.newEvent(event.toString()));
            } catch (Exception e) {
                System.out.println("Could not send to sse event sink");
                e.printStackTrace();
            }
        }
    }

    public void closeSession()
    {
        eventSink.close();
    }

    public boolean sinkIsClosed() {
        return eventSink.isClosed();
    }
}
