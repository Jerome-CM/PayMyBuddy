package fr.cm.paymybuddy.Utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class Utility {

    private static final Logger logger = LogManager.getLogger(Utility.class);

    public static double stringCommaToDoublePoint(String amount){
        String point = amount.replace(",", ".");
        return Double.parseDouble(point);
    }
}
