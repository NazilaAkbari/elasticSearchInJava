package com.elastic.contorller;

import javax.annotation.PostConstruct;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elastic.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserContorller {

	private static String[] lastNames = { "akbari", "hekmat", "fozi", "kiaei",
			"khademi", "foladi", "masoudi", "aghaei", "farhadian", "golabi" };
	private static String[] names = { "nazi", "esa", "ali", "mitra", "shahin",
			"sima", "masume", "milad", "maryam", "hesam" };

	@Autowired
	private Client client;
	@Autowired
	private ObjectMapper mapper;

	@PostConstruct
	public void addUser() throws ElasticsearchException, JsonProcessingException {

		initialize();
		for (int i = 1; i <= 10; i++) {
			User user = new User();
			user.setId(i);
			user.setAge(15 + i);
			user.setLastName(lastNames[i - 1]);
			user.setName(names[i - 1]);
			IndexResponse response = client.prepareIndex("twitter", "tweet")
					.setSource(mapper.writeValueAsString(user)).execute()
					.actionGet();
			System.out.println(response.getId());
		}
	}

	private void initialize() {
		if (!client.admin().indices().prepareExists("twitter").execute()
				.actionGet().isExists())
			client.admin().indices().prepareCreate("twitter").execute()
					.actionGet();

	}
}
