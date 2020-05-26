package com.feng;

import com.feng.dao.TypeRepository;
import com.feng.po.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTests {
	@Autowired
	private TypeRepository typeRepository;
	@Test
	void contextLoads() {
		Type T = typeRepository.findByName("MYSQL");
		System.out.println(T);
	}

}
