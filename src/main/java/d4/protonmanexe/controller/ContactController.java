package d4.protonmanexe.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import d4.protonmanexe.service.ContactsRedisRepo;

@Controller
public class ContactController {

    @Autowired
    ContactsRedisRepo repo;

    private final static Logger logging = LoggerFactory.getLogger(ContactsRedisRepo.class); // instantiate logger

    // GET method to return addressbook.html when requested
    @GetMapping("/")
    public String showForm (Model model) {
        model.addAttribute("addressbookobj", new Contact()); // link object "Contact" to html
        return "addressbook"; 
    }

    // GET method to find contact by id
    @GetMapping("/contact/{id}")
    public String getContact (Model model, @PathVariable(value="id") String id) {
        logging.info("contactId > " + id);
        Contact c = repo.getContactById(id);
        logging.info("getEmail > " + c.getEmail());
        try {
            model.addAttribute("yourcontact", c);
            return "addressbookresult"; 
        } catch (Exception e) {
            model.addAttribute("msg", "404 - Not found");
            return "contactstatus"; 
        }
    }

    // POST method to save contact details
    @PostMapping("/contact")
    public String getForm (@ModelAttribute Contact c, Model model, HttpServletResponse response) {
        logging.info("Name > " + c.getName());
        logging.info("Email > " + c.getEmail());
        logging.info("Phone Number > " + c.getPhoneNo());
        Contact persistToRedisCtc = new Contact(
            c.getName(),
            c.getEmail(),
            c.getPhoneNo()
        );
        repo.saveContact(persistToRedisCtc);
        return "contactstatus";
    }

}
