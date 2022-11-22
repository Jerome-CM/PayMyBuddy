package fr.cm.paymybuddy.Utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.cm.paymybuddy.Model.User;
import fr.cm.paymybuddy.Repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Service
public class Utility {

    private static final Logger logger = LogManager.getLogger(Utility.class);
    @Autowired
    UserRepository userRepository;

    public static <T> T jsonDecode(String json, Class<T> tClass) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, tClass);
        } catch (Exception ex) {
            return null;
        }
    }

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

    public boolean isUserExist(String mail){

        User user = userRepository.findByMail(mail);
        logger.info("user finded : {}", user);
        if(user == null){
            return false;
        } else {
             return true;
        }
    }

    public long idUser(String mail){

        long id = userRepository.findByMail(mail).getId();

        return id;
    }

    public static double stringCommaToDoublePoint(String amount){

        String point = amount.replace(",", ".");
        return Double.parseDouble(point);


    }
}
