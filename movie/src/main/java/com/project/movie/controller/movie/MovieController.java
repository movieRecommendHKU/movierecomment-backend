package com.project.movie.controller.movie;

import com.github.pagehelper.PageInfo;
import com.project.movie.domain.VO.MovieVO;
import com.project.movie.domain.response.BaseResponse;
import com.project.movie.service.movie.MovieService;
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
									  @RequestParam String orderBy) {
		PageInfo<MovieVO> res = movieService.getMovieCompleteList(pageNum, pageSize, orderBy);
		return res == null ? BaseResponse.error("Get Movies Failure! ")
				: BaseResponse.success("Get movies success", res);
	}
}
