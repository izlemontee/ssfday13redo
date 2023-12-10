package sg.nus.vttp.day13redo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.nus.vttp.day13redo.model.Contact;
import sg.nus.vttp.day13redo.service.formService;

@Controller
@RequestMapping(path = "/form")
public class formController {


    @Autowired 
    formService formSvc;

    HttpSession session;

    @GetMapping(path = "/contactform")
    public String getForm(Model model){

        Contact contact = new Contact();

        model.addAttribute("contact", contact);

        return "form";
    }

    @PostMapping(path = "/contact")
    // requestbody does not work this round because it's trying to check the model for errors
    //public String checkForm(@Valid @RequestBody MultiValueMap<String, String> form, BindingResult result){
    public ModelAndView checkForm(@Valid @ModelAttribute(name = "contact") Contact form, BindingResult result)
    throws Exception{
        if(result.hasErrors()){
            ModelAndView mav = new ModelAndView("form");
            mav.setStatus(HttpStatusCode.valueOf(300));
            return mav;
        }
        else{
            String contactId = formSvc.generateID(form);
            formSvc.addtoRepo(form);
            ModelAndView mav = new ModelAndView("ok");
            mav.setStatus(HttpStatusCode.valueOf(201));
            return mav;
        }
    }


    @GetMapping(path = "/contact/{id}")
    public ModelAndView getContact(@PathVariable("id") String id, Model model, HttpSession session) throws Exception{
        ModelAndView mav = new ModelAndView();
        Contact contact = formSvc.retrieveContact(id);
        if(contact != null){
            model.addAttribute("contactdisplay",contact);
            mav.setStatus(HttpStatusCode.valueOf(200));
            mav.setViewName("contactdisplay");
            return mav;
        }
        else{
            mav.setViewName("error");
            mav.setStatus(HttpStatusCode.valueOf(404));
            return mav;
        }
    }

    @GetMapping(path = "/allcontacts")
    public String getAllContacts(Model model, HttpSession session){
        Set<Contact> contacts = formSvc.getContacts();
        model.addAttribute("sessionid", session.getAttribute("sessionid"));
        model.addAttribute("contactslist", contacts);
        System.out.println("session id:"+session.getAttribute("sessionid"));
        return "contacts";
    }

    @GetMapping(path = "/login")
    public String login(Model model){
        return "index";
    }

    @PostMapping(path = "/login")
    public String checkUser(@RequestBody String loginid, HttpSession session, Model model){
        session.setAttribute("sessionid", loginid);
        model.addAttribute("sessionid", loginid);

        System.out.println("session id in the checkuser method:"+session.getAttribute("sessionid"));
        return "redirect:/form/allcontacts";
    }
    
}
