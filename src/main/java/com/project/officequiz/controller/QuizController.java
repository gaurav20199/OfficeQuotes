package com.project.officequiz.controller;
import com.project.officequiz.dto.QuoteDTO;
import com.project.officequiz.entity.Quote;
import com.project.officequiz.service.QuizService;
import com.project.officequiz.utils.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
public class QuizController {
	@Autowired
	private final QuizService service;

	public QuizController(QuizService quizService) {
		this.service = quizService;
	}

	@GetMapping("/quiz")
	public String getQuiz() {
		return "quiz";
	}

	@GetMapping("/quiz/data")
	@ResponseBody
	public List<QuoteDTO> getQuizData() {
		List<Quote> quotes = service.getAllQuotes();
		List<QuoteDTO> quoteDTOS = quotes.stream().map(ConversionUtil::convertQuoteToQuoteDTO).toList();
		return quoteDTOS;
	}

	@PostMapping("/submitQuiz")
	public String submitQuiz(@RequestParam Map<String, String> userAnswers, Model model) {
		List<Quote> quotes = service.getAllQuotes();
		int score = 0;
		for (int i = 0; i < quotes.size(); i++) {
			int correctAnswer = quotes.get(i).getAnswerOption();
			String userAnswer = userAnswers.get("question_" + i);
			if (userAnswer != null && Integer.parseInt(userAnswer) == correctAnswer) {
				score++;
			}
		}
		model.addAttribute("score", score);
		return "quiz";
	}
	
	@PostMapping(path="/insertQuestion",consumes= {"application/json"})  // We can use consume to only accept JSON
	@ResponseBody
	public Quote insertQuestion(@RequestBody Quote ques) {
		
		service.insertQuestion(ques);
		return ques;
	}
}
