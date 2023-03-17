package ru.pulkras.vpnbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class VpnbotApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(VpnbotApplication.class, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
