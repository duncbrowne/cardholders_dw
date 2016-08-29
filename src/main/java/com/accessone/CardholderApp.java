package com.accessone;

import com.accessone.core.Cardholder;
import com.accessone.db.CardholderDAO;
import com.accessone.resources.CardholdersResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Environment;

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
        // Demo Resource
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final CardholderDAO cardholderDAO = new CardholderDAO(hibernateBundle.getSessionFactory());
        final CardholdersResource cardholdersResource =
                new CardholdersResource(cardholderDAO);
        environment.jersey().register(cardholdersResource);
    }

}
