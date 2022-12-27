package fr.cm.paymybuddy.Service.Implementation;


import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Service
public class OtherService implements OtherServiceInt {

    private static final Logger logger = LogManager.getLogger(OtherService.class);

    /**
     * Create a breadcrumb trail
     * @param url
     * @return an array content words and /
     */
    public List<String> accessPath(String url){

        // Target : Split the words and add upperCase for the first char of each word
        String[] words = url.split("/");

        List<String> UpOneCaseToTheWords = new ArrayList<>();
        List<String> arrayConstruct = new ArrayList<>();

        if(url.equals("/") || url.isEmpty()){
            /* Add directly a word in a good format if the url is juste '/' */
            UpOneCaseToTheWords.add("Home");
        } else {
            /* For each word, take the first char and put upperCase this.  */
            for(int i=1; i< words.length; i++){

                char[] word = words[i].toCharArray();
                char upCase = Character.toUpperCase(word[0]);
                StringBuilder loadWord = new StringBuilder().append(upCase);

                for(int x = 1; x < word.length; x++){
                    loadWord.append(word[x]);
                }

                String finalWord = loadWord.toString();
                UpOneCaseToTheWords.add(finalWord);
            }
        }

        // build an array with one word and one slash, if url isn't Home, add "Home /" to path
        if(!UpOneCaseToTheWords.get(0).equalsIgnoreCase("home")){
            arrayConstruct.add("Home");
            arrayConstruct.add("/");
        }

        /* Add / between each word */
        for(String string : UpOneCaseToTheWords){
            arrayConstruct.add(string);
            arrayConstruct.add("/");
        }

        // Delete the last slash
        int index = arrayConstruct.size() - 1;
        arrayConstruct.remove(index);

       return arrayConstruct;
    }

    public RedirectView accesDenied(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userMail = (String)session.getAttribute("mail");
        logger.info("User {} attempt access the forbidden page, but the authority is don't right for her role", userMail);
        return new RedirectView("/accessDenied");
    }

    @Override
    public RedirectView sendContactMessage(HttpServletRequest request) {
        /* Simulated to send a contact message */
        return new RedirectView("/contact?status=sentMessage");
    }
}
