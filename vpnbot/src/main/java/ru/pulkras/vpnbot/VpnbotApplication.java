package ru.pulkras.vpnbot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()

public class VpnbotApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(VpnbotApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
