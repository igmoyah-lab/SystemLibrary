package com.library.users.client;

import java.util.List;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.library.users.dto.BookDto;
@Component
public class BookClientImpl implements BookClient {

    private final RestClient restClient;

    public BookClientImpl(@Value("${book.service.url}") String baseurl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseurl)
                .build();
    }

    @Override
    public List<BookDto> getAllBooks() {

        ResponseEntity<List<BookDto>> response = restClient.get()
                .uri("/api/books")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<BookDto>>() {
                });

        List<BookDto> body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body;
        } else {
            throw new RuntimeException("Failed to fetch books: " + status);
        }
    }



    @Override
    public List<BookDto> searchBooks(String tittle, String author, String category) {
        
        ResponseEntity<List<BookDto>> response = restClient.get()
                .uri(uriBuilder -> {
                        var builder = uriBuilder.path("/api/books/search");

                        if (tittle != null && !tittle.isBlank()) {
                            builder = builder.queryParam("tittle", tittle);
                        }

                        if (author != null && !author.isBlank()) {
                            builder = builder.queryParam("author", author);
                        }
                        
                        if (category != null)
                            builder = builder.queryParam("category", category);
                        return builder.build();
                })
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<BookDto>>() {
                });
        List<BookDto> body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if (status.is2xxSuccessful() && body != null) {
            return body;
        } else {
            throw new RuntimeException("Failed to search books: " + status);
        }
    }


    @Override
    public BookDto getBookById(UUID id) {
        BookDto book = restClient.get()
                .uri("/api/books/{id}", id)
                .retrieve()
                .body(BookDto.class);
            return book;
    }

    @Override
    public BookDto createBook(BookDto bookDto){
        ResponseEntity<BookDto> response = restClient.post()
                .uri("/api/books")
                .body(bookDto)
                .retrieve()
                .toEntity(BookDto.class);

        BookDto body = response.getBody();
        HttpStatusCode status = response.getStatusCode();
        if ((status.is2xxSuccessful() || status.value() == HttpStatus.CREATED.value()) && body != null) {
            return body;
        }
        throw new RuntimeException("Failed to create book: " + status);
        }
    

    @Override
    public BookDto updateBook(UUID bookId, BookDto bookDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBook'");
    }

    @Override
    public void deleteBook(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBook'");
    }

}
