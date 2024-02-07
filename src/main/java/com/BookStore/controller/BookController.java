package com.BookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.BookStore.model.Book;
import com.BookStore.model.MyBookList;
import com.BookStore.service.BookService;
import com.BookStore.service.MyBookService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class BookController {
	@Autowired
	private MyBookService myBookService;
	@Autowired
	private BookService service;
	
	@GetMapping("/")
	public String home(){
		return("home");
	}
	@GetMapping("/book_register")
	public String bookregister(){
		return("bookregister");
	}
	@GetMapping("/available_books")
	public ModelAndView getAllBooks(){
		List<Book>list=service.getAllBook();
		return new ModelAndView("booklist","book",list);
	}
	@PostMapping("/save")
	public String addBool(@ModelAttribute Book b) {
		service.save(b);
		return"redirect:/available_books";
	}
	
	@GetMapping("/my_books")
	public String getMyBooks(Model model) {
		List<MyBookList>list=myBookService.getAllMyBooks();
		model.addAttribute("book",list);
		return "myBooks";
		
	}
	
	@RequestMapping("/mylist/{id}")
	public String getMyList(@PathVariable("id") int  id) {
		Book b=service.getBookById(id);
		MyBookList mb=new MyBookList(b.getId(),b.getName(),b.getAuthor(),b.getPrice());
		myBookService.saveMyBook(mb);
		return "redirect:/my_books";
	}
	
	@RequestMapping("/editbook/{id}")
	public String editBook(@PathVariable int id,Model model) {
		Book b=service.getBookById(id);
		model.addAttribute("book",b);
		return "bookedit" ;
	}
	
	@RequestMapping("/deletebook/{id}")
	public String deletebook(@PathVariable int id) {
		service.deleteById(id); 
		return "redirect:/available_books" ;
	}
	
	
	 
	
}    
