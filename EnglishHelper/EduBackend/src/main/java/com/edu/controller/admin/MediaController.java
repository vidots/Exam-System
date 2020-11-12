package com.edu.controller.admin;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.edu.base.RestResponse;
import com.edu.util.CONSTANTS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Value("${oss.bucket}")
    private String bucket;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.key}")
    private String key;

    @Value("${oss.secret}")
    private String secret;

    @Value("${oss.url}")
    private String url;

    @PostMapping("/upload/image")
    public RestResponse uploadImage(@RequestParam("image") MultipartFile image, HttpServletRequest request) throws IOException {
        String identifier = UUID.randomUUID().toString().replace("-", "");
        String fileName = identifier + "_" + image.getOriginalFilename();
        OSS ossClient = new OSSClientBuilder().build(endpoint, key, secret);
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(image.getBytes()));
        ossClient.shutdown();
        String location = url + "/" + fileName;
        return RestResponse.ok(location);
    }
}












