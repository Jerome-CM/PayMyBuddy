package fr.cm.paymybuddy;

import fr.cm.paymybuddy.Utility.Utility;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.matchers.JUnitMatchers;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class UtilityTest {

    @Test
    public void stringCommaToDoublePointTest(){
        String amountBrut = "50,00";
        double result = Utility.stringCommaToDoublePoint(amountBrut);

        assertEquals("", 50.0, result);

    }

    @Test
    public void stringCommaToDoublePointExceptionTest(){

        assertThrows(ArithmeticException.class, () -> Utility.stringCommaToDoublePoint("Daniel"));
    }
}
