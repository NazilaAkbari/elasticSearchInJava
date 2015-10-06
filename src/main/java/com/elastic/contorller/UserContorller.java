package com.elastic.contorller;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.elastic.model.User;
import com.elastic.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public void addUser(@RequestBody User user) {
		userService.addUser(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<User> search(@RequestParam String searchParam)
			throws JsonParseException, JsonMappingException, IOException {
		return userService.search(searchParam);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	public void removeUser(User user) {
		userService.addUser(user);
		System.out.println("added" + user.getId());
	}

}
