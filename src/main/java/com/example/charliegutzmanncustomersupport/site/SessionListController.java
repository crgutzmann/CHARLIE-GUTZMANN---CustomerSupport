package com.example.charliegutzmanncustomersupport.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SessionListController {
    @RequestMapping(value={"sessions", "ticket/sessions"})
    public String listSessions(Model model) {
        model.addAttribute("now", System.currentTimeMillis());
        model.addAttribute("numSessions", SessionListUtil.getNumberOfSessions());
        model.addAttribute("sessionList", SessionListUtil.getAllSessions());

        return "sessions";

    }

}