package org.arpha.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("org.arpha")
@EnableJpaRepositories("org.arpha")
@ConfigurationPropertiesScan("org.arpha")
@EnableFeignClients("org.arpha")
@SpringBootApplication(scanBasePackages = {
        "org.arpha"
})
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
