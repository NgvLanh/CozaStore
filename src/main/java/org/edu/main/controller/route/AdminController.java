package org.edu.main.controller.route;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping({"", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("title", "Dashboard");
        model.addAttribute("page", "admin/dashboard");
        return "layout/admin-layout";
    }

}