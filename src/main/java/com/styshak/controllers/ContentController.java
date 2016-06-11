package com.styshak.controllers;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ContentController implements Serializable {

    private final int IMAGE_MAX_SIZE = 10204800;
    private UploadedFile file;
    @ManagedProperty(value = "#{bookListController}")
    private BookListController bookListController;

    public void setBookListController(BookListController bookListController) {
        this.bookListController = bookListController;
    }

    public int getImageMaxSize() {
        return IMAGE_MAX_SIZE;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void fileUploadListener(FileUploadEvent e) {
        this.file = e.getFile();
    }

    public void updateContent() {
        if (file != null) {
            bookListController.getSelectedBook().setContent(file.getContents());
        }
    }
}
