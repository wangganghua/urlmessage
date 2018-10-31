package com.url.urlmessage.web;

import com.google.common.collect.Lists;
import com.url.urlmessage.domain.model.Dbinfo;
import com.url.urlmessage.service.DbinfoService;
import com.url.urlmessage.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class DbController {
    @Autowired
    private DbinfoService dbinfoService;

    @Autowired
    private Dbinfo dbinfo;

    @RequestMapping("/insert")
    private String insert() {
        for (int i = 0; i < 10; i++) {
            dbinfo.setName("wd");
            dbinfoService.addMumbers(dbinfo);
        }
        return dbinfo.toString();
    }

    @RequestMapping("/get")
    private List<Object> getList() {
        List list = Lists.newArrayList();
        list = dbinfoService.getList();
        System.out.println(list.size());
        list.forEach(item -> {
            System.out.println(item);
        });
        return list;
    }

    /**
     * 线程安全控制
     */
    private static Integer key = 0;

    private List<String> keys = Collections.synchronizedList(Lists.newArrayList());

    @Autowired
    RedisService redisService;

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public Object addListString() throws Exception {
        String keyName = "JDUrlDatabaseSpider_testTV:items";
        String k = getKey();
        System.out.println("k:" + k);
        List list = Lists.newArrayList();
        list = dbinfoService.getList();
        return redisService.addListString(list, keyName);
    }

    public synchronized String getKey() {
        String k = (++key) + "";
        keys.add(k);
        System.out.println(k);
        return k;
    }
}
