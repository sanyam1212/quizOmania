package io.roost.quiz.bootstrap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.common.configuration.AbstractBootstrapConfiguration;

public class QuizBootstrapConfiguration extends AbstractBootstrapConfiguration {
	@Value("${spring.jpa.datasource.url}")
	private static String url;

	public static String getUrl() {
		return url;
	}

}
