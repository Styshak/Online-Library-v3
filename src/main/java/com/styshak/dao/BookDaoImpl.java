package com.styshak.dao;

import com.styshak.entity.Book;
import com.styshak.entity.HibernateUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

public class BookDaoImpl implements BookDao {

    private Session session;
    private ProjectionList bookProjection;
    
    public BookDaoImpl() {
        bookProjection = Projections.projectionList();
        bookProjection.add(Projections.property("id"), "id");
        bookProjection.add(Projections.property("name"), "name");
        bookProjection.add(Projections.property("image"), "image");
        bookProjection.add(Projections.property("genre"), "genre");
        bookProjection.add(Projections.property("pageCount"), "pageCount");
        bookProjection.add(Projections.property("isbn"), "isbn");
        bookProjection.add(Projections.property("publisher"), "publisher");
        bookProjection.add(Projections.property("author"), "author");
        bookProjection.add(Projections.property("publishYear"), "publishYear");
        bookProjection.add(Projections.property("description"), "description");
        bookProjection.add(Projections.property("rating"), "rating");
        bookProjection.add(Projections.property("voteCount"), "voteCount");
    }

    public Session openSession() {
        session = HibernateUtil.getSessionFactory().openSession();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    @Override
    public List<Book> getAllBooksList(int from, int to) {
        List<Book> list = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.setFirstResult(from).setMaxResults(to);
        list = criteria.list();
        closeSession();
        return list;
    }

    @Override
    public List<Book> getBooksListByGenre(Long genreId, int from, int to) {
        List<Book> list = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.add(Restrictions.eq("genre.id", genreId));
        criteria.setFirstResult(from).setMaxResults(to);
        list = criteria.list();
        closeSession();
        return list;
    }

    @Override
    public Long getCountAllBooks() {
        Long count = 0L;
        openSession();
        count = (Long) session.createCriteria(Book.class).setProjection(Projections.rowCount()).uniqueResult();
        closeSession();
        return count;
    }

    @Override
    public Long getCountBooksByGenre(Long genreId) {
        Long count = 0L;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.add(Restrictions.eq("genre.id", genreId));
        criteria.setProjection(Projections.rowCount());
        count = (Long) criteria.uniqueResult();
        closeSession();
        return count;
    }

    @Override
    public List<Book> getBooksListByLetter(Character letter, int from, int to) {
        List<Book> list = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.add(Restrictions.ilike("name", letter.toString(), MatchMode.START));
        criteria.setFirstResult(from).setMaxResults(to);
        list = criteria.list();
        closeSession();
        return list;
    }

    @Override
    public Long getCountBooksByLetter(Character letter) {
        Long count = 0L;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.add(Restrictions.ilike("name", letter.toString(), MatchMode.START));
        criteria.setProjection(Projections.rowCount());
        count = (Long) criteria.uniqueResult();
        closeSession();
        return count;
    }

    @Override
    public List<Book> getBooksListByName(String name, int from, int to) {
        List<Book> list = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
        criteria.setFirstResult(from).setMaxResults(to);
        list = criteria.list();
        closeSession();
        return list;
    }

    @Override
    public Long getCountBooksListByName(String name) {
        Long count = 0L;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
        criteria.setProjection(Projections.rowCount());
        count = (Long) criteria.uniqueResult();
        closeSession();
        return count;
    }

    @Override
    public List<Book> getBooksListByAuthor(String author, int from, int to) {
       List<Book> list = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class, "b");
        criteria.setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.createAlias("b.author", "author");
        criteria.add(Restrictions.ilike("author.name", author, MatchMode.ANYWHERE));
        criteria.setFirstResult(from).setMaxResults(to);
        list = criteria.list();
        closeSession();
        return list;
    }

    @Override
    public Long getCountBooksListByAuthor(String author) {
        Long count = 0L;
        openSession();
        Criteria criteria = session.createCriteria(Book.class, "b");
        criteria.createAlias("b.author", "author");
        criteria.add(Restrictions.ilike("author.name", author, MatchMode.ANYWHERE));
        criteria.setProjection(Projections.rowCount());
        count = (Long) criteria.uniqueResult();
        closeSession();
        return count;
    }

    @Override
    public byte[] getBookContentById(Long bookId) {
        byte[] content = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class, "b");
        criteria.setProjection(Property.forName("content"));
        criteria.add(Restrictions.eq("id", bookId));
        content = (byte[]) criteria.uniqueResult();
        closeSession();
        return content;
    }

    @Override
    public void delete(Book book) {
        openSession();
        Query query = session.createQuery("delete from Book where id = :id");
        query.setParameter("id", book.getId());
        int result = query.executeUpdate();
        closeSession();
    }

    @Override
    public void update(Book book) {
        openSession();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("update Book ");
        queryBuilder.append("set name = :name, ");
        queryBuilder.append("pageCount = :pageCount, ");
        queryBuilder.append("isbn = :isbn, ");
        queryBuilder.append("genre = :genre, ");
        queryBuilder.append("author = :author, ");
        queryBuilder.append("publishYear = :publishYear, ");
        queryBuilder.append("publisher = :publisher, ");
        if (book.getImage() != null) {
            queryBuilder.append("image = :image, ");
        }
        if (book.getContent() != null) {
            queryBuilder.append("content = :content, ");
        }
        queryBuilder.append("description = :description ");
        queryBuilder.append("where id = :id");
        
        Query query = session.createQuery(queryBuilder.toString());
        query.setParameter("name", book.getName());
        query.setParameter("pageCount", book.getPageCount());
        query.setParameter("isbn", book.getIsbn());
        query.setParameter("genre", book.getGenre());
        query.setParameter("author", book.getAuthor());
        query.setParameter("publishYear", book.getPublishYear());
        query.setParameter("publisher", book.getPublisher());
        query.setParameter("description", book.getDescription());
        query.setParameter("id", book.getId());
        if (book.getName() != null) {
            query.setParameter("image", book.getImage());
        }
        if (book.getContent() != null) {
            query.setParameter("content", book.getContent());
        }
        int result = query.executeUpdate();
    }

    @Override
    public void updateRate(Book book) {
        openSession();
        Query query = session.createQuery("select new map(round(avg(value)) as rating, count(value) as voteCount)  from Vote v where v.book.id=:id");
        query.setParameter("id", book.getId());

        Map<String, Object> map = (HashMap<String, Object>) query.uniqueResult();

        long voteCount = Long.valueOf(map.get("voteCount").toString());
        long rating = Double.valueOf(map.get("rating").toString()).longValue();

        query = session.createQuery("update Book set rating = :rating, "
                + " voteCount = :voteCount"
                + " where id = :id");

        query.setParameter("rating", rating);
        query.setParameter("voteCount", voteCount);
        query.setParameter("id", book.getId());

        int result = query.executeUpdate();
    }

    @Override
    public List<Book> getBooksListByRate(int from, int to) {
        List<Book> list = null;
        openSession();
        Criteria criteria = session.createCriteria(Book.class);
        criteria.setProjection(bookProjection).setResultTransformer(Transformers.aliasToBean(Book.class));
        criteria.addOrder(Order.desc("rating"));
        criteria.setFirstResult(from).setMaxResults(to);
        list = criteria.list();
        closeSession();
        return list;
    }

    @Override
    public void add(Book book) {
        openSession();
        session.save(book);
        closeSession();
    }
}
