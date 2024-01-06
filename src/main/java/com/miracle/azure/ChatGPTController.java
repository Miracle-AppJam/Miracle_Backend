package com.miracle.azure;

import com.miracle.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatgpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/data")
    public Object createData(@RequestPart("data") MassageRequest massageRequest , @RequestPart("file") MultipartFile multipartFile) throws IOException {
        return chatGPTService.createData(massageRequest, multipartFile);
    }

}
