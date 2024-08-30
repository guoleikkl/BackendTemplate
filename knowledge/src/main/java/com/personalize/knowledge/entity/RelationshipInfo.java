package com.personalize.knowledge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RelationshipInfo {
    private String source;
    private String target;
    private String relationName;
    private String properties;
    private String edge;

}
