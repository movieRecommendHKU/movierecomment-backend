package com.project.movie.service.movie.recommend;

import com.project.movie.recommender.AbsRecommender;
import com.project.movie.recommender.ItemBasedRecommender;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.yml")
public class RecommendServiceTest {

	@Autowired
	private ItemBasedRecommender recommender;

	public RecommendServiceTest() {
	}

	@Test
	void getItemBasedTest() {
		AbsRecommender.recommend(1, recommender);
	}
}

