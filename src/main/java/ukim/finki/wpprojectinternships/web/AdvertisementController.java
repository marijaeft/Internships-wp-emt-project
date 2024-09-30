package ukim.finki.wpprojectinternships.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.AdvertisementType;
import ukim.finki.wpprojectinternships.service.AdvertisementService;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/student/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @GetMapping
    public String getAllAdvertisements(Model model) {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();
        model.addAttribute("advertisements", advertisements);
        return "student/advertisement-list";
    }

    @GetMapping("/{id}")
    public String getAdvertisementById(@PathVariable Long id, Model model) {
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        model.addAttribute("advertisement", advertisement);
        return "student/advertisements-details";
    }

    @GetMapping("/create")
   // @PreAuthorize("hasRole('COMPANY')")
    public String showCreateAdvertisementForm() {
        return "company/add_advertisement";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('COMPANY')")
    public String createAdvertisement(@RequestParam String description,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Timestamp dateExpired,
                                      @RequestParam String type,
                                      @RequestParam Long company,
                                      Model model) {
        Advertisement advertisement = advertisementService.createAdvertisement(description, dateExpired, type, company);
        model.addAttribute("advertisement", advertisement);
        return "advertisements-creationSuccess";
    }

    @GetMapping("/edit/{id}")
    //@PreAuthorize("hasRole('COMPANY')")
    public String showEditAdvertisementForm(@PathVariable Long id, Model model) {
        Advertisement advertisement = advertisementService.findAdvertisementById(id);
        model.addAttribute("advertisement", advertisement);
        return "company/advertisements-edit";
    }

    @PostMapping("/adv/edit/{id}")
   // @PreAuthorize("hasRole('COMPANY')")
    public String updateAdvertisement(@PathVariable Long id,
                                      @RequestParam String description,
                                      @RequestParam String dateExpired,
                                      @RequestParam String type,
                                      @RequestParam(required = false) Boolean active,
                                      Model model) {
        LocalDateTime dateTime = LocalDateTime.parse(dateExpired, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Timestamp timestamp1 = Timestamp.valueOf(dateTime);
        if(active == null)
        {
            active=false;
        }
        Advertisement updatedAdvertisement = advertisementService.updateAdvertisement(id, description, timestamp1, type,active);
        model.addAttribute("advertisement", updatedAdvertisement);
        return "redirect:/student/advertisement";
    }

    @PostMapping("/delete/{id}")
    // @PreAuthorize("hasRole('COMPANY')")
    public String deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
        return "redirect:/student/advertisement";
    }
}
