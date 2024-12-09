package de.iisys.sysint.hicumes.manufacturingTerminalBackend.events.EventBus;

import java.util.List;
import java.util.Stack;

public class EventQueue
{
    private volatile Stack<EventEntity> eventQueue = new Stack<>();

    private static EventQueue INSTANCE;

    public static EventQueue getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new EventQueue();
        }

        return INSTANCE;
    }

    public synchronized void pushEvent(EventEntity event)
    {
        this.eventQueue.push(event);
    }

    public synchronized EventEntity popEvent()
    {
        if(!eventQueue.isEmpty())
        {
            return this.eventQueue.pop();
        }
        return null;
    }

    public synchronized boolean hasEvents()
    {
        return !eventQueue.isEmpty();
    }

}
