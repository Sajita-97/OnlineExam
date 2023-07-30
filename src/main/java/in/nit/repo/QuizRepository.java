package in.nit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nit.model.exam.Category;
import in.nit.model.exam.Quiz;

public interface QuizRepository extends JpaRepository<Quiz ,Long> {
  public List<Quiz>findBycategory(Category category);
  public List<Quiz>findByActive(Boolean b);
  public List<Quiz>findByCategoryAndActive(Category c,Boolean b);
}
