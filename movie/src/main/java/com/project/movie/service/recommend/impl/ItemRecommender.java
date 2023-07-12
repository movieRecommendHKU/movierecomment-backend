package com.project.movie.service.recommend.impl;

import com.project.movie.service.recommend.AbsRecommender;

import java.util.List;

public class ItemRecommender extends AbsRecommender {
	@Override
	protected List<Object> recall(Integer userId) {
		return null;
	}
}
