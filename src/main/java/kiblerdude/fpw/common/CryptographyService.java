package kiblerdude.fpw.common;

import io.dropwizard.lifecycle.Managed;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class CryptographyService implements Managed {
	private static final Logger LOG = Logger.getLogger(CryptographyService.class);
	
	public CryptographyService() {

	}
	
	@Override
	public void start() throws Exception {
		LOG.info("Starting Encryption Services");
	}

	@Override
	public void stop() throws Exception {
		LOG.info("Stopping Encryption Services");
	}
	
	public String encrypt(String password) {
		// return the hashed password
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public Boolean checkPassword(String clear, String encrypted) {
		// check the clear password against the hashed version
		return BCrypt.checkpw(clear, encrypted) ? Boolean.TRUE : Boolean.FALSE;		
	}
}
