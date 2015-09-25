package com.elastic.contorller;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.elastic.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserContorller {

	private static String[] lastNames = { "akbari", "hekmat", "fozi", "kiaei",
			"khademi", "foladi", "masoudi", "aghaei", "farhadian", "golabi" };
	private static String[] names = { "nazi", "esa", "ali", "mitra", "shahin",
			"sima", "masume", "milad", "maryam", "hesam" };

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Client client;

	@PostConstruct
	public void addUser() throws ElasticsearchException,
			JsonProcessingException {

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
		searchUser();
	}

	public void searchUser() {
		SearchResponse response = client.prepareSearch("twitter")
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(QueryBuilders.termQuery("name", "esa")).setFrom(0)
				.setSize(60).setExplain(true).execute().actionGet();
		SearchHit[] results = response.getHits().getHits();
		for (SearchHit hit : results) {
			try {
				User user=mapper.readValue(hit.getSourceAsString(), User.class);
				System.out.println("User: "+user);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initialize() {
		if (!client.admin().indices().prepareExists("twitter").execute()
				.actionGet().isExists())
			client.admin().indices().prepareCreate("twitter").execute()
					.actionGet();

	}
}
