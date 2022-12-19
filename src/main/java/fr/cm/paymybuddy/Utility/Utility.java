package fr.cm.paymybuddy.Utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Utility {

    private static final Logger logger = LogManager.getLogger(Utility.class);

    /**
     *
     * @param amount
     * @return
     */
    public static double stringCommaToDoublePoint(String amount){

        String regex = "[0-9]{1,9}[,.][0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(amount);

        if(matcher.matches()){
            String point = amount.replace(",", ".");
            return Double.parseDouble(point);
        } else {
            logger.info("Illegal value for input {}", amount);
            throw new ArithmeticException("The regex doesn't match with amount");
        }
    }
}
