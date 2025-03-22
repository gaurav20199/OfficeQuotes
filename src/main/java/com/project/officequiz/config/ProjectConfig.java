package com.project.officequiz.config;

import com.project.officequiz.entity.OfficeCharacter;
import com.project.officequiz.entity.Question;
import com.project.officequiz.repository.CharacterRepository;
import com.project.officequiz.repository.QuestionRepository;
import com.project.officequiz.service.ExcelImportService;
import com.project.officequiz.utils.mappers.CharacterMapper;
import com.project.officequiz.utils.mappers.QuestionMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProjectConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean("questionImportService")
    public ExcelImportService<Question> questionImportService(
            @Qualifier("questionRepository") QuestionRepository questionRepository,
            QuestionMapper questionMapper) {
        return new ExcelImportService<>(questionRepository, questionMapper);
    }

    @Bean("characterImportService")
    public ExcelImportService<OfficeCharacter> characterImportService(
            @Qualifier("characterRepository") CharacterRepository characterRepository,
            CharacterMapper characterMapper) {
        return new ExcelImportService<>(characterRepository, characterMapper);
    }
}
