package jsp.springboot.spring_boot_crud_operations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseStructure<Book> saveBook(@RequestBody Book book) {
		ResponseStructure<Book> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Book record saved sccessflly");
		res.setData(bookRepository.save(book));
		return res;
	}
	
	@PostMapping("/book/all")
	public ResponseStructure<List<Book>> saveBook(@RequestBody List<Book> books) {
		ResponseStructure<List<Book>> res=new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("All Book records are saved sccessflly");
		res.setData(bookRepository.saveAll(books));
		return res;
	}
	
	@GetMapping("/book")
	public ResponseStructure<List<Book>> getAllBooks(){
		ResponseStructure<List<Book>> res=new ResponseStructure<>();
		List<Book> books=bookRepository.findAll();
		if(!books.isEmpty()) {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("All the Book records");
			res.setData(books);
		}else {
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage("books are not fetched");
		}
		return res;
	}
	
	@GetMapping("/book/{id}")
	public ResponseStructure<Book> getById(@PathVariable Integer id) {
		ResponseStructure<Book> res=new ResponseStructure<>();
		Optional<Book> opt=bookRepository.findById(id);
		if(opt.isEmpty()) {
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage("there is no record with this id: "+id);
			return res;
		}
		else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Get the record as per the id"+id);
			res.setData(bookRepository.findById(id).get());
			return res;
		}
	}
	
	@DeleteMapping("/book/{id}")
	public ResponseStructure<String> deleteBook(@PathVariable Integer id) {
		Optional<Book> opt=bookRepository.findById(id);
		ResponseStructure<String> res=new ResponseStructure<>();
		if(opt.isPresent()) {
			 res.setStatusCode(HttpStatus.OK.value());
			 res.setMessage("Book is Deleted Successfully with the repected id: "+id);
			 bookRepository.delete(opt.get());
			 res.setData("Success");
			 return res;
		}
		else {
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage("Book is not found in this id: "+id);
			return res;
		}
	}
	
	@PutMapping("/book")
	public ResponseStructure<String> updateBook(@RequestBody Book book) {
		ResponseStructure<String> res=new ResponseStructure<>();
		//case:1
		if(book.getId()==null) {
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage("Book record was not found in this id: "+book.getId());
			return res;
		}
		
		Optional<Book> opt =bookRepository.findById(book.getId());
		
		//case:2
		if(opt.isPresent()) {
			 res.setStatusCode(HttpStatus.OK.value());
			 res.setMessage("Book record with Id : "+book.getId()+" updated");
			 bookRepository.save(book);
			 res.setData("Success");
			return res;
		}
		else {
			res.setData("Failed");
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			 res.setMessage("Book record with Id : "+book.getId()+" does not exist");
			return res;
		}
	}
	
	@PatchMapping("/book/{id}")
	public ResponseStructure<String> updateBook(@PathVariable Integer id,@RequestBody Map<String,Object> data) {
		Optional<Book> opt=bookRepository.findById(id);
		ResponseStructure<String> res=new ResponseStructure<>();
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
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Book record with Id : "+id+" updated");
			bookRepository.save(book);
			res.setData("Success");
			return res;
		}
		else {
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage("the particlar record is not exist in the database");
			res.setData("Failed");
			return res;
		}
	}
	
}
