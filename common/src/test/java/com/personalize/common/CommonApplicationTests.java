package com.personalize.common;

import com.personalize.common.utils.id.IdGenerate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommonApplicationTests {
	@Autowired
	private IdGenerate<Long> idGenerate;
	@Test
	void contextLoads() {
		Long generate = idGenerate.generate();
		System.out.println(generate);
	}

}
