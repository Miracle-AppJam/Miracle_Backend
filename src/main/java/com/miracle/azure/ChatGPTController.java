package com.miracle.azure;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chatgpt", method = {RequestMethod.POST})
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/data")
    public Object createFile(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return chatGPTService.createFile(multipartFile);
    }

    @PostMapping("/file")
    public Object createData(@RequestBody MessageRequest massageRequest) throws JsonProcessingException {
        return chatGPTService.createData(massageRequest);
    }
}
