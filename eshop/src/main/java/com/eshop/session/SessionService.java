package com.eshop.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author https://github.com/steshenkoma
 */
public class SessionService {

    private Map<String, Session> sessions;

    public SessionService() {
        this.sessions = new ConcurrentHashMap<>();
    }

    public void addSession(Session session) {
        sessions.put(session.getSID(), session);
    }

    public void deleteSession(Session session) {
        sessions.remove(session.getSID());
    }

    public Session getSession(String SID) {

        Session session = null;
        if (sessions.containsKey(SID)) {
            session = sessions.get(SID);
        }
        return session;
    }
}
