package com.project.movie.service.movie.base;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.VO.MovieVO;

public interface MovieService {

	PageInfo<MovieVO> getMovieCompleteList(int pageNum, int pageSize, String orderBy);
}
