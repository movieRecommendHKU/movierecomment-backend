package com.project.movie.service.movie.base;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.VO.MovieVO;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {

	PageInfo<MovieVO> getMovieCompleteList(int pageNum, int pageSize, String orderBy);
}
