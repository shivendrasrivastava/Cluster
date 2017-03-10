package com.sap.fieldglass.controller;

import com.sap.fieldglass.service.FileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by shivendrasrivastava on 3/9/17.
 */
@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    private FileReaderService fileReaderService;

    @RequestMapping(value="/home", method= RequestMethod.GET)
    public String home(ModelMap model)
    {
        fileReaderService.watchFile();
        return "index";
    }

    @RequestMapping(value="/session", method=RequestMethod.GET)
    public String session(ModelMap model)
    {
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS").format(new Date());

        model.addAttribute("info","Session Id is "+sessionId+ " and the current time is "+currentDate);
        return "index";
    }

}
