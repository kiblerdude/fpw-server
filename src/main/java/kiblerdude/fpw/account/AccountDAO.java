package kiblerdude.fpw.account;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(AccountMapper.class)
public interface AccountDAO {

	/**
	 * Creates an Account in the database.
	 * 
	 * @param email
	 * @param password
	 * @param id
	 * @param name
	 * @return 1 if created, 0 if not created.
	 */
	@SqlUpdate("insert into accounts (email, password, id, name, experience, karma, level, wins, losses, draws, credits) "
			+ "values (:email, :password, :id, :name, 0, 0, 1, 0, 0, 0, 100)")
	int create(
			@Bind("email") String email,
			@Bind("password") String password,
			@Bind("id") String id,
			@Bind("name") String name);

	/**
	 * Updates an Account in the database.
	 * 
	 * @param email
	 * @param password
	 * @param id
	 * @return 1 if updated, 0 if not updated.
	 */
	@SqlUpdate("update accounts set email = :newEmail, password = :newPassword where email = :email limit 1")
	int updateCredentials(@Bind("email") String email, @Bind("newEmail") String newEmail, @Bind("newPassword") String newPassword);

	/**
	 * Removes an Account in the database.
	 * 
	 * @param email
	 * @param password
	 * @return 1 if removed, 0 if not removed.
	 */
	@SqlUpdate("delete from accounts where email = :email limit 1")
	int delete(@Bind("email") String email);

	/**
	 * Gets an Account from the database.
	 * 
	 * @param email
	 * @return the Account, or null if not found.
	 */
	@SqlQuery("select * from accounts where email = :email limit 1")
	UserAccount get(@Bind("email") String email);
	
	/**
	 * 
	 */
	@SqlQuery("select * from accounts where id = :id limit 1")
	UserAccount getById(@Bind("id") String id);
}
