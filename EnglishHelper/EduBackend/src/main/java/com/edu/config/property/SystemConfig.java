package com.edu.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system")
@Data
public class SystemConfig {
    private WxConfig wx;
    private QnConfig qn;
}
