package com.personalize.knowledge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NodeInfo {
    private String id;
    private String tag;
    private String name;
    private String movie;
    private String domain;
    private String book;
    private String url;
}
