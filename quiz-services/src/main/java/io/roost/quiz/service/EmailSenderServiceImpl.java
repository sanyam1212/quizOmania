package io.roost.quiz.service;

import static io.roost.quiz.QuizBootstrap.getEmailClient;

import java.io.IOException;

import org.springframework.boot.common.exception.CommonException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;;

public class EmailSenderServiceImpl implements EmailSenderService {

	private void send(String sendToEmail, String subject, String htmlContent) throws CommonException {
		Email from = new Email("jain_sanyam@infoobjects.com");
		Email to = new Email(sendToEmail);
		Content content = new Content("text/html", htmlContent);
		Mail mail = new Mail(from, subject, to, content);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = getEmailClient().api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw new CommonException(ex.getMessage(), ex);
		}

	}

	@Override
	public void sendQuizCreationEmail(String sendTo, String quizName, String password, String quizzerPassword,
			String URL) throws CommonException {
		StringBuilder htmlContentBuilder = new StringBuilder("<html><head></head><body><div>Hi ").append(sendTo)
				.append(",<br>Your Quiz has been created successfully with the following details:<ul><li>Quiz Name: ")
				.append(quizName).append("</li><li>Password: ").append(password).append("</li><li>Quizzer password: ")
				.append(quizzerPassword)
				.append("</li><li>You can access the Quizzers List by clicking <a target=\"_blank\" href=\"")
				.append(URL).append("\" >").append(URL).append("</a>.</li></div></body></html>");
		send(sendTo, "Quiz \"" + quizName + "\" Created Successfully.", htmlContentBuilder.toString());
	}

	@Override
	public void sendQuizAttemptEmail(String sendTo, String quizName, String score, String URL) throws CommonException {
		StringBuilder htmlContentBuilder = new StringBuilder("<html><head></head><body><div>Hi ").append(sendTo)
				.append(",<br>You have attempted the quiz successfully with the following details:<ul><li>Quiz Name: ")
				.append(quizName).append("</li><li>Score: ").append(score)
				.append("</li><li>You can access the Attempted Quiz by clicking <a target=\"_blank\" href=\"")
				.append(URL).append("\" >").append(URL).append("</a>.</li></div></body></html>");
		send(sendTo, "Quiz \"" + quizName + "\" Attempted Successfully.", htmlContentBuilder.toString());
	}

}
