package kiblerdude.fpw;

import org.skife.jdbi.v2.DBI;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;

import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import kiblerdude.fpw.account.AccountDAO;
import kiblerdude.fpw.account.AccountService;
import kiblerdude.fpw.card.CardDAO;
import kiblerdude.fpw.card.MatchDAO;
import kiblerdude.fpw.common.CacheService;
import kiblerdude.fpw.common.CryptographyService;
import kiblerdude.fpw.common.SerializationService;
import kiblerdude.fpw.news.NewsService;
import kiblerdude.fpw.session.SessionService;

/**
 * Guice Module for FPW App.
 * 
 * @author kiblerj
 *
 */
public class FPWModule extends AbstractModule {
	
	private final FPWConfiguration configuration;
    private final DBIFactory factory;
    private final DBI jdbi;	
	
	public FPWModule(FPWConfiguration configuration, Environment environment) {
		this.configuration = configuration;
	    this.factory = new DBIFactory();
	    this.jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");			
	}

	@Override
	protected void configure() {
	    bind(SerializationService.class).in(Scopes.SINGLETON);
	    bind(CacheService.class).in(Scopes.SINGLETON);
	    bind(SessionService.class).in(Scopes.SINGLETON);
	    bind(AccountService.class).in(Scopes.SINGLETON);
	    bind(CryptographyService.class).in(Scopes.SINGLETON);
	    bind(NewsService.class).in(Scopes.SINGLETON);
	}
	
	@Provides
	public RedisConfiguration provideRedisConfiguration() {
		return configuration.getRedisConfiguration();
	}
	
	@Provides
	public AccountDAO provideAccountDAO() {
		return jdbi.onDemand(AccountDAO.class);
	}
	
	@Provides
	public CardDAO provideCardDAO() {
		return jdbi.onDemand(CardDAO.class);
	}

	@Provides
	public MatchDAO provideMatchDAO() {
		return jdbi.onDemand(MatchDAO.class);
	}
}
