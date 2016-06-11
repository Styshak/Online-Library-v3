package com.styshak.dao;

public class DaoFactory {

    private static GroupDao groupDao;
    private static UserDao userDao;
    private static GenreDao genreDao;
    private static AuthorDao authorDao;
    private static PublisherDao publisherDao;
    private static BookDao bookDao;
    private static VoteDao voteDao;
    private static DaoFactory instance = null;

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }
    
    public GroupDao getGroupDao() {
        if (groupDao == null) {
            groupDao = new GroupDaoImpl();
        }
        return groupDao;
    }

    public UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl();
        }
        return userDao;
    }
    
    public GenreDao getGenreDao() {
        if(genreDao == null) {
            genreDao = new GenreDaoImpl();
        }
        return genreDao;
    }
    
    public AuthorDao getAuthorDao() {
        if(authorDao == null) {
            authorDao = new AuthorDaoImpl();
        }
        return authorDao;
    }
    
    public PublisherDao getPublisherDao() {
        if(publisherDao == null) {
            publisherDao = new PublisherDaoImpl();
        }
        return publisherDao;
    }
    
    public BookDao getBookDao(){
        if(bookDao == null) {
            bookDao = new BookDaoImpl();
        }
        return bookDao;
    }
    
    public VoteDao getVoteDao() {
        if(voteDao == null) {
            voteDao = new VoteDaoImpl();
        }
        return voteDao;
    }
}
