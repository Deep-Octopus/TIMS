package com.example.tims.listener;

import java.io.FileWriter;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

import com.example.tims.util.LogUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

@Component
public class JwtSecretKeyInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Check if the secret key is already set
        if (secret == null || secret.isEmpty()) {
            // Generate a random secret key
            byte[] keyBytes = new byte[32];
            new SecureRandom().nextBytes(keyBytes);
            String newSecret = Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);

            // Update the application.yml file with the new secret key
            try {
                Yaml yaml = new Yaml();
                Resource resource = new ClassPathResource("application.yml");
                InputStream inputStream = resource.getInputStream();
                Map<String, Object> yamlMap = yaml.load(inputStream);

                LogUtils.info("secret"+ secret);

                yamlMap.put("jwt.secret", newSecret);
                FileWriter writer = new FileWriter(resource.getFile());
                yaml.dump(yamlMap, writer);
                writer.close();
            } catch (Exception e) {
                LogUtils.error("秘钥生成错误");
                e.printStackTrace();
            }

            // Update the field annotated with @Value
            secret = newSecret;
        }
    }
}

