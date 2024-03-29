package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.exceptions.DeletedAuthorHasBooksException;
import ru.otus.spring.exceptions.EntityNotFoundException;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repository.BookRepository;

import java.util.List;

@Component
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    private final BookRepository bookRepository;

    @Override
    public Author save(Author author) {
        return repository.save(author);
    }

    @Override
    public Author getById(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Author> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) throws DeletedAuthorHasBooksException {
        if (bookRepository.existsByAuthorId(id)) {
            throw new DeletedAuthorHasBooksException();
        }
        repository.deleteById(id);
    }

}
