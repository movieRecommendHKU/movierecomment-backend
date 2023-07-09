package com.project.movie.controller.search;

import com.project.movie.service.search.ParseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class MovieSearchController {

    @GetMapping("/searchMovie")
    public boolean searchMovie(){
        return true;
    }
}
