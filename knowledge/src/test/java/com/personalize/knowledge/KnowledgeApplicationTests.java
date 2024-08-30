package com.personalize.knowledge;

import com.personalize.knowledge.nebula.NebulaHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KnowledgeApplicationTests {
    @Autowired
    private NebulaHelper nebulaHelper;
    @Test
    void test1(){
        System.out.println("111");
        nebulaHelper.graphSearch();
    }
}
