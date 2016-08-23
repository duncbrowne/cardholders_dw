package accessone.com;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.*;

public class cardholder_dropwizardConfiguration extends Configuration {

    private String serviceId = "RESTAPIHelper";
    private String serviceName = "RESTAPIHelperName";

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory()
    {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory)
    {
        this.database = dataSourceFactory;
    }
}
