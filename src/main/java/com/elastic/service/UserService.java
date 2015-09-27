package com.elastic.service;


import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elastic.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Client client;

	public void addUser(User user) {
		initialize();
		try {
			client.prepareIndex("twitter", "tweet")
					.setSource(mapper.writeValueAsString(user)).execute()
					.actionGet();
		} catch (ElasticsearchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void deleteUser(User user){
		long id=user.getId()
				
		client.prepareDelete("twitter", "tweet",user.getId()).
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
	}*/

	private void initialize() {
		if (!client.admin().indices().prepareExists("twitter").execute()
				.actionGet().isExists())
			client.admin().indices().prepareCreate("twitter").execute()
					.actionGet();

	}
}
