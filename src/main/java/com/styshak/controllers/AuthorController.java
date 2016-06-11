
package com.styshak.controllers;

import com.styshak.dao.DaoFactory;
import com.styshak.entity.Author;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

@ManagedBean
@SessionScoped
public class AuthorController implements Serializable, Converter{
    
    private List<SelectItem> selectItems = new ArrayList<>();
    private Map<Long, Author> map;
    private List<Author> authorList;
    
    @PostConstruct
    public void init() {
        map = new HashMap<>();
        authorList = DaoFactory.getInstance().getAuthorDao().getAllAuthors();
        for (Author author : authorList) {
            map.put(author.getId(), author);
            selectItems.add(new SelectItem(author, author.getName()));
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Author) value).getId().toString();
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public List<Author> getGenreList() {
        return authorList;
    }
}
