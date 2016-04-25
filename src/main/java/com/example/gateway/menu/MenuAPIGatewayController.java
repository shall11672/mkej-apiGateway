package com.example.gateway.menu;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@RestController
@RequestMapping("/menu")
public class MenuAPIGatewayController {
	
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