package com.personalize.knowledge.service;

public interface IKnowledgeService {
    public void click(String knowledgeId,String authorization);
    public int createUserKnowledgeRecord(String knowledgeId,String authorization);
}
