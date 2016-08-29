import com.accessone.core.Cardholder;
import com.accessone.db.CardholderDAO;
import com.accessone.resources.CardholdersResource;
import com.google.common.base.Optional;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CardholderResourceMockTest
{
    private static final CardholderDAO mockCardholderDAO = mock(CardholderDAO.class);

    private final Cardholder cardholderFixture = new Cardholder(3L,"TestTitle3", "TestFirstName3",
            "TestSurname3", "TestEmpNo3", 3, "TestEmailAddress3");

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new CardholdersResource("cardholderResource", "cardholderResource", mockCardholderDAO))
            .build();

    @Before
    public void setup() {
        when(mockCardholderDAO.findByCardholderID(3L)).thenReturn(Optional.of(cardholderFixture));
        when(mockCardholderDAO.findByCardholderID(4L)).thenReturn(Optional.<Cardholder>absent());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void getDemoSuccess() throws IOException {
        Cardholder cardholder =
                resources.client().target("/cardholders/3").request().get(Cardholder.class);
        assertThat(cardholder)
                .isEqualTo(cardholderFixture);
        verify(mockCardholderDAO).findByCardholderID(3L);
    }

    @Test
    public void getDemoNotFound() {
        final Response response = resources.client().target("/cardholders/4").request().get();
        assertThat(response.getStatusInfo().getStatusCode()).
                isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(mockCardholderDAO).findByCardholderID(4L);
    }
}