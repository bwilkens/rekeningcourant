package nl.quintor.rc.web.rest.listener;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ext.Provider;

/**
 * Created by marcel on 5-2-2017.
 */
@Provider
public class JerseyApplicationEventListener implements ApplicationEventListener {
	private static final Logger LOG = LoggerFactory
			.getLogger(JerseyApplicationEventListener.class);
	private volatile int requestCnt = 0;

	@Override
	public void onEvent(ApplicationEvent event) {
		switch (event.getType()) {
		case INITIALIZATION_FINISHED:
			LOG.info(String.format("Application %s was initialized", event
					.getResourceConfig().getApplicationName()));
			break;
		case DESTROY_FINISHED:
			LOG.info(String.format("Application %s destroyed", event
					.getResourceConfig().getApplicationName()));
			break;
		case INITIALIZATION_APP_FINISHED:
			break;
		case INITIALIZATION_START:
			break;
		case RELOAD_FINISHED:
			break;
		default:
			break;
		}
	}

	@Override
	public RequestEventListener onRequest(RequestEvent requestEvent) {
		requestCnt++;
		LOG.info(String.format("Request %s started", requestCnt));
		// return the listener instance that will handle this request.
		return new PerformanceInterceptor(requestCnt);
	}
}
