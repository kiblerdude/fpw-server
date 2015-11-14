package kiblerdude.fpw.account;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.google.common.base.Optional;

import kiblerdude.fpw.feature.AuthenticationNotAllowed;
import kiblerdude.fpw.feature.AuthenticationRequired;
import kiblerdude.fpw.session.SessionService;
import kiblerdude.fpw.session.UserSession;

/**
 * APIs for accounts.
 * 
 * @author kiblerj
 *
 */
@Path("/v1/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
	
	private static final Logger LOG = Logger.getLogger(AccountResource.class);
	
	private final AccountService accounts;
	private final SessionService sessions;

	public AccountResource(AccountService accounts, SessionService sessions) {
		this.accounts = accounts;
		this.sessions = sessions;
	}
	
	/**
	 * Authenticates an account with the provided <code>email</code> and <code>password</code>.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@POST
	@Path("/authentication")
	public Response authenticate(AccountCredentials existingAccount) {
		try {
		    Optional<String> email = Optional.fromNullable(existingAccount.getEmail());
		    Optional<String> password = Optional.fromNullable(existingAccount.getPassword());
			Optional<UserAccount> existingUser = accounts.authenticateAccount(email, password);
			if (!existingUser.isPresent()) {
				return Response.status(Status.UNAUTHORIZED).build();
			} else {
				UserAccount user = existingUser.get();
				
			    // create a new session for the new user
			    Optional<UserSession> session = sessions.create(user);
			    
			    if (!session.isPresent()) {
			    	throw new Exception("Failed to create a session");
			    } else {
    				return Response.status(Status.CREATED).entity(session.get()).build();
    			}
			}
			
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return Response.serverError().build();
	}

	/**
	 * Creates a new account.
	 * 
	 * @param newAccount
	 * @return
	 */
	@POST
	@AuthenticationNotAllowed
	public Response create(AccountCredentials credentials) {
		try {
			Optional<UserAccount> newUser = accounts.createAccount(credentials);
			
			if (!newUser.isPresent()) {
				return Response.status(Status.BAD_REQUEST).build();
			} else {
				UserAccount user = newUser.get();
				
			    // create a new session for the new user
			    Optional<UserSession> session = sessions.create(user);
			    
			    if (!session.isPresent()) {
			    	throw new Exception("Failed to create a session");
			    } else {
    				return Response.status(Status.CREATED).entity(session.get()).build();
    			}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return Response.serverError().build();
	}
	
	/**
	 * Updates an existing account's credentials.
	 * 
	 * @param updatedAccount
	 * @return
	 */
	@PUT
	@AuthenticationRequired
	public Response update(UpdatedAccountCredentials updatedAccount) {
		try {
			Optional<Account> entity = accounts.updateAccountCredentials(updatedAccount);			
			if (entity.isPresent()) {
				return Response.ok(entity.get()).build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return Response.serverError().build();
		}		
	}
	
	/**
	 * Removes an existing account.
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@DELETE
	@AuthenticationRequired
	public Response delete(
			@QueryParam("email") Optional<String> email, 
			@QueryParam("password") Optional<String> password) {
		try {
			//TODO also need to delete the session
			if (accounts.removeAccount(email, password)) {
				return Response.ok().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return Response.serverError().build();
		}
	}
}
