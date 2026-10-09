package jsp.springboot.spring_boot_crud_operations;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>{

}
