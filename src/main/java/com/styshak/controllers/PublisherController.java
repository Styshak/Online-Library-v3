package com.styshak.controllers;

import com.styshak.dao.DaoFactory;
import com.styshak.entity.Publisher;
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
public class PublisherController implements Serializable, Converter{
    
    private List<SelectItem> selectItems = new ArrayList<>();
    private Map<Long, Publisher> map;
    private List<Publisher> publisherList;
    
    @PostConstruct
    public void init() {
        map = new HashMap<>();
        publisherList = DaoFactory.getInstance().getPublisherDao().getAllPublishers();
        for (Publisher publisher : publisherList) {
            map.put(publisher.getId(), publisher);
            selectItems.add(new SelectItem(publisher, publisher.getName()));
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Publisher) value).getId().toString();
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public List<Publisher> getGenreList() {
        return publisherList;
    }
}
