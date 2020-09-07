package io.roost.gateway.discoverymanager;

import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class DiscoveryClientExample implements CommandLineRunner {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Override
	public void run(String... strings) throws Exception {
		discoveryClient.getServices().forEach((String s) -> {
			discoveryClient.getInstances(s).forEach((ServiceInstance si) -> {
				System.out.println(si.getHost());
				System.out.println(si.getInstanceId());
				System.out.println(si.getMetadata());
				System.out.println(si.getPort());
				System.out.println(si.getScheme());
				System.out.println(si.getServiceId());
				System.out.println(si.getUri());
				System.out.println(si.isSecure());
				System.out.println(si.toString());
				for (Map.Entry<String, String> entry : si.getMetadata().entrySet()) {
					System.out.println(entry.getKey() + " ===>>> " + entry.getValue());
				}
			});
		});
		System.out.println();
		discoveryClient.getInstances("photo-service").forEach((ServiceInstance s) -> {
			System.out.println(ToStringBuilder.reflectionToString(s));
		});
		discoveryClient.getInstances("bookmark-service").forEach((ServiceInstance s) -> {
			System.out.println(ToStringBuilder.reflectionToString(s));
		});
	}
}