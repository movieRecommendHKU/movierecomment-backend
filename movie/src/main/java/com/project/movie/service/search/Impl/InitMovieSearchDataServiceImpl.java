package com.project.movie.service.search.Impl;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.project.movie.config.EsUtilConfigClint;
import com.project.movie.domain.DO.MovieForSearch;
import com.project.movie.service.search.InitMovieSearchDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InitMovieSearchDataServiceImpl implements InitMovieSearchDataService {
    @Autowired
    private EsUtilConfigClint clint;

    final Integer WORD_VECTOR_TYPE = 1;
    final Integer SENTENCE_VECTOR_TYPE = 2;

    public List<Map<String,String>> readXlsx(File file) throws Exception{
        InputStream in = Files.newInputStream(file.toPath());
        // read Excel
        XSSFWorkbook sheets = new XSSFWorkbook(in);
        // get first Sheet
        XSSFSheet sheetAt = sheets.getSheetAt(0);
        // set the first line as title line，i = 0
        XSSFRow titleRow = sheetAt.getRow(0);
        List<Map<String,String>> mapList = new ArrayList<>();
        // get each line
        for (int i = 1; i < sheetAt.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = sheetAt.getRow(i);
            // read each cell
            Map<String,String> map = new HashMap<>();
            for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
                XSSFCell titleCell = titleRow.getCell(index);
                XSSFCell cell = row.getCell(index);
                if (cell==null){
                    continue;
                }
                cell.setCellType(CellType.STRING);
                if (cell.getStringCellValue().equals("")) {
                    continue;
                }
                // title data
                String titleName = titleCell.getStringCellValue();
                // cell data
                String valueName = cell.getStringCellValue();
                // line data
                map.put(titleName,valueName);
            }
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public Map<Integer,MovieForSearch> getMovieForSearchMap(File file) throws Exception {
        List<Map<String, String>> parseXlsxResult = readXlsx(file);
        Map<Integer,MovieForSearch> movieForSearchMap = new HashMap<>();
        for (Map<String, String> stringStringMap : parseXlsxResult) {
            try {
                MovieForSearch movieForSearch = new MovieForSearch();
                // 1. read movieId
                movieForSearch.setMovieId(Integer.valueOf(stringStringMap.get("movie_id")));

                // 2. read movieName
                String movie_name = stringStringMap.get("movie_name");
                movieForSearch.setMovieName(movie_name);

                // 3. read overview
                String overview = stringStringMap.get("overview");
                movieForSearch.setOverview(overview);

                // 4. read directorName
                JSONObject director = JSONObject.fromObject(stringStringMap.get("director").replaceAll("None", "\"None\""));
                String directorName = director.get("name").toString();
                movieForSearch.setDirectorName(directorName);

                // 5. read producer
                List<String> producerNamesItem = new ArrayList<>();
                JSONArray json_producerNames = JSONArray.fromObject(stringStringMap.get("production_companies").replaceAll("None", "\"None\""));
                for (int i = 0; i < json_producerNames.size(); i++) {
                    JSONObject o = json_producerNames.getJSONObject(i);
                    producerNamesItem.add(o.get("name").toString());
                }
                if (producerNamesItem.size() == 0) producerNamesItem.add("");
                movieForSearch.setProducerNames(producerNamesItem);

                // 6. read rating
                movieForSearch.setRating(Double.valueOf(stringStringMap.get("vote_average")));

                // 7. read releaseDate
                String release_date = stringStringMap.get("release_date");
                movieForSearch.setReleaseDate(release_date.substring(1, release_date.length() - 1));

                // 8. read castNames
                List<String> castNamesItem = new ArrayList<>();
                JSONArray json_castNames = JSONArray.fromObject(stringStringMap.get("cast").replaceAll("None", "\"None\""));
                for (int i = 0; i < json_castNames.size(); i++) {
                    JSONObject o = json_castNames.getJSONObject(i);
                    castNamesItem.add(o.get("name").toString());
                }
                if (castNamesItem.size() == 0) castNamesItem.add("");
                movieForSearch.setCastNames(castNamesItem);

                // 9. read keyWords
                List<String> keyWordsItem = new ArrayList<>();
                JSONArray json_keyWords = JSONArray.fromObject(stringStringMap.get("keywords").replaceAll("None", "\"None\""));
                for (int i = 0; i < json_keyWords.size(); i++) {
                    JSONObject o = json_keyWords.getJSONObject(i);
                    keyWordsItem.add(o.get("name").toString());
                }
                if (keyWordsItem.size() == 0) keyWordsItem.add("");
                movieForSearch.setKeyWords(keyWordsItem);

                // 10. read genres
                List<String> genresItem = new ArrayList<>();
                JSONArray json_genres = JSONArray.fromObject(stringStringMap.get("genres").replaceAll("None", "\"None\""));
                for (int i = 0; i < json_genres.size(); i++) {
                    JSONObject o = json_genres.getJSONObject(i);
                    genresItem.add(o.get("name").toString());
                }
                if (genresItem.size() == 0) genresItem.add("");
                movieForSearch.setGenres(genresItem);

                if(!movieForSearchMap.containsKey(movieForSearch.getMovieId())){
                    movieForSearchMap.put(movieForSearch.getMovieId(),movieForSearch);
                }
            } catch (Exception ignored) {
            }
        }
        return movieForSearchMap;
    }

    @Override
    public void updateKeywordsForMovie(File file, Map<Integer, MovieForSearch> movieForSearchMap) throws Exception {
        List<Map<String, String>> parseXlsxResult = readXlsx(file);
        for (Map<String, String> stringStringMap : parseXlsxResult){
            try{
                Integer movieId = Integer.valueOf(stringStringMap.get("movie_id"));
                String stringKeywords = stringStringMap.get("keywords");
                stringKeywords = stringKeywords.substring(1, stringKeywords.length()-1);
                String[] keywords = stringKeywords.split(", ");
                List<String> Keywords = new ArrayList<>();
                for (String keyword : keywords) {
                    Keywords.add(keyword.substring(1, keyword.length() - 1));
                }
                if (movieForSearchMap.containsKey(movieId)){
                    MovieForSearch movieForSearch = movieForSearchMap.get(movieId);
                    movieForSearch.setKeyWords(Keywords);
                }
            }catch (Exception ignored){
            }
        }
    }

    @Override
    public void setVectorForMovie(File file, Map<Integer, MovieForSearch> movieForSearchMap, Integer vectorType) throws Exception {
        List<Map<String, String>> parseXlsxResult = readXlsx(file);
        for (Map<String, String> stringStringMap : parseXlsxResult){
            try{
                Integer movieId = Integer.valueOf(stringStringMap.get("movie_id"));
                if (movieForSearchMap.containsKey(movieId)){
                    List<Double> vectorsItem = new ArrayList<>();
                    String stringVector = stringStringMap.get("embedding_data");
                    MovieForSearch movieForSearch = movieForSearchMap.get(movieId);
                    if(vectorType.equals(1)){
                        stringVector = stringVector.substring(1,stringVector.length()-1);
                        String[] stringVectorList =  stringVector.split(", ");
                        for (String s : stringVectorList) {
                            vectorsItem.add(Double.valueOf(s));
                        }
                        movieForSearch.setWordVectors(vectorsItem);
                    } else if (vectorType.equals(2)) {
                        String[] stringVectorList =  stringVector.split(",");
                        for (String s : stringVectorList) {
                            vectorsItem.add(Double.valueOf(s));
                        }
                        movieForSearch.setSentenceVectors(vectorsItem);
                    }
                }
            }catch (Exception ignored){
            }
        }
    }


    @Override
    public Integer addMovieToElasticSearch(String contentPath) throws Exception {
        // movies before 2017
        contentPath = "../movie_es_all.xlsx";
//        contentPath = "/Users/jackwu/Desktop/HKU/Project/movies_es_all.xlsx";
        File movie_file = new File(contentPath);
        if (!movie_file.exists()){
            throw new Exception("文件不存在!");
        }
        Map<Integer,MovieForSearch> MovieForSearchMap = getMovieForSearchMap(movie_file);
        System.out.println(MovieForSearchMap.keySet());

        // update keywords for the movies
        String keyPath = "../merged_movies_data.xlsx";
//        String keyPath = "/Users/jackwu/Desktop/HKU/Project/merged_movies_data.xlsx";
        File key_file = new File(keyPath);
        if (!key_file.exists()){
            throw new Exception("文件不存在!");
        }
        updateKeywordsForMovie(key_file, MovieForSearchMap);
        System.out.println(MovieForSearchMap);

        // set word embedding vector for movies
        String WordFilePath = "../word_embedding_data.xlsx";
//        String WordFilePath = "/Users/jackwu/Desktop/HKU/Project/word_embedding_data.xlsx";
        File word_embedding_file = new File(WordFilePath);
        if (!word_embedding_file.exists()){
            throw new Exception("文件不存在!");
        }
        setVectorForMovie(word_embedding_file, MovieForSearchMap, WORD_VECTOR_TYPE);

        // set sentence embedding vector for movies
        String SentenceFilePath = "../sentence_embedding_data.xlsx";
//        String SentenceFilePath = "/Users/jackwu/Desktop/HKU/Project/sentence_embedding_data.xlsx";
        File sentence_embedding_file = new File(SentenceFilePath);
        if (!sentence_embedding_file.exists()){
            throw new Exception("文件不存在!");
        }
        setVectorForMovie(sentence_embedding_file, MovieForSearchMap, SENTENCE_VECTOR_TYPE);
        System.out.println(MovieForSearchMap);

        List<MovieForSearch> MovieForSearchList = new ArrayList<>(MovieForSearchMap.values());

//        String host = "121.43.150.228";
//        int port = 9200;
//        String username = "elastic";
//        String password = "Wtc@0229";
//        RestHighLevelClient client = createElasticsearchClient(host, port, username, password);

        BulkRequest.Builder bk = new BulkRequest.Builder();
        for (MovieForSearch movieForSearch : MovieForSearchList) {
            bk.operations(op -> op.index(i -> i.index("newindex")
                    .id(String.valueOf(movieForSearch.getMovieId()))
                    .document(movieForSearch)));
        }
        BulkResponse response = clint.configClint().bulk(bk.build());
        if (response.errors()) {
            System.out.println("Bulk had errors");
            for (BulkResponseItem item: response.items()) {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            }
        }
        return 1;
    }

    // 创建带有身份验证的Elasticsearch客户端连接
//    private RestHighLevelClient createElasticsearchClient(String host, int port, String username, String password) {
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//
//        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, "https"))
//                .setHttpClientConfigCallback(httpClientBuilder ->
//                        httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
//
//        RestHighLevelClient client = new RestHighLevelClient(builder);
//        return client;
//    }
}
