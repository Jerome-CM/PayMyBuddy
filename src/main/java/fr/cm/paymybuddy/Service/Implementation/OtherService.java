package fr.cm.paymybuddy.Service.Implementation;

import fr.cm.paymybuddy.Service.Interface.OtherServiceInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class OtherService implements OtherServiceInt {

    private static final Logger logger = LogManager.getLogger(OtherService.class);

    public List<String> accessPath(String url){


        // Split the words and add upperCase for the first char of each word
        String[] words = url.split("/");
        List<String> UpOneCaseToTheWords = new ArrayList<>();
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

        // build an array with one word and one slash, if url isn't Home, add "Home /" to path
        List<String> arrayConstruct = new ArrayList<>();
        if(!UpOneCaseToTheWords.get(0).equalsIgnoreCase("home")){
            arrayConstruct.add("Home");
            arrayConstruct.add("/");
        }

        for(String string : UpOneCaseToTheWords){
            arrayConstruct.add(string);
            arrayConstruct.add("/");
        }

        // Delete the last slash
        int index = arrayConstruct.size() - 1;
        arrayConstruct.remove(index);

       return arrayConstruct;
    }

    @Override
    public boolean sendContactMessage(HttpServletRequest request) {
        return false;
    }
}
