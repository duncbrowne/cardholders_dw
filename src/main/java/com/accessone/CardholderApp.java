package com.accessone;

import com.accessone.core.Cardholder;
import com.accessone.db.CardholderDAO;
import com.accessone.resources.CardholdersResource;
import com.accessone.resources.HAL.CardholdersResourceHAL;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Environment;

import java.util.HashMap;

/**
 * This is the main ClassholderApp class.  It is where the main methode resides.
 */
public class CardholderApp extends Application<CardholderConfig> {

    private final HibernateBundle<CardholderConfig> hibernateBundle =
            new HibernateBundle<CardholderConfig>(Cardholder.class)
            {
                @Override
                public DataSourceFactory getDataSourceFactory(CardholderConfig configuration)
                {
                    return configuration.getDataSourceFactory();
                }

            };

    /**
     * The main method for the Cardholder_dw service
     * @param args takes two arguments.  The first is "server" and the second is the name of the config (YAML)
     *             file to be used
     * @throws Exception Any exception that is not handled in normal operation.
     */
    public static void main(final String[] args) throws Exception
    {
        new CardholderApp().run(args);
    }

    @Override
    public String getName() {
        return "cardholder_dw";
    }

    @Override
    public void initialize(final Bootstrap<CardholderConfig> bootstrap)
    {
        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new MigrationsBundle<CardholderConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(CardholderConfig configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final CardholderConfig configuration,
                    final Environment environment) {

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Components Resource
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        HashMap<String,Component> components = new HashMap<String,Component>();
//
//        final Component componentNodeRoot = new Component("root","CardholdersService",null);
//        components.put(componentNodeRoot.getId(), componentNodeRoot);
//        final RESTComponentResource componentResource = new RESTComponentResource(components);
//        environment.jersey().register(componentResource);
//        RESTComponentResourceHAL componentResourceHAL = new RESTComponentResourceHAL(componentResource);
//        environment.jersey().register(componentResourceHAL);

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Cardholder Resource
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final CardholderDAO cardholderDAO = new CardholderDAO(hibernateBundle.getSessionFactory());
        final CardholdersResource cardholdersResource =
                new CardholdersResource("cardholderResource", "cardholderResource", cardholderDAO);
        environment.jersey().register(cardholdersResource);
        environment.jersey().register(new CardholdersResourceHAL(cardholdersResource));

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // EntryPoint Resource
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        final CardholdersServiceEntryPointResourceHAL entryPointResourceHAL =
//                new CardholdersServiceEntryPointResourceHAL("CardholderService","CardholderService");
//        environment.jersey().register(entryPointResourceHAL);
    }

}
