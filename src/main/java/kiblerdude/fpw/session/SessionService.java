package kiblerdude.fpw.session;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

import io.dropwizard.lifecycle.Managed;
import kiblerdude.fpw.account.UserAccount;
import kiblerdude.fpw.common.CacheService;

/**
 * Service for Sessions that translates session data between the client layer and the data layer.
 * 
 * @author kiblerj
 *
 */
public class SessionService implements Managed {

	private static final Logger LOG = Logger.getLogger(SessionService.class);
	
	private final CacheService cache;
	
	public SessionService(CacheService cache) {
		this.cache = cache;		
	}
	
	@Override
	public void start() throws Exception {
		LOG.info("Starting SessionService");
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping SessionService");
	}
	
	/**
	 * Creates and stores a new {@link UserSession} associated with the provided
	 * <code>accountId</code>.
	 * 
	 * @param accountId
	 *            The Account ID associated with the {@link UserSession}.
	 * @return An {@link Optional} of the Session ID.
	 */
	public Optional<UserSession> create(UserAccount user) {
		checkNotNull(user, "user is null");
		
		// generate a new session id
		String sessionId = UUID.randomUUID().toString();
		String accountId = user.getAccount().getId();
		String key = generateSessionKey(accountId);
		
		// check if a session already exists
		Optional<UserSession> session = cache.get(key, UserSession.class);
		if (session.isPresent()) {
			// remove the existing session
			cache.delete(key);
		}
		
		// create a new Session
		UserSession s = new UserSession(accountId, sessionId, user);
		
		// store the session
		if (cache.set(key, s)) {
			return Optional.of(s);
		}
		
		return Optional.absent();
	}
	
	/**
	 * Retrieves the {@link UserSession} for the provided <code>accountId</code>
	 * and <code>sessionId</code>.
	 * 
	 * @param accountId
	 *            The Account ID associated with the Session.
	 * @param sessionId
	 *            The Session ID associated with the Session.
	 * @return An {@link Optional} of the existing {@link UserSession}.
	 */
	public Optional<UserSession> get(Optional<String> accountId, Optional<String> sessionId) {
		checkNotNull(accountId, "Account ID is null");
		checkNotNull(sessionId, "Session ID is null");

		if (!accountId.isPresent() || !sessionId.isPresent()) {
			return Optional.absent();
		}		
		
		String key = generateSessionKey(accountId.get());

		// get the session
		Optional<UserSession> session = cache.get(key, UserSession.class);

		if (session.isPresent()) {
			UserSession s = session.get();
			if (Objects.equal(sessionId.get(), s.getSessionId())) {
				return session;
			}
		}

		return Optional.absent();
	}
	
	/**
	 * Deletes an existing {@link UserSession} associated with the provided
	 * <code>accountId</code> and <code>sessionId</code>.
	 * 
	 * @param accountId
	 *            The Account ID associated with the Session.
	 * @param sessionId
	 *            The Session ID associated with the Session.
	 * @return True if the {@link UserSession} was deleted, false otherwise.
	 */
	public boolean delete(Optional<String> accountId, Optional<String> sessionId) {
		checkNotNull(accountId, "Account ID is null");
		checkNotNull(sessionId, "Session ID is null");

		if (!accountId.isPresent() || !sessionId.isPresent()) {
			return false;
		}		
		
		String key = generateSessionKey(accountId.get());
		
		Optional<UserSession> session = cache.get(key, UserSession.class);
		
		if (session.isPresent()) {
			UserSession s = session.get();
			// verify the session id matches
			if (Objects.equal(sessionId.get(), s.getSessionId())) {
				return cache.delete(key);
			} else {
				throw new SecurityException("Session ID does not match");
			}
		}

		return false;
	}
	
	/**
	 * Generates the lookup <code>key</code> for the Session.
	 * 
	 * @param accountId
	 *            The Account ID associated with the Session.
	 * @return The session lookup <code>key</code>.
	 */
	private String generateSessionKey(String accountId) {
		return String.format("fpw.session.%s", accountId);
	}
}
