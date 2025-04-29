package com.project.officequiz.controller;
import com.project.officequiz.dto.LeaderboardEntry;
import com.project.officequiz.dto.QuestionDTO;
import com.project.officequiz.dto.QuoteDTO;
import com.project.officequiz.dto.ScoreDTO;
import com.project.officequiz.entity.Question;
import com.project.officequiz.service.QuizService;
import com.project.officequiz.utils.ConversionUtil;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/quiz")
public class QuizController {
	@Autowired
	private final QuizService service;

	public QuizController(QuizService quizService) {
		this.service = quizService;
	}

	@GetMapping
	public String getQuiz() {
		return "quiz";
	}

	@GetMapping("/leaderboard")
	public String showLeaderBoardPage() {
		return "leaderboard";
	}

	@GetMapping("/leaderboard/data")
	@ResponseBody
	public List<LeaderboardEntry> getLeaderBoardData() {
		List<LeaderboardEntry> leaderboard = service.getLeaderboard((short) 0, 0);
		return leaderboard;
	}

	@GetMapping("/data")
	@ResponseBody
	public List<QuestionDTO> getQuizData() {
		List<Question> questions = service.getAllQuestions();
		List<QuestionDTO> questionDTOS = questions.stream().map(ConversionUtil::convertQuestionToQuestionDTO).toList();
		return questionDTOS;
	}

	@PostMapping("/submit")
	@ResponseBody
	public String submitQuiz(@RequestBody ScoreDTO scoreDTO, @AuthenticationPrincipal UserDetails userDetails) {
		service.completeQuiz(userDetails.getUsername(),(short)0,0,"",scoreDTO.getScore());
		return "Score submitted successfully";
	}
	
/*	@PostMapping(path="/insertQuestion",consumes= {"application/json"})  // We can use consume to only accept JSON
	@ResponseBody
	public Quote insertQuestion(@RequestBody Quote ques) {
		
		service.insertQuestion(ques);
		return ques;
	}*/
}
