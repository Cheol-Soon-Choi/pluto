package com.ccs.pluto.controller;

import com.ccs.pluto.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class testController {
    private S3Service s3Service;

    @GetMapping("/test")
    public String test() {

        return "/item/test";
    }

    @PostMapping("/test")
    public String execWrite(@RequestParam("file") MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        System.out.println(imgPath);
        return "redirect:/";
    }
}
