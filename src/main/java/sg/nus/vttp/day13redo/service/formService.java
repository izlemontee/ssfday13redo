package sg.nus.vttp.day13redo.service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;

import jakarta.json.JsonArray;
import sg.nus.vttp.day13redo.model.Contact;
import sg.nus.vttp.day13redo.repo.contactRepo;

@Service
public class formService {

    @Autowired
    contactRepo repo;

    private ObjectMapper objectMapper = new ObjectMapper();

    public String generateID(Contact contact){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<8;i++){
            Integer number = random.nextInt(16);
            String hexChar = Integer.toHexString(number);
            sb.append(hexChar);
        }
        String contactId = sb.toString();
        contact.setId(contactId);
        System.out.println(contactId);
        return contactId;
    }
    
    public void addtoRepo(Contact contact)throws Exception{
        repo.addContact(contact);
    }

    public Contact retrieveContact(String id) throws Exception{
        objectMapper.registerModule(new JavaTimeModule());
        Object obj = repo.getContact(id);
        System.out.println("Object obj = repo.getContact(id);");
        String objString = obj.toString();
        Contact contact = objectMapper.readValue(objString, Contact.class);
        //Contact contact = (Contact)obj;
        System.out.println(contact.getEmail());
        System.out.println(contact.getId());
        System.out.println(contact.getName());
        System.out.println(contact.getPhoneNo());
        System.out.println(contact.getDob());
        return contact;
    }

    public Set<Contact> getContacts(){
        Set<String> keys = repo.getContactList();
        System.out.println(keys);
        Set<Contact> contactsSet = new HashSet<>();
        for(String s:keys){
            Contact c = (Contact)repo.getContact(s);
            contactsSet.add(c);
        }
        return contactsSet;
    }
}
