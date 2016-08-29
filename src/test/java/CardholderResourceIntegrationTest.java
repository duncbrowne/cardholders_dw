import com.accessone.CardholderApp;
import com.accessone.CardholderConfig;
import com.accessone.core.Cardholder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CardholderResourceIntegrationTest {

    private static final int numItems = 10;

    @Test
    public void step1_createItems() {

        Cardholder cardholder = new Cardholder();

        for (int i = 0; i < this.numItems; i++) {
            String title = "Tit" + i;
            cardholder.setTitle(title);
            String firstName = "FirstName" + i;
            cardholder.setFirstName(firstName);
            String surname = "Surname" + i;
            cardholder.setSurname(surname);
            String employeeNumber = "EN" + i;
            cardholder.setEmployeeNumber(employeeNumber);
            int departmentID = i;
            cardholder.setDepartmentID(departmentID);
            String emailAddress = "EmailAddress" + i;
            cardholder.setEmailAddress(emailAddress);

            final Cardholder newCardholder = client.target(generateURI("/cardholders"))
                    .request()
                    .post(Entity.entity(cardholder, MediaType.APPLICATION_JSON_TYPE))
                    .readEntity(Cardholder.class);
            assertThat(newCardholder.getId()).isEqualTo(i+1);
            assertThat(newCardholder.getTitle()).isEqualTo(cardholder.getTitle());
            assertThat(newCardholder.getFirstName()).isEqualTo(cardholder.getFirstName());
            assertThat(newCardholder.getSurname()).isEqualTo(cardholder.getSurname());
            assertThat(newCardholder.getEmployeeNumber()).isEqualTo(cardholder.getEmployeeNumber());
            assertThat(newCardholder.getDepartmentID()).isEqualTo(cardholder.getDepartmentID());
            assertThat(newCardholder.getEmailAddress()).isEqualTo(cardholder.getEmailAddress());

        }

        // Verify the DB contains all the items
        final List<Cardholder> listDemo = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });
        assertThat(listDemo.size()).isEqualTo(this.numItems);
    }

    @Test
    public void step2_testGetById() throws InterruptedException {

        List<Cardholder> listCardholders = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });
        for(int i = 0; i < listCardholders.size(); i++)
        {
            Cardholder cardholder = listCardholders.get(i);
            Cardholder cardholderGet = client.target(generateURI("/cardholders/" + cardholder.getId()))
                    .request()
                    .get(Cardholder.class);
            assertThat(cardholderGet).isEqualTo(cardholder);
        }
    }

    @Test
    public void step3_testGetByIdInvalid() throws InterruptedException {

        Response response =  client.target(generateURI("/cardholders/99999"))
                .request()
                .get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void step4_testGetByRange() throws InterruptedException {

        List<Cardholder> listCardholders = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });

        List<Cardholder> listCardholdersRange =
                client.target(generateURI("/cardholders?from=0&to="+listCardholders.size()))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });

        assertThat(listCardholders).isEqualTo(listCardholdersRange);
    }

    @Test
    public void step5_testUpdate() throws InterruptedException {

        Cardholder cardholderOld = client.target(generateURI("/cardholders/1"))
                .request()
                .get(Cardholder.class);

        cardholderOld.setTitle("Test");
        cardholderOld.setFirstName("TestFirstName");
        cardholderOld.setSurname("TestSurname");
        cardholderOld.setEmployeeNumber("TestEN");
        cardholderOld.setDepartmentID(1);
        cardholderOld.setEmployeeNumber("TestEmailAddress");

        client.target(generateURI("/cardholders/1"))
                .request()
                .post(Entity.entity(cardholderOld, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Cardholder.class);

        Cardholder cardholderNew = client.target(generateURI("/cardholders/1"))
                .request()
                .get(Cardholder.class);

        assertThat(cardholderOld).isEqualTo(cardholderNew);
    }

    @Test
    public void step6_testUpdateInvalid() throws InterruptedException {

        Cardholder cardholderOld = client.target(generateURI("/cardholders/1"))
                .request()
                .get(Cardholder.class);

        Response response = client.target(generateURI("/cardholders/99999"))
                .request()
                .post(Entity.entity(cardholderOld, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void step7_testDelete() throws InterruptedException {

        List<Cardholder> listCardholders = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });
        for(int i = 0; i < listCardholders.size(); i++)
        {
            Cardholder cardholder = listCardholders.get(i);
            client.target(generateURI("/cardholders/" + cardholder.getId()))
                    .request()
                    .delete();
        }

        // Verify DB is empty
        listCardholders = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>(){});
        assertThat(listCardholders.size()).isEqualTo(0);
    }

    @Test
    public void step8_testDeleteInvalid() {

        Response response = client.target(generateURI("/cardholders/1"))
                    .request()
                    .delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    private static final String TMP_FILE = createTempFile();
    protected static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("testConfig.yml");
    protected static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    protected Client client;

    @ClassRule
    public final static DropwizardAppRule<CardholderConfig> RULE = new DropwizardAppRule<>(
            CardholderApp.class, CONFIG_PATH,
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
