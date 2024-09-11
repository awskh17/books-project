package firsProject.booksProject.Controllers;

import firsProject.booksProject.Dtos.BookDto;
import firsProject.booksProject.Dtos.PublisherDto;
import firsProject.booksProject.Services.PublisherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/front/api/publishers")
public class FrontPublisherController {
    PublisherService publisherService;

    public FrontPublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }



    @GetMapping("/get/{id}")
    String getPublisher(@PathVariable int id, Model model) {
        model.addAttribute("publisher", publisherService.findPublisherById(id));
        return "publisher/publisherById";
    }
    @PostMapping("/save")
    public String saveBook(@ModelAttribute("publisher") PublisherDto publisherDto) {
        publisherService.addPublisher(publisherDto);
        return "redirect:/front/api/books/add";
    }

    @GetMapping("/add")
    public String addBook(Model model) {
        PublisherDto publisherDto = new PublisherDto();
        model.addAttribute("publisher", publisherDto);
        return "publisher/AddPublisher";
    }
}
