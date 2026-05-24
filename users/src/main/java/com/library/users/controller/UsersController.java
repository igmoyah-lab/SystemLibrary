package com.library.users.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.users.client.BookClient;
import com.library.users.dto.ApiResponse;
import com.library.users.dto.BookDto;


@RestController
@RequestMapping("/api/users")
public class UsersController {

    final BookClient bookClient;

    public UsersController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse<List<BookDto>>> getAllBooks(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String title) {

        List<BookDto> books;
        boolean hasFilters = (category != null && !category.isBlank()) || (author != null && !author.isBlank())
                || (title != null && !title.isBlank());
        if (hasFilters) {
            books = bookClient.searchBooks(title, author, category);
        } else {
            books = bookClient.getAllBooks();
        }
        ApiResponse<List<BookDto>> response = new ApiResponse<>(true, books);
        return ResponseEntity.ok(response);

    }
}
