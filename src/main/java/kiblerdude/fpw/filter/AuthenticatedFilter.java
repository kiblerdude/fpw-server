package kiblerdude.fpw.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;

import kiblerdude.fpw.session.SessionService;

@Provider
@PreMatching
public class AuthenticatedFilter implements ContainerRequestFilter {
    
    private static final Logger LOG = Logger.getLogger(AuthenticatedFilter.class);

    private final SessionService sessions;
    private final boolean authenticated;
    
    public AuthenticatedFilter(SessionService sessions, boolean authenticated) {
        this.sessions = sessions;
        this.authenticated = authenticated;
    }

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        MultivaluedMap<String, String> parameters = request.getUriInfo().getQueryParameters();

        Optional<String> sessionId = Optional.fromNullable(parameters.getFirst("sessionId"));
        Optional<String> accountId = Optional.fromNullable(parameters.getFirst("accountId"));
        
        boolean sessionExists = sessionId.isPresent() && accountId.isPresent() && sessions.get(accountId, sessionId).isPresent();
        
        LOG.info(String.format("authentication %s session %s", authenticated, sessionExists));
        
        if (sessionExists == authenticated) return;
        
        request.abortWith(Response.status(Status.UNAUTHORIZED).build());  
    }

}
