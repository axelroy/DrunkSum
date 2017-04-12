package ch.hearc.drunksum;

import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NumberPatternTest {

    private static Pattern NUMBER_PATTERN = Pattern.compile(".*(\\d|o|O)+ ?(\\.|\\,) ?(\\d|o|O){2}.*");

    private static String[] CORRECT = {
            "0.12", "20.00", "7,50", "7.5O", "  12.oo   ", "0o. O0", "   24 .30", "total  50. oo   CHF", "15.00    15.00"
    };

    private static String[] INCORRECT = {
            "bonjour", "12.5", "12"
    };

    @Test
    public void correctValues() {
        for(String value: CORRECT){
            boolean match = NUMBER_PATTERN.matcher(value).matches();
            assertTrue(match);

            System.out.println("raw: " + value);

            // remplace les virgules par des points
            value = value.replaceAll(",",".");
            // remplace les o par des 0
            value = value.replaceAll("[oO]","0");
            // supprime tout ce qui n'est pas un chiffre ou un point
            value = value.replaceAll("[^\\d.]", "");

            double dblValue = Double.parseDouble(value);
            System.out.println("filtered: " + dblValue);
        }
    }

    @Test
    public void incorrectValues() {
        for(String value: INCORRECT){
            assertFalse(NUMBER_PATTERN.matcher(value).matches());
        }
    }


}
