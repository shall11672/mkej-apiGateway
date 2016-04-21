package com.example;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}

@Configuration
class RestClientConfiguration {

    @LoadBalanced
    @Bean
    RestOperations restOperations() {
        return new RestTemplate();
    }
}


@RestController
@RequestMapping("/menu")
class MenuAPIGatewayController {
	
	@Autowired
	private RestOperations restOperations;
	

	@RequestMapping(method = RequestMethod.GET, value="/itemnames")
	public Collection<String> getMenuItemNames()
	{
		ParameterizedTypeReference<Resources<MenuItem>> ptr = new ParameterizedTypeReference<Resources<MenuItem>>(){};
		
		ResponseEntity<Resources<MenuItem>> respons = this.restOperations.exchange("http://menuservice/menuItems", HttpMethod.GET, null, ptr);
		
		Collection<MenuItem> content = respons.getBody().getContent();
		
		return content.stream().map(MenuItem::getName).collect(Collectors.toList());
	}

}

class MenuItem
{
	Long id;
	String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}