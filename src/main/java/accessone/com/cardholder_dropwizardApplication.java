package accessone.com;

import accessone.com.core.Cardholder;
import accessone.com.db.CardholderDAO;
import accessone.com.resources.CardholdersResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.lifecycle.ServerLifecycleListener;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class cardholder_dropwizardApplication extends Application<cardholder_dropwizardConfiguration> {

    public static void main(final String[] args) throws Exception
    {
        new cardholder_dropwizardApplication().run(args);
    }

    private final HibernateBundle<cardholder_dropwizardConfiguration> hibernateBundle =
        new HibernateBundle<cardholder_dropwizardConfiguration>(Cardholder.class) {
            @Override
                public DataSourceFactory getDataSourceFactory(cardholder_dropwizardConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };



    @Override
    public String getName() {
        return "cardholder_dropwizard";
    }

    @Override
    public void initialize(final Bootstrap<cardholder_dropwizardConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());

        bootstrap.addBundle(new MigrationsBundle<cardholder_dropwizardConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(cardholder_dropwizardConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

        bootstrap.addBundle(hibernateBundle);    }

    @Override
    public void run(final cardholder_dropwizardConfiguration configuration,
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
