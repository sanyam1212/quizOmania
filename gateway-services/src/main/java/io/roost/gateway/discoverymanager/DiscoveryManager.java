package io.roost.gateway.discoverymanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.common.constants.Punctuation;
import org.springframework.boot.common.dto.HttpResponse;
import org.springframework.boot.common.exception.CommonException;
import org.springframework.boot.common.request.tracker.AbstractRequestTracker;
import org.springframework.boot.common.utils.RestUtils;
import org.springframework.boot.common.utils.RestUtils.HttpRequestMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.http.HttpRequest.Verb;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import io.roost.gateway.dto.Response;

@Component
public class DiscoveryManager {
	private static final String ENCODING = "UTF-8";

	Map<String, List<InstanceInfo>> registry = new HashMap<>();

	@Autowired
	private EurekaClient discoveryClient;

	@Scheduled(fixedRate = 30000)
	public void serviceUrl() {
		for (Application application : discoveryClient.getApplications().getRegisteredApplications()) {
			registry.put(application.getName().toLowerCase(), application.getInstances());
		}
	}

	public HttpResponse loadBalancedRestCall(HttpServletRequest req, String path) throws CommonException {
		return loadBalancedRestCall(req, path, Verb.valueOf(req.getMethod()));
	}

	private HttpResponse loadBalancedRestCall(HttpServletRequest req, String path, Verb verb) throws CommonException {
		int endPos = path.indexOf("/", 1);
		String moduleName = endPos > 0 ? path.substring(1, endPos) : path.substring(1);
		try {
			serviceUrl();
			registry.get(moduleName).get(0).toString();
		} catch (Exception e) {
			serviceUrl();
			registry.get(moduleName).get(0).toString();
		}
		InstanceInfo instance = registry.get(moduleName).get(0);
		StringBuilder urlBuilder = new StringBuilder(req.getScheme()).append(Punctuation.COLON)
				.append(Punctuation.FORWARD_SLASH).append(Punctuation.FORWARD_SLASH).append(instance.getHostName())
				.append(Punctuation.COLON).append(instance.getPort()).append(path);
		Map<String, Object> params = new HashMap<>();
		Map<String, String> headers = new HashMap<>();
		headers.put("Keep-Alive", "timeout=15, max=100");
		headers.put("Content-Type", MediaType.APPLICATION_JSON.toString());
		Enumeration<String> paramsEnum = req.getParameterNames();
		if (Verb.GET == verb) {
			while (paramsEnum.hasMoreElements()) {
				String key = paramsEnum.nextElement();
				try {
					params.put(key, URLEncoder.encode(req.getParameter(key), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					throw new CommonException(e);
				}
			}
		} else if (Verb.DELETE != verb) {
			InputStream is;
			try {
				is = req.getInputStream();
			} catch (IOException e) {
				throw new CommonException(e);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			try {
				line = br.readLine();
			} catch (IOException e) {
				throw new CommonException(e);
			}
			String data = "";
			while (line != null) {
				data += line;
				try {
					line = br.readLine();
				} catch (IOException e) {
					throw new CommonException(e);
				}
			}
			if (StringUtils.isNotBlank(data)) {
				params.put(RestUtils.DUMMY_FIELD, data);
			} else {
				while (paramsEnum.hasMoreElements()) {
					String key = paramsEnum.nextElement();
					try {
						params.put(key, URLEncoder.encode(req.getParameter(key), ENCODING));
					} catch (UnsupportedEncodingException e) {
						throw new CommonException(e);
					}
				}
			}
		}
		headers.put("requestId", (String) req.getAttribute("requestId"));
		headers.put("requestBy", req.getAttribute("requestBy") != null ? (String) req.getAttribute("requestBy")
				: AbstractRequestTracker.DEFAULT_USER);
		headers.put("domain", (String) req.getAttribute("domain"));
		headers.put("scheme", (String) req.getScheme());
		try {
			if ("GET".equalsIgnoreCase(verb.toString())) {
				boolean first = true;
				for (Entry<String, Object> entry : params.entrySet()) {
					if (RestUtils.DUMMY_FIELD.equalsIgnoreCase(entry.getKey())) {
						continue;
					}
					if (first) {
						urlBuilder.append("?");
						first = false;
					} else {
						urlBuilder.append("&");
					}
					urlBuilder.append(URLEncoder.encode(entry.getKey(), ENCODING));
					urlBuilder.append("=");
					urlBuilder.append(URLEncoder.encode(String.valueOf(entry.getValue()), ENCODING));
				}
			}
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}
		HttpResponse httpResponse = null;
		try {
			httpResponse = RestUtils.restCall(urlBuilder.toString(), HttpRequestMode.valueOf(verb.toString()),
					"GET".equalsIgnoreCase(verb.toString()) ? null : params, headers, true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CommonException(e);
		}
		return httpResponse;
	}

	public void writeResponse(HttpResponse resp, HttpServletResponse res) throws CommonException {
		try {
			writeResponse(resp.getResponseAsEntity(Response.class).getEntity(), resp.getStatusCode(), res);
		} catch (Exception e) {
			throw new CommonException(e);
		}

	}

	private void writeResponse(String resStr, int status, HttpServletResponse res) throws CommonException {
		res.setHeader("Content-Length", String.valueOf(resStr.getBytes().length));
		res.setContentType(MediaType.APPLICATION_JSON);
		res.setCharacterEncoding("UTF-8");
		try (PrintWriter out = res.getWriter()) {
			out.println(resStr);
			res.setStatus(status);
		} catch (IOException e) {
			res.setStatus(500);
			throw new CommonException(e);
		}
	}

}
