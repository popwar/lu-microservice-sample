package org.lu.loadbalancer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
//import com.netflix.loadbalancer.PingUrl;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;

@Configuration
public class RibbonConfiguration {
	@Bean
	public IPing ribbonPing(IClientConfig config) {
		return new NIWSDiscoveryPing ();
	}
}
