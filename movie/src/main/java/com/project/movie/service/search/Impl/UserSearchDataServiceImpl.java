package com.project.movie.service.search.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.json.JsonpMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.project.movie.config.EsUtilConfigClint;
import com.project.movie.domain.DTO.UserSimilarityInfo;
import com.project.movie.service.search.UserSearchDataService;
import jakarta.json.stream.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UserSearchDataServiceImpl implements UserSearchDataService {
    @Autowired
    private EsUtilConfigClint clint;

    @Autowired
    private ElasticsearchClient esClient;

    @Override
    public Boolean initialElasticSearch() throws Exception{
        // 初始化创建index
        Boolean res = false;
        // set mapping for the index
        try {
            // 确定es的index对应结构，userId(Integer)和similarity（dense_vector)
            String mappingPath = "/Users/chengdonghuang/Desktop/real_data/user_mappings.json";
            ElasticsearchClient client = clint.configClint();
            JsonpMapper mapper = client._transport().jsonpMapper();
            String mappings_str = new String(Files.readAllBytes(Paths.get(mappingPath)));
            JsonParser parser = mapper.jsonProvider()
                    .createParser(new StringReader(mappings_str));
            client.indices()
                    .create(createIndexRequest -> createIndexRequest.index("user_es_data")
                            .mappings(TypeMapping._DESERIALIZER.deserialize(parser, mapper)));
            res = true;
        }catch (Exception e){
            e.printStackTrace();
            ;
        }
        return res;
    }

    @Override
    public Boolean addUserToElasticSearch(UserSimilarityInfo userSimilarityInfo) throws Exception{
        // 用来add和update的，集成一体的，es里面没有的userId他就会新加入对应的UserSimilarityInfo（包括userId和similarity），
        // 假如是有的，就会用当前传入的UserSimilarityInfo进行更新（即更新userId对应的similarity）

        // 判断是否存在该id，使用userId作为es中的id
        // 记得不要存全0的vector进去，这样会导致cos计算出现NaN的错误
        GetResponse<ObjectNode> response = esClient.get(g -> g
                        .index("user_es_data")
                        .id(String.valueOf(userSimilarityInfo.getUserId())),
                ObjectNode.class
        );

        if (response.found()) {
            // update
            UpdateResponse<UserSimilarityInfo> response_update = esClient.update(e -> e
                    .index("user_es_data")
                    .id(String.valueOf(userSimilarityInfo.getUserId()))
                    .doc(userSimilarityInfo),
                    UserSimilarityInfo.class
            );
        }else {
            // add
            IndexResponse response_index = esClient.index(k -> k
                    .index("user_es_data")
                    .id(String.valueOf(userSimilarityInfo.getUserId()))
                    .document(userSimilarityInfo)
            );
        }
        return response.found();
    }

}
