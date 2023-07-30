package in.nit.service.imp;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nit.model.exam.Questions;
import in.nit.model.exam.Quiz;
import in.nit.repo.QuestionsRepository;
import in.nit.service.QuestionsService;

@Service
public class QuestionsServiceImp implements QuestionsService {
	@Autowired
	private QuestionsRepository questionRepository;

	@Override
	public Questions addQuestions(Questions question) {
		
		return this.questionRepository.save(question);
	}

	@Override
	public Questions updateQuestions(Questions question) {
	
		return this.questionRepository.save(question);
	}

	@Override
	public Set<Questions> getQuestions() {
	
		return new HashSet<>(this.questionRepository.findAll());
	}

	@Override
	public Questions getQuestion(Long questionId) {

		return this.questionRepository.findById(questionId).get();
	}

	@Override
	public Set<Questions> getQuestionsOfQuiz(Quiz quiz) {
		
		return this.questionRepository.findByQuiz(quiz);
	}

	@Override
	public void deleteQuestion(Long quesId) {
		// TODO Auto-generated method stub
		Questions questions = new Questions();
		questions.setQuesId(quesId);
		this.questionRepository.delete(questions);
	}

	@Override
	public Questions get(Long questionsId) {
		// TODO Auto-generated method stub
		return this.questionRepository.getOne(questionsId);
	}

}
