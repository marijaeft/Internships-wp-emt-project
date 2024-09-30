package ukim.finki.wpprojectinternships.web;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ukim.finki.wpprojectinternships.model.Advertisement;
import ukim.finki.wpprojectinternships.model.Application;
import ukim.finki.wpprojectinternships.model.Student;
import ukim.finki.wpprojectinternships.service.AdvertisementService;
import ukim.finki.wpprojectinternships.service.ApplicationService;
import ukim.finki.wpprojectinternships.service.StudentService;
import jakarta.servlet.http.HttpSession;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final AdvertisementService advertisementService;
    private final StudentService studentService;

    public ApplicationController(ApplicationService applicationService, AdvertisementService advertisementService, StudentService studentService) {
        this.applicationService = applicationService;
        this.advertisementService = advertisementService;
        this.studentService = studentService;
    }

    @GetMapping("/student/{studentId}")
    public String getAllApplicationsByStudentId(@PathVariable Long studentId, Model model) {
        List<Application> applications = applicationService.findAllApplicationsThatTheStudentAppliedTo(studentId);
        model.addAttribute("applications", applications);
        return "student/studentApplications";
    }

    @GetMapping("/form/{advertisementId}")
    public String showApplicationForm(@PathVariable Long advertisementId, Model model, HttpSession session) {
        Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
        Student student = (Student) session.getAttribute("currentStudent");
        model.addAttribute("advertisement", advertisement);
        model.addAttribute("student", student);
        return "student/application-form";
    }

    @PostMapping("/save")
    public String saveApplication(@RequestParam("advertisementIdStr") String advertisementIdStr,
                                  @RequestParam("studentIdStr") String studentIdStr,
                                  @RequestParam("cv") MultipartFile cvFile,
                                  Model model) {
        try {
            Long advertisementId = Long.valueOf(advertisementIdStr.trim());
            // Validate and convert studentIdStr to Long
            Long studentId = Long.valueOf(studentIdStr.trim());

            // Check if advertisement and student exist
            Advertisement advertisement = advertisementService.findAdvertisementById(advertisementId);
            Student student = studentService.findStudentById(studentId);

            // If advertisement or student is not found, return error page
            if (advertisement == null || student == null) {
                model.addAttribute("error", "Advertisement or Student not found");
                return "error";
            }

            // Create application using service
            applicationService.createApplication(studentId, advertisementId, cvFile);

            // Redirect to student's advertisement list
            return "redirect:/applications/student/" + studentId;
        } catch (NumberFormatException e) {
            // Handle case where advertisementIdStr or studentIdStr cannot be parsed as Long
            model.addAttribute("error", "Invalid Advertisement or Student ID format");
            return "error";
        } catch (Exception e) {
            // Handle other exceptions, such as issues with CV file processing
            model.addAttribute("error", "Error processing CV file");
            return "error";
        }
    }

    @GetMapping("/downloadCV/{applicationId}")
    public ResponseEntity<byte[]> downloadCV(@PathVariable Long applicationId) {
        Application application = applicationService.findApplicationById(applicationId);

        if (application == null || application.getCV() == null) {
            // Handle case where application or CV data is not found
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); // Set appropriate content type

        // Optionally, you can set content disposition header to prompt download
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename("cv_" + application.getId() + ".pdf").build());

        return new ResponseEntity<>(application.getCV(), headers, HttpStatus.OK);
    }

    @GetMapping("/advertisement/{advertisementId}")
    public String getAllApplicationsByAdvertisementId(@PathVariable Long advertisementId, Model model) {
        List<Application> applications = applicationService.findAllApplicationsToACertainAdvertisement(advertisementId);
        model.addAttribute("applications", applications);
        return "company/applications-advertisementApplications";
    }

    @GetMapping("/{id}")
    public String getApplicationById(@PathVariable Long id, Model model) {
        Application application = applicationService.findApplicationById(id);
        model.addAttribute("application", application);
        return "applications-details";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('STUDENT')")
    public String showCreateApplicationForm() {
        return "applications-create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('STUDENT')")
    public String createApplication(@RequestParam Long studentId,
                                    @RequestParam Long advertisementId,
                                    @RequestParam(required = false) MultipartFile CV,
                                    Model model) {
        Application application = applicationService.createApplication(studentId, advertisementId, CV);
        model.addAttribute("application", application);
        return "applications-creationSuccess";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('STUDENT') or hasRole('COMPANY')")
    public String showEditApplicationForm(@PathVariable Long id, Model model) {
        Application application = applicationService.findApplicationById(id);
        model.addAttribute("application", application);
        return "applications-edit";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('COMPANY')")
    public String updateApplication(@PathVariable Long id,
                                    @RequestParam Long studentId,
                                    @RequestParam Long advertisementId,
                                    Model model) {
        Application updatedApplication = applicationService.updateApplication(id, studentId, advertisementId);
        model.addAttribute("application", updatedApplication);
        return "applications-updateSuccess";
    }

    @GetMapping("/generateCv")
    public String getFormForGeneratingCv() {
        return "student/generateCv";
    }

    @GetMapping("/adv")
    public String getAllAdvertisements(Model model) {
        List<Advertisement> advertisements = advertisementService.findAllAdvertisements();
        model.addAttribute("advertisements", advertisements);
        return "student/advertisement-list";
    }

    /// THIS DOWN WORKS

//    @PostMapping("/cv/generate")
//    public ResponseEntity<byte[]> generateCV(
//            @RequestParam String name,
//            @RequestParam String surname,
//            @RequestParam String email,
//            @RequestParam String phone,
//            @RequestParam String aboutMe,
//            @RequestParam String address,
//            @RequestParam String linkedin,
//            @RequestParam String github,
//            @RequestParam List<String> education,
//            @RequestParam List<String> experience,
//            @RequestParam List<String> softSkills,
//            @RequestParam List<String> technicalSkills,
//            @RequestParam List<String> projects,
//            @RequestParam(required = false) byte[] profileImage // Optional profile image
//    ) {
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            Document document = new Document();
//            PdfWriter.getInstance(document, outputStream);
//            document.open();
//
//            // Set font styles
//            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
//            Font sectionHeaderFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLUE);
//            Font textFont = new Font(Font.FontFamily.HELVETICA, 10);
//            Font bulletFont = new Font(Font.FontFamily.HELVETICA, 10);
//
//            // Add Name as Header
//            Paragraph nameHeader = new Paragraph(name + " " + surname, headerFont);
//            nameHeader.setAlignment(Element.ALIGN_CENTER);
//            nameHeader.setSpacingAfter(20);
//            document.add(nameHeader);
//
//            // Add Profile Image (if available)
//            if (profileImage != null && profileImage.length > 0) {
//                try {
//                    Image img = Image.getInstance(profileImage);
//                    img.scaleToFit(100, 100);
//                    img.setAlignment(Element.ALIGN_RIGHT);
//                    document.add(img);
//                } catch (Exception e) {
//                    System.err.println("Error adding profile image: " + e.getMessage());
//                }
//            }
//
//            // Add Contact Information
//            document.add(new Paragraph("Email: " + email, textFont));
//            document.add(new Paragraph("Phone: " + phone, textFont));
//            document.add(new Paragraph("Address: " + address, textFont));
//            if (!linkedin.isEmpty()) {
//                document.add(new Paragraph("LinkedIn: " + linkedin, textFont));
//            }
//            if (!github.isEmpty()) {
//                document.add(new Paragraph("GitHub: " + github, textFont));
//            }
//            document.add(Chunk.NEWLINE);
//
//            // About Me Section
//            if (!aboutMe.isEmpty()) {
//                document.add(new Paragraph("About Me", sectionHeaderFont));
//                document.add(new Paragraph(aboutMe, textFont));
//                document.add(Chunk.NEWLINE);
//            }
//
//            // Education Section
//            document.add(new Paragraph("Education", sectionHeaderFont));
//            for (String edu : education) {
//                document.add(new Paragraph("• " + edu, bulletFont));
//            }
//            document.add(Chunk.NEWLINE);
//
//            // Experience Section
//            document.add(new Paragraph("Work Experience", sectionHeaderFont));
//            for (String exp : experience) {
//                document.add(new Paragraph("• " + exp, bulletFont));
//            }
//            document.add(Chunk.NEWLINE);
//
//            // Soft Skills Section
//            document.add(new Paragraph("Soft Skills", sectionHeaderFont));
//            for (String skill : softSkills) {
//                document.add(new Paragraph("• " + skill, bulletFont));
//            }
//            document.add(Chunk.NEWLINE);
//
//            // Technical Skills Section
//            document.add(new Paragraph("Technical Skills", sectionHeaderFont));
//            for (String techSkill : technicalSkills) {
//                document.add(new Paragraph("• " + techSkill, bulletFont));
//            }
//            document.add(Chunk.NEWLINE);
//
//            // Projects Section
//            document.add(new Paragraph("Projects", sectionHeaderFont));
//            for (String project : projects) {
//                document.add(new Paragraph("• " + project, bulletFont));
//            }
//
//            document.close();
//
//            // Prepare response headers and content
//            byte[] pdfBytes = outputStream.toByteArray();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "CV_" + name + "_" + surname + ".pdf");
//
//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/cv/generate")
    public ResponseEntity<byte[]> generateCV(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String aboutMe,
            @RequestParam String address,
            @RequestParam String linkedin,
            @RequestParam String github,
            @RequestParam List<String> education,
            @RequestParam List<String> experience,
            @RequestParam List<String> softSkills,
            @RequestParam List<String> technicalSkills,
            @RequestParam List<String> projects,
            @RequestParam(required = false) MultipartFile profileImage
    ) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Set font styles
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(255, 255, 255)); // White
            Font sectionHeaderFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(0, 102, 204)); // Dark Blue
            Font textFont = new Font(Font.FontFamily.HELVETICA, 10); // Reduced font size
            Font bulletFont = new Font(Font.FontFamily.HELVETICA, 10); // Reduced font size
            Font whiteTextFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, new BaseColor(255, 255, 255)); // White text for contact info

            // Create a table for the header
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.setSpacingAfter(5);


            // Left Column for Profile Image
            if (profileImage != null && !profileImage.isEmpty()) {
                try {
                    Image img = Image.getInstance(profileImage.getBytes());
                    img.scaleToFit(100, 100);
                    PdfPCell imageCell = new PdfPCell(img);
                    imageCell.setBorder(PdfPCell.NO_BORDER);
                    imageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    imageCell.setPadding(5);
                    headerTable.addCell(imageCell);
                } catch (Exception e) {
                    System.err.println("Error adding profile image: " + e.getMessage());
                }
            } else {
                // If no image, add an empty cell
                headerTable.addCell(new PdfPCell(new Phrase(" ", textFont)));
            }

            // Right Column for Contact Information
            PdfPCell contactCell = new PdfPCell();
            contactCell.setBackgroundColor(new BaseColor(0, 102, 204)); // Blue background
            contactCell.setPadding(6); // Reduced padding for a smaller cell
            contactCell.addElement(new Paragraph("Email: " + email, whiteTextFont));
            contactCell.addElement(new Paragraph("Phone: " + phone, whiteTextFont));
            contactCell.addElement(new Paragraph("Address: " + address, whiteTextFont));
            if (!linkedin.isEmpty()) {
                contactCell.addElement(new Paragraph("LinkedIn: " + linkedin, whiteTextFont));
            }
            if (!github.isEmpty()) {
                contactCell.addElement(new Paragraph("GitHub: " + github, whiteTextFont));
            }
            contactCell.setBorder(PdfPCell.NO_BORDER);
            contactCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerTable.addCell(contactCell);

            // Add the header table to the document
            document.add(headerTable);

            // Add Name as Header
            Paragraph nameHeader = new Paragraph(name + " " + surname, headerFont);
            nameHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(nameHeader);


            // About Me Section
            if (!aboutMe.isEmpty()) {
                document.add(new Paragraph("About Me", sectionHeaderFont));
                document.add(new Paragraph(aboutMe, textFont));
                document.add(Chunk.NEWLINE);
            }

            // Education Section
            document.add(new Paragraph("Education", sectionHeaderFont));
            for (String edu : education) {
                document.add(new Paragraph("• " + edu, bulletFont));
            }
            document.add(Chunk.NEWLINE);

            // Experience Section
            document.add(new Paragraph("Work Experience", sectionHeaderFont));
            for (String exp : experience) {
                document.add(new Paragraph("• " + exp, bulletFont));
            }
            document.add(Chunk.NEWLINE);

            // Soft Skills Section
            document.add(new Paragraph("Soft Skills", sectionHeaderFont));
            for (String skill : softSkills) {
                document.add(new Paragraph("• " + skill, bulletFont));
            }
            document.add(Chunk.NEWLINE);

            // Technical Skills Section
            document.add(new Paragraph("Technical Skills", sectionHeaderFont));
            for (String techSkill : technicalSkills) {
                document.add(new Paragraph("• " + techSkill, bulletFont));
            }
            document.add(Chunk.NEWLINE);

            // Projects Section
            document.add(new Paragraph("Projects", sectionHeaderFont));
            for (String project : projects) {
                document.add(new Paragraph("• " + project, bulletFont));
            }

            document.close();

            // Prepare response headers and content
            byte[] pdfBytes = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "CV_" + name + "_" + surname + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}