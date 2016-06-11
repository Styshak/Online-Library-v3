/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.styshak.servlet;

import com.styshak.controllers.BookListController;
import com.styshak.entity.Book;
import com.styshak.utils.BookUtils;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sergey
 */
@WebServlet(name = "ShowImage", urlPatterns = {"/ShowImage"})
public class ShowImage extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("image/jpeg");
        try (OutputStream out = response.getOutputStream();) {
            int book_id = Integer.valueOf(request.getParameter("book_id"));
            BookListController bookListController = (BookListController) request.getSession(false).getAttribute("bookListController");
            Book book = BookUtils.getBookById(bookListController.getBookService().getList(), book_id);
            byte[] image = null;
            if (book != null) {
                image = book.getImage();
                response.setContentLength(image.length);
                out.write(image);
            }
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
