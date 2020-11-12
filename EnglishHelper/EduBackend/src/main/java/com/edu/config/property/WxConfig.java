package com.edu.config.property;

import lombok.Data;

import java.time.Duration;
import java.util.List;

@Data
public class WxConfig {
    private String appid;
    private String secret;
    private List<String> securityIgnoreUrls;
}
