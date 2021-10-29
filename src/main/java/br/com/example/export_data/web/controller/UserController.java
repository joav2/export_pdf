package br.com.example.export_data.web.controller;

import br.com.example.export_data.domain.User;
import br.com.example.export_data.web.pdf.UserPDFExporter;
import br.com.example.export_data.service.UserService;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @PostMapping("/")
    public String saveUser(User user) {
        service.save(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/load", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public void getImage(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        List<User> listUsers = service.listAll();

        UserPDFExporter exporter = new UserPDFExporter(listUsers);

        exporter.export(response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = service.listAll();

        UserPDFExporter exporter = new UserPDFExporter(listUsers);
        exporter.export(response);

    }
}
