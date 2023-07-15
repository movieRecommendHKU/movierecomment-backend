package com.project.movie.recommender;

import com.project.movie.domain.DTO.MovieRecommend;
import com.project.movie.domain.enums.RecommenderEnum;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.InitializingBean;

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
        List<MovieRecommend> recallResult = recommender.recall(userId);
        List<MovieRecommend> filterResult = recommender.filter(recallResult, userId);
        List<Integer> sortResult = recommender.sort(filterResult);
        return sortResult;
    }

    abstract protected List<MovieRecommend> recall(Integer userId);

    abstract protected List<MovieRecommend> filter(List<MovieRecommend> recallResult, Integer userId);

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
