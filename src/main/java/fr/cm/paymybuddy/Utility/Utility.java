package fr.cm.paymybuddy.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Utility {

    /**
     *
     * @param o Objet ( List, ArrayList, Map, Object, ... )
     * @return A print of JsonFormat
     */
    public static String jsonEncode(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    private static final Logger logger = LogManager.getLogger(Utility.class);

    /**
     *
     * @param amount
     * @return
     */
    public static double stringCommaToDoublePoint(String amount){

            String point = amount.replace(",", ".");
            return Double.parseDouble(point);
     
    }
}
