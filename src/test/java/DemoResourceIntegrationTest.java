import accessone.com.core.Cardholder;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.smartcardio.Card;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DemoResourceIntegrationTest extends IntegrationTest {

    private static final int numItems = 10;

    @Test
    public void step1_createItems() {

        Cardholder cardholder = new Cardholder();

        for (int i = 0; i < this.numItems; i++)
        {
            String title = "Mr";
            String firstName = "FirstName " + i;
            String surname = "Surname " + i;
            String employeeName = "Emp " + i;
            long departmentId = i;
            String emailAddress = "email " + i;

            final Cardholder newCardholder = client.target(generateURI("/cardholders"))
                    .request()
                    .post(Entity.entity(cardholder, MediaType.APPLICATION_JSON_TYPE))
                    .readEntity(Cardholder.class);
            assertThat(newCardholder.getCardholderID()).isEqualTo(i+1);
//            assertThat(newDemo.g()).isEqualTo(demo.getName());
//            assertThat(newDemo.getDescription()).isEqualTo(demo.getDescription());
        }

        // Verify the DB contains all the items
        final List<Cardholder> listCardholder = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });
        assertThat(listCardholder.size()).isEqualTo(this.numItems);
    }

    @Test
    public void step2_testGetById() throws InterruptedException {

        List<Cardholder> listDemo = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });
        for(int i = 0; i < listDemo.size(); i++)
        {
            Cardholder cardholder = listDemo.get(i);
            Cardholder cardholderGet = client.target(generateURI("/cardholders/" + cardholder.getCardholderID()))
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

        List<Cardholder> listCardholder = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });

        List<Cardholder> listDemoRange =
                client.target(generateURI("/cardholders?from=0&to="+listCardholder.size()))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });

        assertThat(listCardholder).isEqualTo(listDemoRange);
    }

    @Test
    public void step5_testUpdate() throws InterruptedException {

        Cardholder cardholderOld = client.target(generateURI("/cardholders/1"))
                .request()
                .get(Cardholder.class);

        cardholderOld.setTitle("TestTitle");
        cardholderOld.setFirstName("TestFirstName");
        cardholderOld.setSurname("TestSurname");
        cardholderOld.setDepartmentID(1);
        cardholderOld.setEmailAddress("TestEmail");
        cardholderOld.setEmployeeNumber("TestEmployeeNumber");

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

        cardholderOld.setTitle("TestTitle");
        cardholderOld.setFirstName("TestFirstName");
        cardholderOld.setSurname("TestSurname");
        cardholderOld.setDepartmentID(1);
        cardholderOld.setEmailAddress("TestEmail");
        cardholderOld.setEmployeeNumber("TestEmployeeNumber");

        Response response = client.target(generateURI("/cardholders/99999"))
                .request()
                .post(Entity.entity(cardholderOld, MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void step7_testDelete() throws InterruptedException {

        List<Cardholder> listCardholder = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>() {
                });
        for(int i = 0; i < listCardholder.size(); i++)
        {
            Cardholder demo = listCardholder.get(i);
            client.target(generateURI("/cardholders/" + demo.getCardholderID()))
                    .request()
                    .delete();
        }

        // Verify DB is empty
        listCardholder = client.target(generateURI("/cardholders"))
                .request()
                .get(new GenericType<List<Cardholder>>(){});
        assertThat(listCardholder.size()).isEqualTo(0);
    }

    @Test
    public void step8_testDeleteInvalid() {

        Response response = client.target(generateURI("/carholders/1"))
                    .request()
                    .delete();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
    }
}
