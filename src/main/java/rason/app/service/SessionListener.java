package rason.app.service;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionListener implements HttpSessionListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RasonSettings settings;

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		arg0.getSession().setMaxInactiveInterval(settings.getMaxSessionLifeMinutes() * 60);
		logger.info("session created: " + arg0.getSession().getId());
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		logger.info("session destroyed: " + arg0.getSession().getId());
	}
}
