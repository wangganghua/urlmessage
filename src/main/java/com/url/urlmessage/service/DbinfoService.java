package com.url.urlmessage.service;

import com.url.urlmessage.domain.model.Dbinfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DbinfoService {

    public int addMumbers(Dbinfo dbinfo);

    public List<Dbinfo> getListMap();

    public List<Object> getList();

    public List getMapList();

}
