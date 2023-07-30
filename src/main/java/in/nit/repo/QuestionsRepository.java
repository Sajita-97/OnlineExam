package in.nit.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nit.model.exam.Questions;
import in.nit.model.exam.Quiz;

public interface QuestionsRepository extends JpaRepository<Questions ,Long> {

	Set<Questions> findByQuiz(Quiz quiz);

}
