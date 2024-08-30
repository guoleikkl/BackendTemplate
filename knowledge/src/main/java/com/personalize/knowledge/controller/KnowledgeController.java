package com.personalize.knowledge.controller;

import com.personalize.common.entity.R;
import com.personalize.knowledge.entity.GraphResponse;
import com.personalize.knowledge.nebula.NebulaHelper;
import com.personalize.knowledge.service.IKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * @author zheng
 */
@RestController
@RequestMapping("/nebula")
public class KnowledgeController {
    @Autowired
    private NebulaHelper nebulaHelper;
    @Autowired
    private IKnowledgeService knowledgeService;
    @GetMapping("/scienceKnowledge")
    public R<GraphResponse> scienceKnowledge(){
        R<GraphResponse> res = nebulaHelper.graphSearch();
        return res;
    }
    @GetMapping("/book/{book}")
    public R<GraphResponse> book(@PathVariable(value = "book")String book){
        book = URLDecoder.decode(book);
        R<GraphResponse> res = nebulaHelper.bookModelGraph(book);
        return res;
    }
    @GetMapping("/movie/{movie}")
    public R<GraphResponse> movie(@PathVariable(value = "movie")String movie){
        movie = URLDecoder.decode(movie);
        R<GraphResponse> res = nebulaHelper.movieModelGraph(movie);
        return res;
    }
    @GetMapping("/domain/{domain}")
    public R<GraphResponse> domain(@PathVariable(value = "domain")String domain){
        domain = URLDecoder.decode(domain);
        R<GraphResponse> res = nebulaHelper.domainModelGraph(domain);
        return res;
    }
    /**
     * 节点查询
     */
    @GetMapping("/knowledge/{knowledgeName}")
    public R<GraphResponse> getKnowledge(@PathVariable("knowledgeName")String knowledgeName){
        knowledgeName = URLDecoder.decode(knowledgeName);
        R<GraphResponse> res = nebulaHelper.getKnowledgePoint(knowledgeName);
        return res;
    }
    @GetMapping("/knowledge/click/{knowledgeId}")
    public void clickKnowledge(@PathVariable("knowledgeId")String knowledgeId, HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        knowledgeService.click(knowledgeId,authorization);
    }
}
