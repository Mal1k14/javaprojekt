package com.sport.sportsgroups.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration  // ← Говорит Spring: "Это файл настроек"
public class RequestLoggingConfig {

    @Bean  // ← Создаем объект и передаем управление Spring'у
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();

        // Что логировать:
        loggingFilter.setIncludeClientInfo(true);   // IP клиента
        loggingFilter.setIncludeQueryString(true);  // Параметры URL (?id=5&name=Иван)
        loggingFilter.setIncludePayload(true);      // Тело запроса (JSON)
        loggingFilter.setIncludeHeaders(false);     // Заголовки (не включаем, чтобы не засорять)
        loggingFilter.setMaxPayloadLength(1000);    // Макс. длина тела (символов)
        loggingFilter.setAfterMessagePrefix("REQUEST: ");  // Префикс для лога

        return loggingFilter;
    }
}