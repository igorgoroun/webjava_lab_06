package lab.web.lab_05.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/")
    public String home() {
//        @RequestParam String color,
//                       @RequestParam String username,
//                       Model model
//        model.addAttribute("username", username);
//        model.addAttribute("color", color);
//        http://localhost:8080/home?color=green&username=Snaka
        return "home";
    }

//    @GetMapping("/home/{color}")
//    public String homec(@PathVariable String color,
//                       @RequestParam String username,
//                       Model model) {
//        model.addAttribute("username", username);
//        model.addAttribute("color", color);
////        http://localhost:8080/home/green?username=Snaka
//        return "home";
//    }

}
