package com.elastic.contorller;


import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elastic.model.User;
import com.elastic.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/user")
public class UserContorller {


	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Client client;
	@Autowired
	private UserService userService;

	@RequestMapping(method=RequestMethod.PUT)
	@ResponseBody
	public void addUser(User user){
		userService.addUser(user);
	}
	
	@RequestMapping(method=RequestMethod.DELETE)
	@ResponseBody
	public void removeUser(User user){
		userService.addUser(user);
		System.out.println("added"+user.getId());
	}

	

	
}
