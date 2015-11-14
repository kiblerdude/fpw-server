package kiblerdude.fpw.account;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.google.common.base.Optional;

import io.dropwizard.lifecycle.Managed;
import kiblerdude.fpw.common.CryptographyService;

/**
 * Service for Accounts that validates account information and translates account data between
 * the client layer (Resources) and the data layer (DAOs).
 * 
 * @author kiblerj
 *
 */
public class AccountService implements Managed {

	private static final Logger LOG = Logger.getLogger(AccountService.class);
	
	private static final int MIN_NAME_LENGTH = 4;
	private static final int MAX_NAME_LENGTH = 48;
	private static final int MAX_EMAIL_LENGTH = 128;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_PASSWORD_LENGTH = 64;
	
	private final CryptographyService cryptography;
	private final AccountDAO dao;
	
	public AccountService(CryptographyService cryptography, AccountDAO dao) {
		this.dao = dao;
		this.cryptography = cryptography;
	}
	
	@Override
	public void start() throws Exception {
		LOG.info("Start");
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stop");
	}
	
	/**
	 * Creates a new account.
	 * 
	 * @param account
	 * @return
	 */
	public Optional<UserAccount> createAccount(AccountCredentials credentials) {
		// generate an id for the account
		String id = UUID.randomUUID().toString();
		String email = credentials.getEmail();
		String password = credentials.getPassword();
		String name = credentials.getName();
		
		// verify the account is valid
		if (!validEmail(email)) {
			return Optional.absent();
		} else if (!validPassword(password)) {
			return Optional.absent();
		} else if (!validName(name)) {
			return Optional.absent();
		}

		// encrypt the password
		String hashedPassword = cryptography.encrypt(credentials.getPassword());
		
		// store the account with the encrypted password in the database
		if (dao.create(email, hashedPassword, id, name) == 1)  {
			Account newAccount = new Account(email, id);
			Player newPlayer = new Player(name);
			return Optional.of(new UserAccount(newAccount, newPlayer));
		}
		
		return Optional.absent();
	}
	
	/**
	 * Updates an existing account.
	 * 
	 * @param account
	 * @return
	 */
	public Optional<Account> updateAccountCredentials(UpdatedAccountCredentials updated) {
		UserAccount user = dao.get(updated.getEmail());

		// account does not exist
		if (user == null) {
			return Optional.absent();
		} else if (cryptography.checkPassword(updated.getPassword(), user.getAccount().getPassword())) {
			String newEmail = updated.getNewEmail() == null ? updated.getEmail() : updated.getNewEmail();
			String newPassword = updated.getNewPassword() == null ? updated.getPassword() : updated.getNewPassword();
			
			// encrypt the password
			String hashedPassword = cryptography.encrypt(newPassword);
			
			if (dao.updateCredentials(user.getAccount().getEmail(), newEmail, hashedPassword) == 1) {
				return Optional.of(new Account(newEmail, user.getAccount().getId()));
			}
			return Optional.absent();
		}
		
		return Optional.absent();
	}
	
	/**
	 * Removes an existing account.
	 * 
	 * @param email
	 * @param password
	 * @return <code>true</code> if the account was removed, <code>false</code> otherwise.
	 */
	public boolean removeAccount(Optional<String> email, Optional<String> password) {
		
		if (!email.isPresent() || !password.isPresent()) {
			return false;
		}
		
		UserAccount user = dao.get(email.get());
		
		// account does not exist
		if (user == null) {
			return false;
		} else if (cryptography.checkPassword(password.get(), user.getAccount().getPassword())) {
			return dao.delete(email.get()) == 1;
		}
		
		return false;		
	}
	
	/**
	 * Authenticates an existing account.
	 * 
	 * @param email
	 * @param password
	 * @return The account id
	 */
	public Optional<UserAccount> authenticateAccount(
			Optional<String> email,
			Optional<String> password) {
		
		if (!email.isPresent() || !password.isPresent()) {
			return Optional.absent();
		}
		
		UserAccount user = dao.get(email.get());
		
		if (user == null) {
			return Optional.absent();
		} else if (cryptography.checkPassword(password.get(), user.getAccount().getPassword())) {
			return Optional.of(user);
		}
		
		return Optional.absent();
	}
	
	/**
	 * Returns <code>true</code> if the email is valid.
	 */
	private boolean validEmail(String email) {
		if (email == null || email.isEmpty() || email.length() > MAX_EMAIL_LENGTH) {
			return false;
		}
		
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(email)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns <code>true</code> if the password is valid.
	 */
	private boolean validPassword(String password) {
		if (password == null || password.length() < MIN_PASSWORD_LENGTH ||
				password.length() > MAX_PASSWORD_LENGTH) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if the name is valid.
	 */
	private boolean validName(String name) {
		if (name == null || name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			return false;
		}
		if (!StringUtils.isAlphanumericSpace(name)) {
			return false;
		}
		return true;
	}
}
