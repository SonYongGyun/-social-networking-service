package kr.co.mz.sns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SnsApplication {

    public static void main(String[] args) {

        SpringApplication.run(SnsApplication.class, args);
        
    }
}
