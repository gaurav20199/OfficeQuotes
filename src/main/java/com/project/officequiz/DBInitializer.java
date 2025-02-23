package com.project.officequiz;


import com.project.officequiz.dao.QuoteRepository;
import com.project.officequiz.entity.Options;
import com.project.officequiz.entity.Quote;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBInitializer implements CommandLineRunner {
    private final QuoteRepository quoteRepository;

    public DBInitializer(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    private Options createOption(String optionText, int optionOrder, Quote quote) {
        Options option = new Options();
        option.setOptionText(optionText);
        option.setOptionOrder(optionOrder);
        option.setQuote(quote);
        return option;
    }

    @Override
    public void run(String... args) throws Exception {

        Quote quote = new Quote();
        quote.setQuote("I'm an early bird and a night owl. So I'm wise and I have worms.");
        List<Options> optionsForQuote1 = new ArrayList<>();
        optionsForQuote1.add(createOption(AppConstants.CHARACTER_DWIGHT,1,quote));
        optionsForQuote1.add(createOption(AppConstants.CHARACTER_RYAN,2,quote));
        optionsForQuote1.add(createOption(AppConstants.CHARACTER_MICHAEL,3,quote));
        optionsForQuote1.add(createOption(AppConstants.CHARACTER_STANLEY,4,quote));
        quote.setAnswerOption(3);
        quote.setOptions(optionsForQuote1);


        Quote quote2 = new Quote();
        quote2.setQuote("I am a black belt in gift wrapping");
        List<Options> optionsForQuote2 = new ArrayList<>();
        optionsForQuote2.add(createOption(AppConstants.CHARACTER_MICHAEL,1,quote2));
        optionsForQuote2.add(createOption(AppConstants.CHARACTER_JIM,2,quote2));
        optionsForQuote2.add(createOption(AppConstants.CHARACTER_PAM,3,quote2));
        optionsForQuote2.add(createOption(AppConstants.CHARACTER_ANGELA,4,quote2));
        quote2.setAnswerOption(2);
        quote2.setOptions(optionsForQuote2);

        Quote quote3 = new Quote();
        quote3.setQuote("I'm going to be the best husband, the best father... and the best CEO of my company.");
        List<Options> optionsForQuote3 = new ArrayList<>();
        optionsForQuote3.add(createOption(AppConstants.CHARACTER_CREED,1,quote3));
        optionsForQuote3.add(createOption(AppConstants.CHARACTER_DWIGHT,2,quote3));
        optionsForQuote3.add(createOption(AppConstants.CHARACTER_RYAN,3,quote3));
        optionsForQuote3.add(createOption(AppConstants.CHARACTER_STANLEY,4,quote3));
        quote3.setAnswerOption(3);
        quote3.setOptions(optionsForQuote3);

        Quote quote4 = new Quote();
        quote4.setQuote("Today's halloween? That's really good timing. Really good");
        List<Options> optionsForQuote4 = new ArrayList<>();
        optionsForQuote4.add(createOption(AppConstants.CHARACTER_MICHAEL,1,quote4));
        optionsForQuote4.add(createOption(AppConstants.CHARACTER_DWIGHT,2,quote4));
        optionsForQuote4.add(createOption(AppConstants.CHARACTER_KEVIN,3,quote4));
        optionsForQuote4.add(createOption(AppConstants.CHARACTER_CREED,4,quote4));
        quote4.setAnswerOption(4);
        quote4.setOptions(optionsForQuote4);

        Quote quote5 = new Quote();
        quote5.setQuote("I am so good at my job, it’s ridiculous.");
        List<Options> optionsForQuote5 = new ArrayList<>();
        optionsForQuote5.add(createOption(AppConstants.CHARACTER_MICHAEL,1,quote5));
        optionsForQuote5.add(createOption(AppConstants.CHARACTER_DWIGHT,2,quote5));
        optionsForQuote5.add(createOption(AppConstants.CHARACTER_ANGELA,3,quote5));
        optionsForQuote5.add(createOption(AppConstants.CHARACTER_STANLEY,4,quote5));
        quote5.setAnswerOption(1);
        quote5.setOptions(optionsForQuote5);

        quoteRepository.save(quote);
        quoteRepository.save(quote2);
        quoteRepository.save(quote3);
        quoteRepository.save(quote4);
        quoteRepository.save(quote5);

    }
}
