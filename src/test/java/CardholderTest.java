import com.accessone.core.Cardholder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class CardholderTest
{

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Cardholder cardholder = new Cardholder(3L,"TestTitle3", "TestFirstName3",
                "TestSurname3", "TestEmpNo3", 3, "TestEmailAddress3");
        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/cardholder3.json"), Cardholder.class));
        assertThat(MAPPER.writeValueAsString(cardholder)).isEqualTo(expected);
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final Cardholder cardholder = new Cardholder(3L,"TestTitle3", "TestFirstName3",
                "TestSurname3", "TestEmpNo3", 3, "TestEmailAddress3");
        assertThat(MAPPER.readValue(fixture("fixtures/cardholder3.json"), Cardholder.class))
                .isEqualTo(cardholder);
    }

    @Test
    public void equals() throws Exception {
        final Cardholder cardholder1 = new Cardholder();
        final Cardholder cardholder2 =
                MAPPER.readValue(fixture("fixtures/cardholder2.json"), Cardholder.class);
        final Cardholder cardholder3 =
                MAPPER.readValue(fixture("fixtures/cardholder3.json"), Cardholder.class);

        assertThat(cardholder2).isNotEqualTo(null);
        assertThat(cardholder2).isNotEqualTo(cardholder1);
        assertThat(cardholder2).isNotEqualTo(cardholder3);
        assertThat(cardholder2).isEqualTo(cardholder2);
    }
}