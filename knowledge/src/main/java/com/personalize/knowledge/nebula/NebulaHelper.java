package com.personalize.knowledge.nebula;

import com.personalize.common.entity.R;
import com.personalize.knowledge.entity.GraphResponse;
import com.personalize.knowledge.entity.NodeInfo;
import com.personalize.knowledge.entity.RelationshipInfo;
import com.vesoft.nebula.client.graph.SessionPool;
import com.vesoft.nebula.client.graph.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Component
public class NebulaHelper {
    private static final Logger log = LoggerFactory.getLogger(NebulaHelper.class);
    @Resource
    private SessionPool sessionPool;
    /*
    查询某个领域的知识图
     */
    public R<GraphResponse> domainModelGraph(String domain){
        GraphResponse  graphResponse;
        if (!sessionPool.init()) {
            log.error("session pool init failed.");
            return R.fail("session pool init failed.");
        }
        try {
            String stmt = String.format("match (v:KnowledgePoint) where properties(v).domain==\"%s\" return v",domain);
            ResultSet resultSet = sessionPool.execute(stmt);
            graphResponse = makeDomainGraph(domain,resultSet);
            return R.success(graphResponse);
        }catch (Exception e){
            log.error("Failed to execute query:", e);
        }
        return R.fail("不存在此书籍的知识点");
    }
    public GraphResponse makeDomainGraph(String domain,ResultSet resultSet) throws UnsupportedEncodingException {
        List<NodeInfo> nodes = new ArrayList<>();
        List<RelationshipInfo> relationshipInfos = new ArrayList<>();
        for (int row_index = 0 ;row_index<resultSet.rowsSize();row_index++){
            Node node = resultSet.rowValues(row_index).get(0).asNode();
            NodeInfo nodeInfo = insertNode(node);
            nodes.add(nodeInfo);
        }
        NodeInfo mainNode = new NodeInfo("\"0\"", "Domain", domain, "", "domain","" , "");
        nodes.add(mainNode);
        for (NodeInfo nodeInfo:nodes){
            RelationshipInfo rel = new RelationshipInfo(mainNode.getId(), nodeInfo.getId(), "subclass", domain, "subclass");
            relationshipInfos.add(rel);
        }
        return new GraphResponse(nodes,relationshipInfos);
    }
    /*
    查询某部电影的知识图
     */
    public R<GraphResponse> movieModelGraph(String movie){
        GraphResponse  graphResponse;
        if (!sessionPool.init()) {
            log.error("session pool init failed.");
            return R.fail("session pool init failed.");
        }
        try {
            String stmt = String.format("match (v:KnowledgePoint) where properties(v).movie==\"%s\" return v",movie);
            ResultSet resultSet = sessionPool.execute(stmt);
            graphResponse = makeMovieGraph(movie,resultSet);
            return R.success(graphResponse);
        }catch (Exception e){
            log.error("Failed to execute query:", e);
        }
        return R.fail("不存在此书籍的知识点");
    }
    public GraphResponse makeMovieGraph(String movie,ResultSet resultSet) throws UnsupportedEncodingException {
        List<NodeInfo> nodes = new ArrayList<>();
        List<RelationshipInfo> relationshipInfos = new ArrayList<>();
        for (int row_index = 0 ;row_index<resultSet.rowsSize();row_index++){
            Node node = resultSet.rowValues(row_index).get(0).asNode();
            NodeInfo nodeInfo = insertNode(node);
            nodes.add(nodeInfo);
        }
        NodeInfo mainNode = new NodeInfo("\"0\"", "Movie","", movie, "", movie, "");
        nodes.add(mainNode);
        for (NodeInfo nodeInfo:nodes){
            RelationshipInfo rel = new RelationshipInfo(mainNode.getId(), nodeInfo.getId(), "subclass", movie, "subclass");
            relationshipInfos.add(rel);
        }
        return new GraphResponse(nodes,relationshipInfos);
    }
    /*
    查询某本书的知识图
     */
    public R<GraphResponse> bookModelGraph(String book){
        GraphResponse  graphResponse;
        if (!sessionPool.init()) {
            log.error("session pool init failed.");
            return R.fail("session pool init failed.");
        }
        try {
            String stmt = String.format("match (v:KnowledgePoint) where properties(v).book==\"%s\" return v",book);
            ResultSet resultSet = sessionPool.execute(stmt);
            graphResponse = makeBookGraph(book,resultSet);
            return R.success(graphResponse);
        }catch (Exception e){
            log.error("Failed to execute query:", e);
        }
        return R.fail("不存在此书籍的知识点");
    }
    public GraphResponse makeBookGraph(String book,ResultSet resultSet) throws UnsupportedEncodingException {
         List<NodeInfo> nodes = new ArrayList<>();
         List<RelationshipInfo> relationshipInfos = new ArrayList<>();
         for (int row_index = 0 ;row_index<resultSet.rowsSize();row_index++){
             Node node = resultSet.rowValues(row_index).get(0).asNode();
             NodeInfo nodeInfo = insertNode(node);
             nodes.add(nodeInfo);
         }
        NodeInfo mainNode = new NodeInfo("\"0\"", "Book", book, "", "", book, "");
         nodes.add(mainNode);
        for (NodeInfo nodeInfo:nodes){
            RelationshipInfo rel = new RelationshipInfo(mainNode.getId(), nodeInfo.getId(), "subclass", book, "subclass");
            relationshipInfos.add(rel);
        }
         return new GraphResponse(nodes,relationshipInfos);
    }

    /*
    查询整个知识图
     */
    public R<GraphResponse> graphSearch(){
        GraphResponse graphResponse;
        if (!sessionPool.init()) {
            log.error("session pool init failed.");
            return R.fail("session pool init failed.");
        }
        ResultSet resultSet;
        try {
            resultSet = sessionPool.execute("match (n)-[r]->(m) return n,r,m;");
            graphResponse = makeGraphResponse(resultSet);
            return R.success(graphResponse);
        } catch (Exception e) {
            log.error("Failed to execute query:", e);
        }
        return R.fail("知识图中暂无数据");
    }
    public NodeInfo insertNode(Node node) throws UnsupportedEncodingException {
        String id = node.getId().toString();
        String type = node.tagNames().get(0).toString();
        String book = node.properties(type).get("book").toString();
        String movie = node.properties(type).get("movie").toString();
        String domain = node.properties(type).get("domain").toString();
        String name = node.properties(type).get("name").toString();
        String url = "https://www.kepuchina.cn/article/articleinfo?business_type=100&classify=2&ar_id=439400";
        NodeInfo nodeInfo = new NodeInfo(id, type, name, movie, domain, book,url);
        return nodeInfo;
    }
    public GraphResponse makeGraphResponse(ResultSet resp) throws UnsupportedEncodingException {
        List<NodeInfo> nodes = new ArrayList<>();
        HashSet<String> isVisit = new HashSet<>();
        List<RelationshipInfo> relationshipInfos = new ArrayList<>();
        for (int row_index = 0;row_index<resp.rowsSize();row_index++){
            Node endNode = resp.rowValues(row_index).get(0).asNode();
            Relationship relationship = resp.rowValues(row_index).get(1).asRelationship();
            Node startNode = resp.rowValues(row_index).get(2).asNode();
            String startId = startNode.getId().toString();
            String endId = endNode.getId().toString();
            if (!isVisit.contains(startId)){
                isVisit.add(startId);
                NodeInfo startNodeInfo = insertNode(startNode);
                nodes.add(startNodeInfo);
            }
            if (!isVisit.contains(endId)){
                isVisit.add(endId);
                NodeInfo endNodeInfo = insertNode(endNode);
                nodes.add(endNodeInfo);
            }
            String relationProperties = relationship.properties().get("name").toString();
            RelationshipInfo relationshipInfo = new RelationshipInfo(startId,endId,relationship.edgeName(),relationProperties,relationship.edgeName());
            relationshipInfos.add(relationshipInfo);
        }
        return new GraphResponse(nodes,relationshipInfos);
    }

    public R<GraphResponse> getKnowledgePoint(String knowledgeName) {
        GraphResponse graphResponse;
        if (!sessionPool.init()) {
            log.error("session pool init failed.");
            return R.fail("session pool init failed.");
        }
        ResultSet resultSet;
        try {
            String stmt = String.format("match (v:KnowledgePoint) where properties(v).name==\"%s\" return v",knowledgeName);
            resultSet = sessionPool.execute(stmt);
            graphResponse = makeKnowledgeGraph(resultSet);
            return R.success(graphResponse);
        } catch (Exception e) {
            log.error("Failed to execute query:", e);
        }
        return R.fail("知识图中暂无数据");
    }
    public GraphResponse makeKnowledgeGraph(ResultSet resp) throws UnsupportedEncodingException {
        List<NodeInfo> nodes = new ArrayList<>();
        List<RelationshipInfo> relationshipInfos = new ArrayList<>();
        for (int row_index = 0;row_index<resp.rowsSize();row_index++){
            Node Node = resp.rowValues(row_index).get(0).asNode();
            NodeInfo startNodeInfo = insertNode(Node);
            nodes.add(startNodeInfo);
        }
        return new GraphResponse(nodes,relationshipInfos);
    }
}
