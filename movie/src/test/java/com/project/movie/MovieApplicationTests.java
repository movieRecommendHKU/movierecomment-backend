package com.project.movie;

import com.project.movie.domain.enums.RecommenderEnum;
import com.project.movie.recommender.AbsRecommender;
import com.project.movie.recommender.ItemBasedRecommender;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
class MovieApplicationTests {

	@Test
	void contextLoads() {
	}

}
