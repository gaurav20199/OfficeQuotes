package com.project.officequiz.service;

import com.project.officequiz.dao.QuoteRepository;
import com.project.officequiz.entity.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuizService {
	
	@Autowired
	QuoteRepository repository;

	
	
	public List<Quote> getAllQuotes(){
		return repository.findRandomQuestions();
	}
	
	public Quote insertQuestion(Quote ques) {
		repository.save(ques);
		return ques;
	}
	
	

}
