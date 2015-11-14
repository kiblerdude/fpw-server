package kiblerdude.fpw;

import kiblerdude.fpw.account.AccountResource;
import kiblerdude.fpw.account.AccountService;
import kiblerdude.fpw.card.CardResource;
import kiblerdude.fpw.common.CacheService;
import kiblerdude.fpw.common.CryptographyService;
import kiblerdude.fpw.common.SerializationService;
import kiblerdude.fpw.feature.AuthenticationRequiredFeature;
import kiblerdude.fpw.news.NewsResource;
import kiblerdude.fpw.news.NewsService;
import kiblerdude.fpw.session.SessionResource;
import kiblerdude.fpw.session.SessionService;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class FPW extends Application<FPWConfiguration> {

	public FPW() {

	}
	
	@Override
    public String getName() {
        return "fpw";
    }

	@Override
	public void initialize(Bootstrap<FPWConfiguration> bootstrap) {
		// nothing to do (yet)
	}

	@Override
	public void run(FPWConfiguration configuration, Environment environment) throws Exception {
		Injector injector = Guice.createInjector(new FPWModule(configuration, environment));

	    // manage services
	    environment.lifecycle().manage(injector.getInstance(SerializationService.class));
	    environment.lifecycle().manage(injector.getInstance(CacheService.class));
	    environment.lifecycle().manage(injector.getInstance(SessionService.class));
	    environment.lifecycle().manage(injector.getInstance(AccountService.class));
	    environment.lifecycle().manage(injector.getInstance(CryptographyService.class));
	    environment.lifecycle().manage(injector.getInstance(NewsService.class));
	    
        // add filters (using features)
        environment.jersey().register(injector.getInstance(AuthenticationRequiredFeature.class));
	    
	    // register resources
        environment.jersey().register(injector.getInstance(NewsResource.class));
	    environment.jersey().register(injector.getInstance(AccountResource.class));
	    environment.jersey().register(injector.getInstance(SessionResource.class));
	    environment.jersey().register(injector.getInstance(CardResource.class));
	}

	public static void main(String[] args) throws Exception {
		new FPW().run(args);
	}
}
