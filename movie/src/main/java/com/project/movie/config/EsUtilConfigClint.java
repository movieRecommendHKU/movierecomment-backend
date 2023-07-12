package com.project.movie.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
@Configuration
public class EsUtilConfigClint {
    /**
     * customer client
     * @return
     * @throws IOException
     */
    public ElasticsearchClient configClint() throws IOException {
        // Create the low-level client
        RestClient restClient = RestClient.builder(
                new HttpHost("127.0.0.1", 9200)).build();

        // Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        // customer client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        return client;
    }
}
