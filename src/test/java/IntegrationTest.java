import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;

public class IntegrationTest {

    private static final String TMP_FILE = createTempFile();
    protected static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("testConfig.yml");
    protected static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    protected Client client;

    @ClassRule
    public final static DropwizardAppRule<accessone.com.cardholder_dropwizardConfiguration> RULE =
            new DropwizardAppRule<>(
            accessone.com.cardholder_dropwizardApplication.class, CONFIG_PATH,
            ConfigOverride.config("database.url", "jdbc:h2:" + TMP_FILE));

    @Before
    public void setup() {
        client = ClientBuilder.newClient();
    }

    @After
    public void teardown() throws Exception {
        client.close();
    }

    public String generateURI(String strPath)
    {
        final URI uri = UriBuilder.fromUri("http://{host}:{port}")
                .resolveTemplate("host", "127.0.0.1")
                .resolveTemplate("port", RULE.getLocalPort())
                .build();
        return uri.toString()  + strPath;
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("DBTest", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
