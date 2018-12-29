package com.gokhanozg.hardwarels.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Gokhan Ozgozen on 29-Dec-18.
 */
@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String showHome(HttpServletRequest request, HttpServletResponse response, Model model) {
        return "home";
    }
}
