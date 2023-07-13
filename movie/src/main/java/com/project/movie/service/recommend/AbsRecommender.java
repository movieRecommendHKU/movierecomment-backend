package com.project.movie.service.recommend;

import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;


public abstract class AbsRecommender implements InitializingBean {

    static protected HashMap<RecommenderEnum, AbsRecommender> RECOMMENDERS = new HashMap<>();

    public static List<AbsRecommender> getRecommenders() {
        return Lists.newArrayList(RECOMMENDERS.values().iterator());
    }

    @Nullable
    public static AbsRecommender getRecommenderByProtocol(RecommenderEnum protocol) {
        return RECOMMENDERS.get(protocol);
    }

    @Override
    public void afterPropertiesSet() {
        register();
    }

    abstract protected void register();

    /**
     * Recommend movies for user
     *
     * @param userId
     * @param recommender
     * @return
     */
    public static List<Integer> recommend(Integer userId, AbsRecommender recommender) {
        return recommender.sort(
                recommender.filter(
                        recommender.recall(userId)));
    }

    abstract protected List<MovieRecommend> recall(Integer userId);

    abstract protected List<MovieRecommend> filter(List<MovieRecommend> recallResult);

    abstract protected List<Integer> sort(List<MovieRecommend> filterResult);

    /**
     * default resort
     *
     * @param recommendLists
     * @return movie ids
     */
    public static List<Integer> resort(List<List<Integer>> recommendLists) {
        HashSet<Integer> ids = new HashSet<>();
        for (List<Integer> recommendList : recommendLists)
            ids.addAll(recommendList);
        return ids.stream().toList();
    }

}
