package com.project.server;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = "com.project.server.**.mapper")
@EnableScheduling
public class ServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);
    public static void main(String... args) {

        SpringApplication.run(ServerApplication.class, args);

        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\project3.0\\javaStart.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                for (String s : fields) {
                    logger.info("*{}", s);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
