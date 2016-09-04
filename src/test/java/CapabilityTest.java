import com.accessone.resources.Capabilities;
import com.accessone.resources.Capability;
import java.util.Optional;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CapabilityTest
{
    @Test
    public void addCapabilitiesTest() throws Exception {
        Capabilities capabilities = new Capabilities();
        capabilities.addCapability(Capability.OC_ID_LOOKUP);
        capabilities.addCapability(Capability.OC_ALL_LOOKUP);
        capabilities.addCapability(Capability.OC_POS_RANGE_LOOKUP);

        // Check the size
        assertThat(capabilities.size() == 3).isTrue();
    }

    @Test
    public void existingCapabilityTest() throws Exception {
        Capabilities capabilities = new Capabilities();
        capabilities.addCapability(Capability.OC_ID_LOOKUP);
        capabilities.addCapability(Capability.OC_ALL_LOOKUP);
        capabilities.addCapability(Capability.OC_POS_RANGE_LOOKUP);

        Optional<Capability> idLookupCapability = capabilities.getCapability(Capability.OC_ID_LOOKUP);
        assertThat(idLookupCapability.isPresent()).isTrue();

        Optional<Capability> allLookupCapability = capabilities.getCapability(Capability.OC_ALL_LOOKUP);
        assertThat(allLookupCapability.isPresent()).isTrue();

        Optional<Capability> posRangeLookupCapability = capabilities.getCapability(Capability.OC_POS_RANGE_LOOKUP);
        assertThat(posRangeLookupCapability.isPresent()).isTrue();
    }

    @Test
    public void nonExistingCapabilityTest() throws Exception {
        Capabilities capabilities = new Capabilities();
        capabilities.addCapability(Capability.OC_ID_LOOKUP);
        capabilities.addCapability(Capability.OC_ALL_LOOKUP);
        capabilities.addCapability(Capability.OC_POS_RANGE_LOOKUP);

        Optional<Capability> idLookupCapability = capabilities.getCapability(Capability.OC_ID_DELETE);
        assertThat(idLookupCapability.isPresent()).isFalse();
    }
}