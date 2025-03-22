package com.project.officequiz.utils;

import com.project.officequiz.dto.EpisodeDTO;
import com.project.officequiz.dto.OptionsDTO;
import com.project.officequiz.dto.QuestionDTO;
import com.project.officequiz.dto.QuoteDTO;
import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.entity.Episode;
import com.project.officequiz.entity.Option;
import com.project.officequiz.entity.Question;
import com.project.officequiz.entity.Season;
import com.project.officequiz.entity.User;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConversionUtil {
    public static QuestionDTO convertQuestionToQuestionDTO(Question question) {
        Set<Option> options = question.getOptions();
        Set<OptionsDTO> optionsDTOs = options.stream().map(option -> convertOptionToOptionDTO(option)).collect(Collectors.toSet());
        return new QuestionDTO(question.getQuote(),optionsDTOs);
    }
    public static OptionsDTO convertOptionToOptionDTO(Option option) {
        return new OptionsDTO(option.getText(),option.isCorrect());
    }

    public static User convertUserDTOToUser(UserDTO userDTO,String encodedPassword) {
        User user = new User();
        user.setUserName(userDTO.userName());
        user.setEmail(userDTO.email());
        user.setPassword(encodedPassword);
        return user;

    }

    public static Season convertToSeason(short number,double avgRating, Date releasedDate) {
        LocalDate localDate = releasedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return Season.builder().number(number).
                avgRating(avgRating).
                releaseDate(localDate).
                build();

    }

    public static Episode convertToEpisode(EpisodeDTO episodeDTO) {
        LocalDate localDate = episodeDTO.releaseDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return Episode.builder().
                title(episodeDTO.title()).
                description(episodeDTO.description()).
                number(episodeDTO.episodeNo()).
                imdbRating(episodeDTO.imdbRating()).
                directedBy(episodeDTO.directedBy()).
                totalVotes(episodeDTO.totalVotes()).
                releaseDate(localDate).
                writtenBy(episodeDTO.writtenBy()).
                build();
    }
}
