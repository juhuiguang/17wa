package com.alienlab.wa17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 橘 on 2017/7/15.
 */
@Controller
public class PageController {
    @GetMapping("/custable/{cusid}")
    public String custable(@PathVariable int cusid, Model model){
        model.addAttribute("hello","from TemplateController.helloHtml");
        return "customTable";
    }

    @GetMapping("/includetable/{account}/{productid}")
    public String includetable(@PathVariable int account,@PathVariable Long productid, Model model){
        model.addAttribute("hello","from TemplateController.helloHtml");
        return "includeTable";
    }
}
