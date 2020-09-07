package io.roost.quiz.error.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.springframework.boot.common.error.controller.AbstractErrorController;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizErrorController extends AbstractErrorController {

	@Override
	public Response handleError(HttpServletRequest request) {
		return super.handleError(request);
	}
	
	@Override
	public String getErrorPath() {
		return super.getErrorPath();
	}
	
}
