package com.project.officequiz.utils.mappers;

import com.project.officequiz.entity.Episode;
import com.project.officequiz.entity.Option;
import com.project.officequiz.entity.Question;
import com.project.officequiz.service.EpisodeService;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class QuestionMapper implements Function<Row, Optional<Question>> {

    private static final String NOISE_REGEX = "\\[.*?\\]|\\.{2,}|[^\\w\\s'\".,!?]";
    private static final Integer MIN_WORD_COUNT = 5;

    private final EpisodeService episodeService;

    public QuestionMapper(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @Override
    public Optional<Question> apply(Row row) {
        if(row.getCell(0)==null || row.getCell(1)==null)
            return Optional.empty();
        String characterName = row.getCell(0).getStringCellValue();
        String quote = row.getCell(1).getStringCellValue();
        short seasonNumber = (short) row.getCell(2).getNumericCellValue();
        short episodeNumber = (short) row.getCell(3).getNumericCellValue();
        boolean isPopular = row.getCell(4) != null ? row.getCell(4).getBooleanCellValue() : false;

        Episode episode = episodeService.getEpisode(seasonNumber, episodeNumber);
        if (episode == null) {
            throw new IllegalArgumentException(
                    "Episode not found for Season: " + seasonNumber + ", Episode: " + episodeNumber
            );
        }

        quote = cleanQuote(quote);

        if(!isValidQuote(quote,isPopular))
            return Optional.empty();

        Question question = Question.builder()
                .quote(quote)
                .episode(episode).
                isPopular(isPopular).
                build();

        List<String> incorrectOptions = episodeService.fetchAllCharacters().stream()
                .filter(name -> !name.contains(characterName))
                .collect(Collectors.toList());
        Collections.shuffle(incorrectOptions);
        List<String> optionsExceptCorrectAns = incorrectOptions.stream()
                .limit(3)
                .collect(Collectors.toList());
        Set<Option> options = optionsExceptCorrectAns.stream().map(option -> createNewOption(option, question, false)).collect(Collectors.toSet());
        Option correctOption = createNewOption(characterName, question, true);
        options.add(correctOption);
        List<Option> shuffledOptions = new ArrayList<>(options);
        Collections.shuffle(shuffledOptions);
        question.setOptions(new HashSet<>(shuffledOptions));
        return Optional.of(question);
    }

    private String cleanQuote(String quote) {
        if (quote != null) {
            // Remove brackets, special symbols, and extra spaces
            quote = quote.replaceAll(NOISE_REGEX, "").trim();
            quote = quote.replaceAll("\\s+", " ");
        }
        return quote;
    }

    private boolean isValidQuote(String quote, boolean popularFlag) {
        if (quote == null || quote.isEmpty()) return false;

        int wordCount = quote.split("\\s+").length;

        return wordCount >= MIN_WORD_COUNT || (popularFlag);
    }

    private Option createNewOption(String optionText,Question question,boolean isCorrectOption) {
        return Option.builder().
                text(optionText).
                isCorrect(isCorrectOption).
                question(question).
                build();
    }

}
