package com.personalize.knowledge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class GraphResponse {
    private List<NodeInfo> nodes;
    private List<RelationshipInfo> relationships;
}
