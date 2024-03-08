package com.moneyview.employeePortal.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class CloudinaryConfiguration {

    private final String cloudName="dfk6ftcdb";

    private final String apiKey="263755754574323";

    private final String apiSecret="EdhRHBW_0orWJdjNNlOJzVZSVkg";

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }
}