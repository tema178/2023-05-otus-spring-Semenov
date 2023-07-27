package ru.otus.spring.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.util.List;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author create(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select s from Author s", Author.class);
        return query.getResultList();
    }

    @Override
    public int update(Author author) {
        em.merge(author);
        return 1;
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Author where id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}