package ukim.finki.wpprojectinternships.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.service.AdvertisementService;
import ukim.finki.wpprojectinternships.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AdvertisementService advertisementService;

    public StudentController(StudentService studentService, AdvertisementService advertisementService) {
        this.studentService = studentService;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
       // model.addAttribute("student", new Student());
        return "student/student-register";
    }

    @PostMapping("/register")
    public String registerStudent(@RequestParam String name,
                                  @RequestParam String surname,
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  @RequestParam String index,
                                  @RequestParam(required = false) MultipartFile image,
                                  Model model) {
        Student student = studentService.registerStudent(name, surname, email, phone, index, image);
        model.addAttribute("student", student);
        return "/index";
    }

    //ovoj
    @GetMapping("/advertisements")
    public String getAllAdvertisements(Model model, HttpSession session) {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();
        Student student = (Student) session.getAttribute("currentStudent");
        model.addAttribute("advertisements", advertisements);
        model.addAttribute("student",student);
        return "student/advertisement-list"; // Име на шаблонот
    }

    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model) {
        Student student = studentService.findStudentById(id);
        model.addAttribute("student", student);
        return "student-details";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam String index,
                                @RequestParam(required = false) MultipartFile image,
                                Model model) {
        Student updatedStudent = studentService.updateStudent(id, name, surname, email, phone, index, image);
        model.addAttribute("student", updatedStudent);
        return "student-updateSuccess";
    }

    @GetMapping
    public String getAllStudents(Model model) {
        List<Student> students = studentService.findAllStudents();
        model.addAttribute("students", students);
        return "student-list";
    }
}