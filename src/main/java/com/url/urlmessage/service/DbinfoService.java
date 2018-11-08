package com.url.urlmessage.service;

import com.url.urlmessage.domain.model.Dbinfo;

import java.util.List;

public interface DbinfoService {

    public int addMumbers(Dbinfo dbinfo);

    public List<Dbinfo> getListMap();

    public List<Object> getList();

    public List getMapList();

}
