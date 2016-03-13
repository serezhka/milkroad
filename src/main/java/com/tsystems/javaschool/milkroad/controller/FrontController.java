package com.tsystems.javaschool.milkroad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Sergey on 12.03.2016.
 */
@Controller
@RequestMapping(value = {"/", ""})
public class FrontController {
    @RequestMapping
    public String indexPage() {
        return "index";
    }
}
