package com.project.officequiz.service;

import com.project.officequiz.entity.Question;
import com.project.officequiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class QuizService {

	private final QuestionRepository repository;

	public QuizService(QuestionRepository repository) {
		this.repository = repository;
	}

	public List<Question> getAllQuestions(){
		List<Integer> randomQuoteIds = repository.findRandomQuestionsIds();
		return repository.findQuoteWithQuoteIds(randomQuoteIds);
	}
	
	public Question insertQuestion(Question ques) {
		repository.save(ques);
		return ques;
	}
	
	

}
