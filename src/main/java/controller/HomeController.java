package controller;

import kz.bitlab.springboot2.model.ApplicationRequest;
import kz.bitlab.springboot2.repository.AddRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AddRequestRepository addRequestRepository;


    @GetMapping(value = "/home")
    public String getHomePage(Model model){
        List<ApplicationRequest> applicationRequestList = addRequestRepository.findAll();
        model.addAttribute("reqList", applicationRequestList);
        return "home";
    }

    @GetMapping(value = "/addReq")
    public String addRequestPage(){
        return "addReq";
    }

    @PostMapping(value = "/addNewReq")
    public String addNewReq(@RequestParam(value = "userName") String userName,
                            @RequestParam(value = "courseName") String courseName,
                            @RequestParam(value = "phoneNumber") String phoneNumber,
                            @RequestParam(value = "comment") String comment,
                            @RequestParam(value = "handled") boolean handled){
        ApplicationRequest applicationRequest = new ApplicationRequest();
        applicationRequest.setCommentary(comment);
        applicationRequest.setPhone(phoneNumber);
        applicationRequest.setUserName(userName);
        applicationRequest.setCourseName(courseName);
        applicationRequest.setHandled(handled);

        addRequestRepository.save(applicationRequest);

        return "redirect:/home";
    }

    @GetMapping(value = "/details/{id}")
    public String detailsPage(@PathVariable(value = "id") Long id,
                              Model model){
        ApplicationRequest request = addRequestRepository.findById(id).orElseThrow();
        model.addAttribute("request", request);
        return "details";
    }

    @GetMapping(value = "/deleteReq/{id}")
    public String deleteRequest(@PathVariable(value = "id") Long id){
        addRequestRepository.deleteById(id);
        return "redirect:/home";
    }

    @GetMapping(value = "/makeReq/{id}")
    public String makeRequest(@PathVariable(value = "id") Long id){
        ApplicationRequest request = addRequestRepository.findById(id).orElseThrow();
        request.setHandled(true);
        addRequestRepository.save(request);
        return "redirect:/home";
    }

    @GetMapping(value = "/doneReqs")
    public String doneRequests(Model model){
        List<ApplicationRequest> requests = addRequestRepository.findAll();
        List<ApplicationRequest> newreqs = new ArrayList<>();
        for(ApplicationRequest req : requests){
            if(req.isHandled()==true){
                newreqs.add(req);
            }
        }
        model.addAttribute("reqs" , newreqs);

        return "done";
    }

    @GetMapping(value = "/newReqs")
    public String newRequests(Model model){
        List<ApplicationRequest> requests = addRequestRepository.findAll();
        List<ApplicationRequest> newreqs = new ArrayList<>();
        for(ApplicationRequest req : requests){
            if(req.isHandled()==false){
                newreqs.add(req);
            }
        }
        model.addAttribute("reqs" , newreqs);

        return "newReqs";
    }
}
