package com.url.urlmessage.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileStoragePropertys {
    @Getter
    @Setter
    private String uploadDir;
}
