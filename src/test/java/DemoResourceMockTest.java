import com.google.common.base.Optional;
import accessone.com.core.Cardholder;
import accessone.com.db.CardholderDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import accessone.com.resources.CardholdersResource;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

public class DemoResourceMockTest {

    private static final CardholderDAO mockCardholderDAO = mock(CardholderDAO.class);

    private final Cardholder cardholderFixture = new Cardholder(3L,"Mr","Ben","Morton", "BM001", 1, "ben.morton@pebble.tv");

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new CardholdersResource(mockCardholderDAO))
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
    public void getDemoSucess() throws IOException {
        Cardholder demo = resources.client().target("/cardholders/3").request().get(Cardholder.class);
        assertThat(demo)
                .isEqualTo(cardholderFixture);
        verify(mockCardholderDAO).findByCardholderID(3L);
    }

    @Test
    public void getDemoNotFound() {
        final Response response = resources.client().target("/cardholders/4").request().get();
        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(mockCardholderDAO).findByCardholderID(4L);
    }
}