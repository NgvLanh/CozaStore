package org.edu.main.controller.route;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "client/home");
        return "layout/client-layout";
    }

    @GetMapping("/product")
    public String shop(Model model) {
        model.addAttribute("title", "Shop");
        model.addAttribute("page", "client/product");
        return "layout/client-layout";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        model.addAttribute("title", "Cart");
        model.addAttribute("page", "client/cart");
        return "layout/client-layout";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("title", "Blogs");
        model.addAttribute("page", "client/blog");
        return "layout/client-layout";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About Us");
        model.addAttribute("page", "client/about");
        return "layout/client-layout";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contact");
        model.addAttribute("page", "client/contact");
        return "layout/client-layout";
    }

    @GetMapping("/product-details")
    public String productDetails(Model model) {
        model.addAttribute("title", "Blogs");
        model.addAttribute("page", "client/product_details");
        return "layout/client-layout";
    }

    @GetMapping("/blog-details")
    public String blogDetails(Model model) {
        model.addAttribute("title", "Blog Details");
        model.addAttribute("page", "client/blog_details");
        return "layout/client-layout";
    }

    @GetMapping("/profile")
    public String account(Model model) {
        model.addAttribute("title", "Profile");
        model.addAttribute("page", "client/profile");
        return "layout/client-layout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("page", "client/login");
        return "layout/client-layout";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        model.addAttribute("page", "client/register");
        return "layout/client-layout";
    }
}
