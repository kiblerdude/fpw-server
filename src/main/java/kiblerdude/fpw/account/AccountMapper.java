package kiblerdude.fpw.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class AccountMapper implements ResultSetMapper<UserAccount> {
	public UserAccount map(int index, ResultSet r, StatementContext ctx) throws SQLException {		
		Account account = new Account(r.getString("email"), r.getString("password"), r.getString("id"));
		Player player = new Player(r.getString("name"), 
				r.getLong("level"), r.getLong("experience"), 
				r.getLong("wins"), r.getLong("losses"), r.getLong("draws"), 
				r.getLong("credits"), r.getLong("karma"));		
		return new UserAccount(account, player);
	}
}
