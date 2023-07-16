package com.project.movie.recommender;

import com.project.movie.domain.enums.RecommenderEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PreferenceRecommenderTest {
    @Test
    public void test() {
        AbsRecommender recommender = AbsRecommender.getRecommenderByProtocol(RecommenderEnum.PREFERENCE);
        assert recommender != null;
        List<Integer> movieId = AbsRecommender.recommend(3, recommender);
        System.out.println(movieId);
    }
}
