package com.project.officequiz.utils;

import com.project.officequiz.dto.OptionsDTO;
import com.project.officequiz.dto.QuoteDTO;
import com.project.officequiz.dto.UserDTO;
import com.project.officequiz.entity.Options;
import com.project.officequiz.entity.Quote;
import com.project.officequiz.entity.User;
import java.util.List;

public class ConversionUtil {
    public static QuoteDTO convertQuoteToQuoteDTO(Quote quote) {
        List<Options> options = quote.getOptions();
        List<OptionsDTO> optionsDTOs = options.stream().map(option -> convertOptionToOptionDTO(option)).toList();
        return new QuoteDTO(quote.getQuote(),optionsDTOs,quote.getAnswerOption());
    }
    public static OptionsDTO convertOptionToOptionDTO(Options option) {
        return new OptionsDTO(option.getOptionText(),option.getOptionOrder());
    }

    public static User convertUserDTOToUser(UserDTO userDTO,String encodedPassword) {
        User user = new User();
        user.setUserName(userDTO.userName());
        user.setEmail(userDTO.email());
        user.setPassword(encodedPassword);
        return user;

    }
}
