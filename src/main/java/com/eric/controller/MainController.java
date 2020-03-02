package com.eric.controller;

import com.eric.model.GitUser;
import com.eric.utils.GitUserHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MainController {

    private final static Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public String index(@RequestParam(value = "user", required = false, defaultValue = "fcpgris") String user,
            Model model) {
        logger.info("got request on /, user = " + user);
        GitUser gitUser = GitUserHandler.getGitUserByName(user);
        model.addAttribute("gitUser", gitUser);
        
        return "index";
    }

}
