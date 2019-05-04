package tk.roydgar.restinitializr.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class FormatUtilsTest {

    @Test
    public void deleteQuotes_deletesBackQuotes() {
        String target = "`test`";

        String actual = FormatUtils.deleteQuotes(target);

        assertThat(actual).isEqualTo("test");
    }

    @Test
    public void deleteQuotes_deletesSingleQuotes() {
        String target = "'test'";

        String actual = FormatUtils.deleteQuotes(target);

        assertThat(actual).isEqualTo("test");
    }

    @Test
    public void deleteQuotes_deletesQuotes() {
        String target = "\"test\"";

        String actual = FormatUtils.deleteQuotes(target);

        assertThat(actual).isEqualTo("test");
    }

    @Test
    public void deleteQuotes_deletesQuoteInTheMiddle() {
        String target = "'te'st'";

        String actual = FormatUtils.deleteQuotes(target);

        assertThat(actual).isEqualTo("test");
    }

    @Test
    public void deleteQuotes_deletesDifferentTypeOfQuotes() {
        String target = "`te'st\"";

        String actual = FormatUtils.deleteQuotes(target);

        assertThat(actual).isEqualTo("test");
    }

}