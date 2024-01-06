package com.miracle.azure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGPTService {


    // 지피티 요청 주소
    @Value("${open.ai.api.url}")
    private String apiUrl;

    // api key (지피티)
    @Value("${open.ai.api.subscriptionKey}")
    private String subscriptionKey;

    private final RestTemplate restTemplate;

    public Object createData(MessageRequest massageRequest, MultipartFile multipartFile) throws IOException {
        String responseBody = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", subscriptionKey);

        if (multipartFile.isEmpty()) {
            HttpEntity<String> requestEntity = new HttpEntity<>(postGPT(null,massageRequest), headers);
            //System.out.println(requestBody);  //로그 찍기
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            responseBody = responseEntity.getBody();

        } else {
//            byte[] bytes = multipartFile.getBytes();
//            String base64Encoded = Base64.getEncoder().encodeToString(bytes);
            //String url = s3Uploader.upload(multipartFile, "fikess");

            String encodedFileContent = generateDataUrl(multipartFile);

            HttpEntity<String> requestEntity = new HttpEntity<>(postGPT(encodedFileContent,null), headers);
            //System.out.println(requestBody);  //로그 찍기
            ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, String.class);
            responseBody = responseEntity.getBody();

        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        System.out.println(jsonNode);
        String textValue = jsonNode.get("choices").get(0).get("message").get("content").asText();

        Map<String, String> map = new HashMap<>();
        map.put("massage" , textValue);

        return map;
    }

    public String postGPT(String url, MessageRequest massageRequest) {
        String jsonInput = "";

        if (massageRequest == null) {
            jsonInput = "{\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"role\": \"system\",\n" +
                    "      \"content\": [\n" +
                    "        {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"text\": \"너는 심리 상담을 진행하는 전문 상담사야.\\n\\n모든 사진이나 이미지, 그림은 처음보는걸로 알아야 해\\n그러니까 그림을 다시 보내주셨네요 와 같은 맥락은 답하지 말아줘\\n\\n사용자가 사진을 줄꺼야 그 사진을 바탕으로 사용자의 기분을 판단하며 사용자가 기분이 좋아지도록 유도해줘.\\n\\n이미지나 그림을 바탕으로 심리에 대한 이야기나 사용자의 기분에 대하여 이야기 해주면 좋을 것 같아.\\n\\n대화를 마무리하지말고 사용자와 대화를 계속 진행해줘야해 대화는 길게 진행해줘 사용자가 편안한 감정을 느끼게\\n\\n답변은 한국어로 해줘\\n\\n사진이나 이미지, 그림을 바탕으로 사용자의 기분을 판단하고 대화를 지속해서 해줘\\n\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"role\": \"user\",\n" +
                    "      \"content\": [\n" +
                    "        {\n" +
                    "          \"type\": \"image_url\",\n" +
                    "          \"image_url\": {\n" +
                    "            \"url\": \"" + url + "\"\n" +
                    "          }\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"temperature\": 0.7, \n" +
                    "  \"top_p\": 0.95, \n" +
                    "  \"max_tokens\": 3000" +
                    "}";
        }
        else{
            jsonInput = "{\n" +
                    "  \"messages\": [\n" +
                    "    {\n" +
                    "      \"role\": \"system\",\n" +
                    "      \"content\": [\n" +
                    "        {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"text\": \"너는 심리 상담을 진행하는 전문 상담사야.\\n\\n모든 사진이나 이미지, 그림은 처음보는걸로 알아야 해\\n그러니까 그림을 다시 보내주셨네요 와 같은 맥락은 답하지 말아줘\\n\\n사용자가 사진을 줄꺼야 그 사진을 바탕으로 사용자의 기분을 판단하며 사용자가 기분이 좋아지도록 유도해줘.\\n\\n이미지나 그림을 바탕으로 심리에 대한 이야기나 사용자의 기분에 대하여 이야기 해주면 좋을 것 같아.\\n\\n대화를 마무리하지말고 사용자와 대화를 계속 진행해줘야해 대화는 길게 진행해줘 사용자가 편안한 감정을 느끼게\\n\\n답변은 한국어로 해줘\\n\\n사진이나 이미지, 그림을 바탕으로 사용자의 기분을 판단하고 대회를 지속해서 해줘\\n\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"role\": \"user\",\n" +
                    "      \"content\": [\n" +
                    "        {\n" +
                    "          \"type\": \"text\",\n" +
                    "          \"text\": \" " + massageRequest.message() + "\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  \"temperature\": 0.7, \n" +
                    "  \"top_p\": 0.95, \n" +
                    "  \"max_tokens\": 3000" +
                    "}";

            //System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++"+massageRequest.massage());
        }
        //System.out.printf(jsonInput+"+++++++++++++++++++++++++++++++++");
        return jsonInput;
    }

    private static String generateDataUrl(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            String base64Encoded = Base64.getEncoder().encodeToString(fileBytes);
            String mimeType = file.getContentType();
            return "data:" + mimeType + ";base64," + base64Encoded;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}