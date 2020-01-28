package com.waes.assignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Provides redirecting welcome page to swagger-ui page
 *
 * @developer -- ilkercolakoglu
 */
@Controller
@ApiIgnore
public class HomeController
{

    @RequestMapping("/")
    public String home()
    {
        return "redirect:swagger-ui.html";
    }

}
