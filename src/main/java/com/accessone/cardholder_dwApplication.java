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

public class cardholder_dwApplication extends Application<cardholder_dwConfiguration> {

    public static void main(final String[] args) throws Exception
    {
        new cardholder_dwApplication().run(args);
    }

    private final HibernateBundle<cardholder_dwConfiguration> hibernateBundle =
        new HibernateBundle<cardholder_dwConfiguration>(Cardholder.class)
    {
        @Override
        public DataSourceFactory getDataSourceFactory(cardholder_dwConfiguration configuration)
        {
            return configuration.getDataSourceFactory();
        }

    };



    @Override
    public String getName() {
        return "cardholder_dz";
    }

    @Override
    public void initialize(final Bootstrap<cardholder_dwConfiguration> bootstrap)
    {
        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new MigrationsBundle<cardholder_dwConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(cardholder_dwConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final cardholder_dwConfiguration configuration,
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
