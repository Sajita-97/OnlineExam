package in.nit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.nit.model.exam.Questions;
import in.nit.model.exam.Quiz;
import in.nit.service.QuestionsService;
import in.nit.service.QuizService;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionsController {
	@Autowired
	private QuestionsService questionService;
	@Autowired
	private QuizService quizService;

	// add question
	@PostMapping("/")
	public ResponseEntity<Questions> add(@RequestBody Questions question) {
		return ResponseEntity.ok(this.questionService.addQuestions(question));
	}

	// update
	@PutMapping("/")
	public ResponseEntity<Questions> update(@RequestBody Questions question) {
		return ResponseEntity.ok(this.questionService.updateQuestions(question));
	}

	// get all question of any quiz
	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable("qid") Long qid) {
		/*
		 * it gives all question Quiz quiz = new Quiz(); quiz.setQid(qid);
		 * Set<Questions> questionsOfQuiz =
		 * this.questionService.getQuestionsOfQuiz(quiz); return
		 * ResponseEntity.ok(questionsOfQuiz);
		 */

		Quiz quiz = this.quizService.getQuiz(qid);
		Set<Questions> question = quiz.getQuestion();

		List<Questions> list = new ArrayList<>(question);
		if (list.size() > Integer.parseInt(quiz.getNumberOfQuestions())) {
			list = list.subList(0, Integer.parseInt(quiz.getNumberOfQuestions() + 1));
		}
		list.forEach((q) -> {
			q.setAnswer("");
		});
		// randomly question
		Collections.shuffle(list);
		return ResponseEntity.ok(list);
	}

	@GetMapping("/quiz/all/{qid}")
	public ResponseEntity<?> getQuestionsOfQuizAdmin(@PathVariable("qid") Long qid) {

		Quiz quiz = new Quiz();
		quiz.setQid(qid);
		Set<Questions> questionsOfQuiz = this.questionService.getQuestionsOfQuiz(quiz);
		return ResponseEntity.ok(questionsOfQuiz);

	}

	// get single questions
	@GetMapping("/{quesId}")
	public Questions get(@PathVariable("quesId") Long quesId) {
		return this.questionService.getQuestion(quesId);
	}

	// delete single question
	@DeleteMapping("/{quesId}")
	public void delete(@PathVariable("quesId") Long quesId) {
		this.questionService.deleteQuestion(quesId);

	}

	@PostMapping("/eval-quiz")
	public ResponseEntity<?> evalQuiz(@RequestBody List<Questions> questions) {
		System.out.println(questions);
		double marksGot = 0;
		Integer correctAnswer = 0;
		Integer attempted = 0;
		for (Questions q : questions) {
// single questions
			Questions question = this.questionService.get(q.getQuesId());
			if (question.getAnswer().equals(q.getGivenAnswer())) {
				// correct
				correctAnswer++;
				Double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks()) / questions.size();

				marksGot += marksSingle;
			}
			if (q.getGivenAnswer() != null) {
				attempted++;
			}
		}
		Map<String, Object> map = Map.of("marksGot", marksGot, " correctAnswer", correctAnswer, " attempted",
				attempted);
		return ResponseEntity.ok(map);
	}
};