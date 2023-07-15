package com.project.movie.domain.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GraphNode {
    List<String> labels;
    String type;
    Integer nodeId;
}
