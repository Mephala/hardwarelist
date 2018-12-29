package com.gokhanozg.hardwarels.home;

import com.gokhanozg.hardwarels.cpu.CPU;
import com.gokhanozg.hardwarels.cpu.CpuParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Gokhan Ozgozen on 29-Dec-18.
 */
@Controller
public class HomeController {

    private final CpuParser cpuParser;

    public HomeController(CpuParser cpuParser) {
        this.cpuParser = cpuParser;
    }

    @GetMapping(value = "/")
    public String showHome(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CPU> cpuList = cpuParser.getAllCpu();
        model.addAttribute("cpuList", cpuList);
        return "home";
    }
}
