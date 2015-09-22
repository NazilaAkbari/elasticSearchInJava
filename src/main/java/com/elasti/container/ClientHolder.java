package com.elasti.container;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ClientHolder {

	private static Client client;

	public static Client getClient() {
		if (client == null) {
			Node node = NodeBuilder.nodeBuilder().node();
			client = node.client();
		}
		return client;
	}
}
