package io.roost.gateway.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("io.roost.gateway")
@SpringBootApplication
@EnableEurekaClient
public class GatewayBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(GatewayBootstrap.class, args);
	}

}
