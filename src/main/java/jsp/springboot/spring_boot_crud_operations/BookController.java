package jsp.springboot.spring_boot_crud_operations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping("/book")
	public String saveBook(@RequestBody Book book) {
		bookRepository.save(book);
		return "Book saved successfully";
	}
	
	@PostMapping("/book/all")
	public String saveBook(@RequestBody List<Book> books) {
		bookRepository.saveAll(books);
		return "All Books are saved successfully";
	}
	
	@GetMapping("/book")
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	@GetMapping("/book/{id}")
	public Book getById(@PathVariable Integer id) {
		Optional<Book> opt=bookRepository.findById(id);
		if(opt.isEmpty()) {
			return null;
		}
		else {
			return opt.get();
		}
	}
	
	@DeleteMapping("/book/{id}")
	public String deleteBook(@PathVariable Integer id) {
		Optional<Book> opt=bookRepository.findById(id);
		if(opt.isPresent()) {
			 bookRepository.delete(opt.get());
			 return "Book is Deleted Successfully";
		}
		else {
			return "Book is Not Found";
		}
	}
	
	@PutMapping("/book")
	public String updateBook(@RequestBody Book book) {
		//case:1
		if(book.getId()==null) {
			return "Id must be passed to updated a record";
		}
		
		Optional<Book> opt =bookRepository.findById(book.getId());
		
		//case:2
		if(opt.isPresent()) {
			bookRepository.save(book);
			return "Book record with Id : "+book.getId()+" updated";
		}
		else {
			return "Book record with Id : "+book.getId()+" does not exist";
		}
	}
	
	@PatchMapping("/book/{id}")
	public String updateBook(@PathVariable Integer id,@RequestBody Map<String,Object> data) {
		Optional<Book> opt=bookRepository.findById(id);
		if(opt.isPresent()) {
			Book book=opt.get();
			for (Map.Entry<String,Object> entry : data.entrySet()) {
				String key = entry.getKey();
				Object value=entry.getValue();
				
				switch (key) {
				case "title":book.setTitle((String) value);
					break;
				case "author":book.setAuthor((String) value);
				 	break;
				case "genre":book.setGenre((String) value);
					break;
				case "price":book.setPrice((double) value);
					break;
				case "publishedYear":book.setPublishedear((Integer) value);
					break;
				case "availability":book.setAvailability((Boolean) value);
					break;
				}
			}
			bookRepository.save(book);
			return "Book record with Id : "+id+" updated";
		}
		else {
			return "the particlar record is not exist in the database";
		}
	}
	
}
