package common.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class SessionListener implements HttpSessionListener {
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private static int counter;

    public static int getCounter() {
        return counter;
    }

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        ServletContext application = session.getServletContext();

        // 在application范围由一个HashSet集保存所有的session
        HashSet sessions = (HashSet) application.getAttribute("sessions");
        if (sessions == null) {
            sessions = new HashSet();
            application.setAttribute("sessions", sessions);
        }
        sessions.add(session);
        counter = sessions.size();
        System.out.println("session被创建了-来自session监听器      " + sdf.format(new Date()) + "目前在线人数为:" + sessions.size());

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        ServletContext application = session.getServletContext();
        HashSet sessions = (HashSet) application.getAttribute("sessions");

        // 销毁的session均从HashSet集中移除
        sessions.remove(session);
        counter = sessions.size();
        System.out.println("session被销毁了-来自session监听器      " + sdf.format(new Date()) + "目前在线人数为:" + sessions.size());
    }
}
