package in.nit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nit.model.exam.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
