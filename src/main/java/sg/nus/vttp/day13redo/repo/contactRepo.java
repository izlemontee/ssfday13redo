package sg.nus.vttp.day13redo.repo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import sg.nus.vttp.day13redo.Utils;
import sg.nus.vttp.day13redo.model.Contact;

@Repository
public class contactRepo {

    @Autowired @Qualifier(Utils.REDIS)//ask spring to look for something, look for myredis bean
	private RedisTemplate<String,Object> template;
    private ObjectMapper objectMapper = new ObjectMapper();
    

    List<Contact> contactList = new ArrayList<>();
    //private final String path = "static/";
    private final String path = "src/main/resources/contacts";
    private final String mainKey = "contacts";
    
    public void addContact(Contact contact)throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        contactList.add(contact);
        System.out.println("called contactList.add");
        addToRedis(contact.getId(),contact);
        System.out.println("called addToRedis");
    }

    public void addToRedis(String id, Contact contact)throws Exception{

        objectMapper.registerModule(new JavaTimeModule());
        String contactJson = objectMapper.writeValueAsString(contact);
        System.out.println("called this method");
        HashOperations<String, String, String> HO = template.opsForHash();
        System.out.println("Got hashoperations");
        HO.put(mainKey,id,contactJson);
        System.out.println("Contact added to redis");
    }

    public Object getContact(String id){
        HashOperations<String, String, Contact> HO = template.opsForHash();
        Object obj = HO.get(mainKey, id);
        System.out.println(obj);
        return obj;
    }

    public Set<String> getContactList(){
        HashOperations<String, String, Contact> HO = template.opsForHash();
        Set<String> keys = HO.keys(mainKey);
        return keys;

    }

    public void createContactFile(Contact contact)throws Exception{
        File file = new File(path+"/"+contact.getId()+".txt");
            OutputStream osw = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(osw);
            pw.write(contact.toString());
            pw.write("\n");
            pw.flush();
            pw.close();

        
    }
}
