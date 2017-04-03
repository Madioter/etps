package com.madiot.enterprise.controller;

import com.madiot.enterprise.model.Attachment;
import com.madiot.enterprise.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by DELL on 2016/8/27.
 */
@Controller
@RequestMapping("/")
public class TestController {

    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping("/test")
    public void test() throws FileNotFoundException {
        attachmentService.getAttachment(4);
    }
}
