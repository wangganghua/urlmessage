package com.url.urlmessage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jsp")
public class HtmlController {
    @RequestMapping("/htmls")
    private String returns() {
        return "index";
    }
}
