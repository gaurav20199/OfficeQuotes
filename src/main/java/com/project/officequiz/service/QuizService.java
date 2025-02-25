package com.project.officequiz.service;

import com.project.officequiz.dao.QuoteRepository;
import com.project.officequiz.entity.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class QuizService {

	private final QuoteRepository repository;

	public QuizService(QuoteRepository repository) {
		this.repository = repository;
	}

	public List<Quote> getAllQuotes(){
		List<Integer> randomQuoteIds = repository.findRandomQuestionsIds();
		return repository.findQuoteWithQuoteIds(randomQuoteIds);
	}
	
	public Quote insertQuestion(Quote ques) {
		repository.save(ques);
		return ques;
	}
	
	

}
