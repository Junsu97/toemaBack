package junsu.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BackingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackingServiceApplication.class, args);
    }

}
