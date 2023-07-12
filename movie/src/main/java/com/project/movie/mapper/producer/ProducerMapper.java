package com.project.movie.mapper.producer;

import com.project.movie.domain.DO.Producer;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerMapper {

	Producer getProducerByMovie(int producerId);
}
