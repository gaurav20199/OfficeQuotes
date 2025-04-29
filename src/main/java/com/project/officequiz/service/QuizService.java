package com.project.officequiz.service;

import com.project.officequiz.dto.LeaderboardEntry;
import com.project.officequiz.entity.Question;
import com.project.officequiz.entity.Quiz;
import com.project.officequiz.entity.Users;
import com.project.officequiz.exception.InvalidUserDetailsException;
import com.project.officequiz.repository.QuestionRepository;
import com.project.officequiz.repository.QuizRepository;
import com.project.officequiz.repository.UserManagementRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {

	private final UserManagementRepository userManagementRepository;

	private final QuestionRepository repository;
	private final QuizRepository quizRepository;

	public QuizService(UserManagementRepository userManagementRepository, QuestionRepository repository, QuizRepository quizRepository) {
		this.userManagementRepository = userManagementRepository;
		this.repository = repository;
		this.quizRepository = quizRepository;
	}

	public List<Question> getAllQuestions(){
		List<Integer> randomQuoteIds = repository.findRandomQuestionsIds();
		return repository.findQuoteWithQuoteIds(randomQuoteIds);
	}
	
	public Question insertQuestion(Question ques) {
		repository.save(ques);
		return ques;
	}


	@Transactional
	public Quiz completeQuiz(String userName, Short quizType, Integer seasonId,
							 String characterName, Integer score) {

		Users users = userManagementRepository.findUserByUserName(userName).orElseThrow(() -> new InvalidUserDetailsException(HttpStatus.BAD_REQUEST, "Invalid User Details"));
		Quiz quiz = Quiz.builder().users(users).
				quizType(quizType).seasonId(seasonId).characterName(characterName).score(score).completedAt(LocalDateTime.now()).build();

		return quizRepository.save(quiz);
	}

	public List<LeaderboardEntry> getLeaderboard(Short quizType, Integer seasonId) {
		List<Quiz> topQuizzes;

		if (quizType != null) {
			topQuizzes = quizRepository.findTopScoresByQuizType(quizType);
		} else if (seasonId != null) {
			topQuizzes = quizRepository.findTopScoresBySeason(seasonId);
		} else {
			topQuizzes = quizRepository.findTopScores();
		}

		List<LeaderboardEntry> leaderboard = new ArrayList<>();
		int rank = 1;

		for (Quiz quiz : topQuizzes) {
			LeaderboardEntry entry = new LeaderboardEntry(rank++,quiz.getUsers().getUserName(),quiz.getScore(),String.valueOf(quizType),quiz.getCompletedAt());
			leaderboard.add(entry);
		}

		return leaderboard;
	}
	
	

}
