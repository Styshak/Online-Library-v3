package com.styshak.controllers;

import com.styshak.entity.Book;
import com.styshak.entity.User;
import com.styshak.enums.CurrentRequest;
import com.styshak.enums.SearchType;
import com.styshak.models.BookListDataModel;
import com.styshak.service.BookService;
import com.styshak.utils.BookUtils;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Named;
import org.primefaces.component.datagrid.DataGrid;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

@ManagedBean(eager = true)
@Named(value = "bookListController")
@SessionScoped
public class BookListController implements Serializable {

    private DataGrid dataTable;
    private LazyDataModel<Book> bookListModel;
    private Long selectedAuthorId;// текущий автор книги из списка при редактировании книги
    // критерии поиска
    private char selectedLetter; // выбранная буква алфавита, по умолчанию не выбрана ни одна буква
    private SearchType selectedSearchType = SearchType.TITLE;// хранит выбранный тип поиска, по-умолчанию - по названию
    private long selectedGenreId; // выбранный жанр
    private String currentSearchString; // хранит поисковую строку
    private BookService bookService;
    //-------
    private boolean editModeView;// отображение режима редактирования
    private boolean addModeView;// отображение режима добавление
    private Book selectedBook;

    public BookListController() {
        bookService = new BookService();
        bookListModel = new BookListDataModel(bookService);
    }

    private int calcSelectedPage() {
        int page = dataTable.getPage();// текущий номер страницы (индексация с нуля)

        int leftBound = bookService.getTo() * (page - 1);
        int rightBound = bookService.getTo() * page;

        boolean goPrevPage = bookService.getTotalBooksCount() > leftBound & bookService.getTotalBooksCount() <= rightBound;

        if (goPrevPage) {
            page--;
        }

        return (page > 0) ? page * bookService.getTo() : 0;
    }

    public void fillBooksListByGenre() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedGenreId = Long.valueOf(params.get("genre_id"));
        updateValues(' ', selectedGenreId);
        bookService.setCurrentRequest(CurrentRequest.GENRE);
        bookService.setSelectedGenreId(selectedGenreId);
        bookService.populateCountBooksList();
    }

    public void fillBooksListByLetter() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedLetter = params.get("letter").charAt(0);
        updateValues(selectedLetter, -1);
        bookService.setCurrentRequest(CurrentRequest.LETTER);
        bookService.setSelectedLetter(selectedLetter);
        bookService.populateCountBooksList();
    }

    public void fiilBooksListBySearchString() {
        updateValues(' ', -1);
        if (currentSearchString.trim().length() == 0) {
            bookService.setCurrentRequest(null);
            bookService.populateCountBooksList();
        } else {
            bookService.setCurrentRequest(CurrentRequest.SEARCH_STRING);
            bookService.setCurrentSearchString(currentSearchString);
            bookService.setSelectedSearchType(selectedSearchType);
            bookService.populateCountBooksList();
        }
    }
    
    public void fillBooksListByRate() {
        updateValues(' ', -1);
        bookService.setCurrentRequest(CurrentRequest.RATE);
        bookService.populateCountBooksList();
    }

    public void delete() {
        bookService.delete(selectedBook);
        bookService.populateCountBooksList();
        ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("deleted")));
        dataTable.setFirst(calcSelectedPage());
    }

    public void update() {
        bookService.update(selectedBook);
        bookService.populateCountBooksList();
        RequestContext.getCurrentInstance().execute("PF('dlgEditBook').hide()");
        ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("updated")));
    }
    
    public void add() {
        bookService.add(selectedBook);
        bookService.populateCountBooksList();
        RequestContext.getCurrentInstance().execute("PF('dlgEditBook').hide()");
        ResourceBundle bundle = ResourceBundle.getBundle("com.styshak.nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("added")));
    }

    private void updateValues(Character selectedLetter, long selectedGenreId) {
        this.selectedLetter = selectedLetter;
        this.selectedGenreId = selectedGenreId;
        dataTable.setFirst(0);
    }

    public void rate() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        long bookId = Long.parseLong(params.get("book_id"));

        FacesContext context = FacesContext.getCurrentInstance();
        User user = (User) context.getExternalContext().getSessionMap().get("user");

        Book book = BookUtils.getBookById(bookService.getList(), bookId);

        bookService.rate(book, user);
    }

    public DataGrid getDataTable() {
        if (dataTable == null) {
            return dataTable;
        } else {
            return null;
        }
    }

    public void setDataTable(DataGrid dataTable) {
        this.dataTable = dataTable;
    }

    public LazyDataModel<Book> getBookListModel() {
        return bookListModel;
    }

    public void setBookListModel(LazyDataModel<Book> bookListModel) {
        this.bookListModel = bookListModel;
    }

    public Long getSelectedAuthorId() {
        return selectedAuthorId;
    }

    public void setSelectedAuthorId(Long selectedAuthorId) {
        this.selectedAuthorId = selectedAuthorId;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    public SearchType getSelectedSearchType() {
        return selectedSearchType;
    }

    public void setSelectedSearchType(SearchType selectedSearchType) {
        this.selectedSearchType = selectedSearchType;
    }

    public long getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(long selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public String getCurrentSearchString() {
        return currentSearchString;
    }

    public void setCurrentSearchString(String currentSearchString) {
        this.currentSearchString = currentSearchString;
    }

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public boolean isEditModeView() {
        return editModeView;
    }

    public void setEditModeView(boolean editModeView) {
        this.editModeView = editModeView;
    }

    public boolean isAddModeView() {
        return addModeView;
    }

    public void setAddModeView(boolean addModeView) {
        this.addModeView = addModeView;
    }

    public void cancelModes() {
        if (addModeView) {
            addModeView = false;
        }

        if (editModeView) {
            editModeView = false;
        }

        RequestContext.getCurrentInstance().execute("PF('dlgEditBook').hide()");
    }

    public void switchEditMode() {
        editModeView = true;
        RequestContext.getCurrentInstance().execute("PF('dlgEditBook').show()");
    }
    
    public void  switchAddMode() {
        addModeView = true;
        selectedBook = new Book();
        RequestContext.getCurrentInstance().execute("PF('dlgEditBook').show()");
    }
    

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }
    
    public ActionListener updateOrInsert() {
        return new ActionListener() {
            @Override
            public void processAction(ActionEvent event) {

                if (editModeView) {
                    update();
                } else if (addModeView) {
                    add();
                    dataTable.setFirst(calcSelectedPage());
                }
                cancelModes();
            }
        };
    }
}
