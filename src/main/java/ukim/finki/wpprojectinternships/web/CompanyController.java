package ukim.finki.wpprojectinternships.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.config.FacultyUserDetails;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.AdvertisementType;
import ukim.finki.wpprojectinternships.model.Company;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.service.AdvertisementService;
import ukim.finki.wpprojectinternships.service.CompanyService;
import ukim.finki.wpprojectinternships.service.StudentService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final StudentService studentService;
    private final AdvertisementService advertisementService;

    public CompanyController(CompanyService companyService, AdvertisementService advertisementService,StudentService studentService) {
        this.companyService = companyService;
        this.advertisementService = advertisementService;
        this.studentService=studentService;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
       // model.addAttribute("company", new Company());
        return "company/company-register";
    }

    @PostMapping("/register")
    public String registerCompany(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String description,
            @RequestParam String address,
            @RequestParam(required = false) MultipartFile image) {


        byte[] imageBytes = null;
        try {
            if (!image.isEmpty()) {
                imageBytes = image.getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }


        //Company newCompany = new Company(name, email, phone, description, address, imageBytes);
        companyService.registerCompany(name, email, phone, description, address, image);

        return "/index";
    }

    @GetMapping("/profile")
    public String getCompanyProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FacultyUserDetails userDetails = (FacultyUserDetails) authentication.getPrincipal();
        Company company = userDetails.getCompany();
        model.addAttribute("company", company);
        return "company/profile"; // Thymeleaf template name
    }



    @GetMapping("/{id}")
    public String getCompanyById(@PathVariable Long id, Model model) {
        Company company = companyService.findCompanyById(id);
        model.addAttribute("company", company);
        return "company-details";
    }

    @GetMapping("/listAdvertisements/{id}")
    public String listAllAdvertisementsByCompanyId(@PathVariable Long id, Model model) {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisementsByCompanyId(id);
        model.addAttribute("advertisements", advertisements);
        return "student/advertisement-list";
    }



        @GetMapping
    public String getAllCompanies(Model model) {
        List<Company> companies = companyService.findAllCompanies();
        model.addAttribute("companies", companies);
        return "company-list";
    }

    @GetMapping("/edit/{id}")
//    @PreAuthorize("hasRole('COMPANY')")
    public String showEditAdvertisementForm(@PathVariable Long id, Model model) {
        Company company = companyService.findCompanyById(id);
        model.addAttribute("company", company);
        return "company/edit";
    }

    @PostMapping("/update")
    public String updateCompanyProfile(
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String description,
            @RequestParam String address,
            @RequestParam MultipartFile image) {

        companyService.updateCompany(id, name, email, phone, description, address, image);
        return "redirect:/company/profile"; // Redirect to profile page after update
    }

    @GetMapping("/add-advertisement")
    public String addAdvertisementForm(Model model) {
        model.addAttribute("advertisement", new Advertisement());
        return "company/add_advertisement";
    }

    @PostMapping("/add-advertisement")
    public String addAdvertisement(@RequestParam String description,
                                   @RequestParam String dateExpired,
                                   @RequestParam AdvertisementType type,
                                   HttpSession session,
                                   Model model) {
        LocalDateTime dateTime = LocalDateTime.parse(dateExpired, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        Long id = Long.parseLong(session.getAttribute("companyId").toString());
        companyService.addAdvertisement(id, description, timestamp, type);
        List<Advertisement> advertisements=advertisementService.findAllAdvertisementsByCompanyId(id);
        model.addAttribute("advertisements", advertisements);

        return "student/advertisement-list";
    }
}
