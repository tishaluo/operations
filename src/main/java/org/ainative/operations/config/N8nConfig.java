package org.ainative.operations.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Getter
@Component
@ConfigurationProperties(prefix = "n8n")
public class N8nConfig {
    String baseurl;
    String apikey;
    String projectId;
    String headerKey;


}
