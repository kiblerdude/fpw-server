package kiblerdude.fpw.feature;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import kiblerdude.fpw.filter.AuthenticatedFilter;
import kiblerdude.fpw.session.SessionService;

import org.apache.log4j.Logger;

@Provider
@PreMatching
public class AuthenticationRequiredFeature implements DynamicFeature {

    private static final Logger LOG = Logger.getLogger(AuthenticationRequiredFeature.class);
    
    private final SessionService sessions;
    
    public AuthenticationRequiredFeature(SessionService sessions) {
        this.sessions = sessions;
    }

    @Override
    public void configure(ResourceInfo resource, FeatureContext context) {
        LOG.info("Adding authentication filter to " + resource);
        if (resource.getResourceMethod().getAnnotation(AuthenticationRequired.class) != null) {
            context.register(new AuthenticatedFilter(sessions, true));
        } else if (resource.getResourceMethod().getAnnotation(AuthenticationNotAllowed.class) != null) {
            context.register(new AuthenticatedFilter(sessions, false));
        }
    }

}

