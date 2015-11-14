package kiblerdude.fpw.session;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;

import kiblerdude.fpw.feature.AuthenticationRequired;

/**
 * APIs for sessions.
 * 
 * @author kiblerj
 *
 */
@Path("/v1/sessions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Provider
public class SessionResource {
	
	private static final Logger LOG = Logger.getLogger(SessionResource.class);

	private final SessionService sessions;
	
	public SessionResource(final SessionService sessions) {
		this.sessions = sessions;
	}	
	
	/**
	 * Retrieves an existing session.
	 * 
	 * @param accountId
	 * @param sessionId
	 * @return
	 */
	@GET
	public Response get(@QueryParam("accountId") Optional<String> accountId, @QueryParam("sessionId") Optional<String> sessionId) {
		try {
			Optional<UserSession> session = sessions.get(accountId, sessionId);
			if (session.isPresent()) {
				return Response.ok(session.get()).build();
			}
			return Response.status(Status.NOT_FOUND).build();
		} catch (SecurityException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return Response.serverError().build();
		}
	}
	
	/**
	 * Removes an existing session.
	 * 
	 * @param accountId
	 * @param sessionId
	 * @return
	 */
	@DELETE
	@AuthenticationRequired
	public Response delete(@QueryParam("accountId") Optional<String> accountId, @QueryParam("sessionId") Optional<String> sessionId) {
		try {
			if (sessions.delete(accountId, sessionId)) {
				return Response.ok().build();
			}
			return Response.status(Status.NOT_FOUND).build();
		} catch (SecurityException e) {
			return Response.status(Status.UNAUTHORIZED).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
