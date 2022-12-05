package fr.cm.paymybuddy;

import fr.cm.paymybuddy.Utility.Utility;
import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UtilityTest {

    @Test
    public void stringCommaToDoublePointTest(){
        String amountBrut = "50,00";
        double result = Utility.stringCommaToDoublePoint(amountBrut);

        assertEquals("", 50.0, result);

    }
}
