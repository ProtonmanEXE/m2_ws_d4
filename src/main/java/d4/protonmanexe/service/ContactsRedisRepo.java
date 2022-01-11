package d4.protonmanexe.service;

import static d4.protonmanexe.Constants.*;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import d4.protonmanexe.controller.Contact;

@Repository
public class ContactsRedisRepo {

    @Autowired
    private RedisTemplate <String, Object> template;

    private final static Logger logging = LoggerFactory.getLogger(ContactsRedisRepo.class); // instantiate logger

    public synchronized void saveContact (Contact c) {   
        // object initialisation
        Random r = new Random();
        StringBuffer sb = new StringBuffer(); 

        // randomly generate an eight character long hex string
        while(sb.length() < ID_LENGTH) { 
            sb.append(Integer.toHexString(r.nextInt()));
        }
        String id = sb.toString().substring(0, ID_LENGTH);
        logging.info("id > " +id);

        // write to Redis
        template.opsForValue().set(id, c);
    }

    public Contact getContactById (String id) {
        Contact result = (Contact) template.opsForValue().get(id);
            logging.info(id +">>> getContactById email >>> " + result.getEmail());
        return result;        
    }
    
}