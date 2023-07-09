package com.project.movie.controller.movie.base;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.base.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("/list")
	BaseResponse getCompleteMovieList(@RequestParam(defaultValue = "1") int pageNum,
									  @RequestParam(defaultValue = "10") int pageSize,
									  @RequestParam(required = false) String orderBy) {
		PageInfo<MovieVO> res = movieService.getMovieCompleteList(pageNum, pageSize, orderBy);
		return res == null ? new BaseResponse().setStatus(false).setMsg("Get Movies Failure! ")
				: new BaseResponse().setStatus(true).setMsg("Get movies success").setContent(res);
	}
}
