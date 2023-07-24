package team3.secondhand.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {

    @Value("34.123.18.157:9200")
    private String elasticsearchAddress;

    @Value("team3")
    private String elasticsearchUsername;

    @Value("secondhand2023")
    private String elasticsearchPassword;

    // 我们在本机运行着app,但是我们Elastic Search Service是运行在GCP上的VM instance中
    // 因此我们得从本机发送request给那个instance进行连接, 这里就是这个RestHighLevelClient的作用
    // 帮助我们连接我们在GCP创建的Elastic Search Service
    // 这样当我们在DescriptionRepository中使用调用其中的method进行搜索的时候
    // 他就会自动去连接
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration
                = ClientConfiguration.builder()
                .connectedTo(elasticsearchAddress)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
