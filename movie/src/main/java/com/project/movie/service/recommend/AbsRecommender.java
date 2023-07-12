package com.project.movie.service.recommend;

import java.util.HashMap;
import java.util.List;

public abstract class AbsRecommender {
//	HashMap<String, AbsRecommender> register;

	public List<Integer> getAll(Integer userId) {
		return null;
	}

	public List<Integer> getOne(Integer userId) {
		return null;
	}

	private void setPolicy() {}

	// TODO: protected or private ?
	protected List<Object> recall(Integer userId) {
		return null;
	}

	private List<Object> filter(List<Object> recallResult) {
		return null;
	}

	private List<Object> sort(List<Object> filterResult) {
		return null;
	}

	private List<Integer> resort(List<Object> sortResult) {
		return null;
	}

	private List<Integer> startFlow() {
		// recall
		// filter
		// sort
		// resort
		return null;
	}

}
