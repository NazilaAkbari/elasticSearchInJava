package com.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elastic.model.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
			client.prepareIndex("user", "info")
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

	/*
	 * public void deleteUser(User user){ long id=user.getId()
	 * 
	 * client.prepareDelete("twitter", "tweet",user.getId()). }
	 * 
	 * public void searchUser() { SearchResponse response =
	 * client.prepareSearch("twitter")
	 * .setSearchType(SearchType.QUERY_AND_FETCH)
	 * .setQuery(QueryBuilders.termQuery("name", "esa")).setFrom(0)
	 * .setSize(60).setExplain(true).execute().actionGet(); SearchHit[] results
	 * = response.getHits().getHits(); for (SearchHit hit : results) { try {
	 * User user=mapper.readValue(hit.getSourceAsString(), User.class);
	 * System.out.println("User: "+user); } catch (JsonParseException e) { //
	 * TODO Auto-generated catch block e.printStackTrace(); } catch
	 * (JsonMappingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } } }
	 */

	public List<User> search(String searchParam) throws JsonParseException,
			JsonMappingException, IOException {
		MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(
				searchParam, "_all");
/*		MatchAllQueryBuilder multiMatchQuery = QueryBuilders.matchAllQuery();
*/		SearchResponse response = client.prepareSearch("user")
				.setSearchType(SearchType.QUERY_AND_FETCH)
				.setQuery(multiMatchQuery).setFrom(0).setSize(60)
				.setExplain(true).execute().actionGet();
		SearchHit[] results = response.getHits().getHits();
		List<User> users = new ArrayList<User>();
		for (SearchHit hit : results) {
			User user = mapper.readValue(hit.getSourceAsString(), User.class);
			users.add(user);
		}
		return users;
	}

	private void initialize() {
		if (!client.admin().indices().prepareExists("user").execute()
				.actionGet().isExists())
			client.admin().indices().prepareCreate("user").execute()
					.actionGet();

	}
}
