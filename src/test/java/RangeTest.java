import com.accessone.resources.Capabilities;
import com.accessone.resources.Capability;
import com.accessone.resources.Range;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class RangeTest
{
    @Test
    public void minus1FromMinus1ToTest() throws Exception
    {
        int from = -1;
        int to = -1;
        Range range = new Range(from, to);

        // Check the values
        assertThat(range.getTo().equals("Last")).isTrue();
        assertThat(range.getFrom().equals("First")).isTrue();
        assertThat(range.getType().equals("all")).isTrue();
        assertThat(range.getPage() == 1).isTrue();
        assertThat(range.getIsComplete()).isTrue();
    }

    @Test
    public void minus1FromNonMinus1ToTest() throws Exception
    {
        int from = -1;
        int to = 4;
        Range range = new Range(from, to);

        // Check the values
        assertThat(range.getTo().equals("4")).isTrue();
        assertThat(range.getFrom().equals("-1")).isTrue();
        assertThat(range.getType().equals("range")).isTrue();
        assertThat(range.getPage() == 1).isTrue();
        assertThat(range.getIsComplete()).isTrue();
    }

    @Test
    public void nonMinus1FromMinus1ToTest() throws Exception
    {
        int from = 3;
        int to = -1;
        Range range = new Range(from, to);

        // Check the values
        assertThat(range.getTo().equals("-1")).isTrue();
        assertThat(range.getFrom().equals("3")).isTrue();
        assertThat(range.getType().equals("range")).isTrue();
        assertThat(range.getPage() == 1).isTrue();
        assertThat(range.getIsComplete()).isTrue();
    }

    @Test
    public void nonMinus1FromNonMinus1ToTest() throws Exception
    {
        int from = 1;
        int to = 3;
        Range range = new Range(from, to);

        // Check the values
        assertThat(range.getTo().equals("3")).isTrue();
        assertThat(range.getFrom().equals("1")).isTrue();
        assertThat(range.getType().equals("range")).isTrue();
        assertThat(range.getPage() == 1).isTrue();
        assertThat(range.getIsComplete()).isTrue();
    }

    @Test
    public void equalsToTest()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Range range2 = new Range(-1, -1);
        range2.setFrom(Integer.toString(from));
        range2.setTo(Integer.toString(to));
        range2.setType("range");
        range2.setPage(1);
        range2.setIsComplete(true);

        assertThat(range1.equals(range2)).isTrue();
    }

    @Test
    public void notEqualsToTestFrom()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Range range2 = new Range(-1, -1);
        range2.setTo(Integer.toString(to));
        range2.setType("range");
        range2.setPage(1);
        range2.setIsComplete(true);

        assertThat(range1.equals(range2)).isFalse();
    }

    @Test
    public void notEqualsToTestTo()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Range range2 = new Range(-1, -1);
        range2.setFrom(Integer.toString(from));
        range2.setType("range");
        range2.setPage(1);
        range2.setIsComplete(true);

        assertThat(range1.equals(range2)).isFalse();
    }

    @Test
    public void notEqualsToTestType()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Range range2 = new Range(-1, -1);
        range2.setTo(Integer.toString(to));
        range2.setFrom(Integer.toString(from));
        range2.setPage(1);
        range2.setIsComplete(true);

        assertThat(range1.equals(range2)).isFalse();
    }

    @Test
    public void notEqualsToTestPage()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Range range2 = new Range(-1, -1);
        range2.setTo(Integer.toString(to));
        range2.setFrom(Integer.toString(from));
        range2.setType("range");
        range2.setPage(2);
        range2.setIsComplete(false);

        assertThat(range1.equals(range2)).isFalse();
    }

    @Test
    public void notEqualsToTestIsComplete()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Range range2 = new Range(-1, -1);
        range2.setTo(Integer.toString(to));
        range2.setFrom(Integer.toString(from));
        range2.setType("range");
        range2.setPage(1);
        range2.setIsComplete(false);

        assertThat(range1.equals(range2)).isFalse();
    }

    @Test
    public void notEqualsToTestObject()
    {
        int from = 2;
        int to = 4;
        Range range1 = new Range(from, to);

        Capabilities mockCapabilities = mock(Capabilities.class);

        assertThat(range1.equals(mockCapabilities)).isFalse();
    }
}