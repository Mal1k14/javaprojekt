package com.sport.sportsgroups;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.liquibase.enabled=false",  // Отключаем Liquibase для тестов
		"spring.jpa.hibernate.ddl-auto=create-drop"  // Создаем таблицы автоматически
})
class SportsGroupsApplicationTests {

	@Test
	void contextLoads() {
		// Тест просто проверяет, что приложение запускается
	}
}