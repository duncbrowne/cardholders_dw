import io.dropwizard.jackson.Jackson;
import org.junit.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import accessone.com.core.Cardholder;
import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class DemoTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

//    @Test
//    public void serializesToJSON() throws Exception {
//        final Cardholder cardholder = new Demo(3L,"Demo3","Description Demo3");
//        final String expected = MAPPER.writeValueAsString(
//                MAPPER.readValue(fixture("fixtures/demo3.json"), Demo.class));
//        assertThat(MAPPER.writeValueAsString(demo)).isEqualTo(expected);
//    }
//
//    @Test
//    public void deserializesFromJSON() throws Exception {
//        final Demo demo = new Demo(3L,"Demo3","Description Demo3");
//        assertThat(MAPPER.readValue(fixture("fixtures/demo3.json"), Demo.class))
//                .isEqualTo(demo);
//    }
//
//    @Test
//    public void equals() throws Exception {
//        final Demo demo1 = new Demo();
//        final Demo demo2 = MAPPER.readValue(fixture("fixtures/demo2.json"), Demo.class);
//        final Demo demo3 = MAPPER.readValue(fixture("fixtures/demo3.json"), Demo.class);
//
//        assertThat(demo2).isNotEqualTo(null);
//        assertThat(demo2).isNotEqualTo(demo1);
//        assertThat(demo2).isNotEqualTo(demo3);
//        assertThat(demo2).isEqualTo(demo2);
//    }
}