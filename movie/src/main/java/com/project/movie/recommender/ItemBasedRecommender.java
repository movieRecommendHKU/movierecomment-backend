package com.project.movie.recommender;

import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemBasedRecommender extends AbsRecommender {

    @Override
    protected void register() {
        RECOMMENDERS.put(RecommenderEnum.ITEM_BASED, this);
    }

    @Override
    protected List<MovieRecommend> recall(Integer userId) {
        return null;
    }

    @Override
    protected List<MovieRecommend> filter(List<MovieRecommend> recallResult, Integer userId) {
        return null;
    }

    @Override
    protected List<Integer> sort(List<MovieRecommend> filterResult) {
        return null;
    }
}
