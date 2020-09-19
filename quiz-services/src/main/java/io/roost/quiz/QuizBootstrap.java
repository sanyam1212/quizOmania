package io.roost.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

import com.netflix.discovery.EurekaClient;
import com.sendgrid.SendGrid;

@SpringBootApplication
public class QuizBootstrap implements ApplicationRunner {

	@Autowired
	@Lazy
	private EurekaClient eurekaClient;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${sendgrid.api.key}")
	private String sendgridApiKey;

	private static SendGrid emailClient;

	public static void main(String[] args) {

		SpringApplication.run(QuizBootstrap.class, args);
	}

	private void initEmailClient() {
		emailClient = new SendGrid(sendgridApiKey);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initEmailClient();
	}

	public static SendGrid getEmailClient() {
		return emailClient;
	}

}
