package com.project.movie.service.search.Impl;

import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.project.movie.config.EsUtilConfigClint;
import com.project.movie.domain.DO.MovieForSearch;
import com.project.movie.service.search.InitMovieSearchDataService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
            // read each row
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
        //System.out.println(JSON.toJSONString(mapList));
        return mapList;
    }

    @Override
    public List<MovieForSearch> getMovieForSearchList(File file) throws Exception {
        List<Map<String, String>> parseXlsxResult = readXlsx(file);
        List<MovieForSearch> movieForSearchList = new ArrayList<>();
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

                // 11. read wordVectors
                List<Double> wordVectorsItem = new ArrayList<>();
                String stringWordVector = stringStringMap.get("vector1");
                String[] stringWordVectorList =  stringWordVector.split(" ");
                for (String s : stringWordVectorList) {
                    wordVectorsItem.add(Double.valueOf(s));
                }
                movieForSearch.setWordVectors(wordVectorsItem);

                // 12. read sentenceVectors
                List<Double> sentenceVectorsItem = new ArrayList<>();
                String stringSentenceVector = stringStringMap.get("vector2");
                String[] stringSentenceVectorList =  stringSentenceVector.split(" ");
                for (String s : stringSentenceVectorList) {
                    sentenceVectorsItem.add(Double.valueOf(s));
                }
                movieForSearch.setSentenceVectors(sentenceVectorsItem);

                movieForSearchList.add(movieForSearch);
            } catch (Exception ignored) {
            }
        }
        return movieForSearchList;
    }

    @Override
    public Integer addMovieToElasticSearch(String contentPath) throws Exception {
        contentPath = "/Users/chengdonghuang/Desktop/test.xlsx";
        File file = new File(contentPath);
        if (!file.exists()){
            throw new Exception("文件不存在!");
        }
        List<MovieForSearch> MovieForSearchList = getMovieForSearchList(file);
        System.out.println(MovieForSearchList);
        BulkRequest.Builder bk = new BulkRequest.Builder();
        for (MovieForSearch movieForSearch : MovieForSearchList) {
            bk.operations(op -> op.index(i -> i.index("newindex2")
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
        return MovieForSearchList.size();
    }
}
