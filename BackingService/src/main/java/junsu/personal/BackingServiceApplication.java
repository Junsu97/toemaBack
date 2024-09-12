package junsu.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
@EnableMongoRepositories
@EnableScheduling
public class BackingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackingServiceApplication.class, args);
    }

}
