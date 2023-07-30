package in.nit.service;

import java.util.Set;

import in.nit.model.exam.Questions;
import in.nit.model.exam.Quiz;

public interface QuestionsService {
	public Questions addQuestions(Questions question);

	public Questions updateQuestions(Questions question);

	public Set<Questions> getQuestions();

	public Questions getQuestion(Long questionId);

	public Set<Questions> getQuestionsOfQuiz(Quiz quiz);
	
	public void deleteQuestion(Long quesId);
	public Questions get(Long questionsId);
}
