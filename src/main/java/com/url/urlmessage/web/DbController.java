package com.url.urlmessage.web;

import com.google.common.collect.Lists;
import com.url.urlmessage.domain.model.Dbinfo;
import com.url.urlmessage.service.AccessDownloadService;
import com.url.urlmessage.service.DbinfoService;
import com.url.urlmessage.service.ExcelDownloadService;
import com.url.urlmessage.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController
public class DbController {
    @Autowired
    private DbinfoService dbinfoService;

    @Autowired
    private Dbinfo dbinfo;

    @Autowired
    private AccessDownloadService accessDownloadService;

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
    @Autowired
    ExcelDownloadService excelDownloadService;

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

    @RequestMapping(value = "/getexcel")
    private String inputExcel(HttpServletResponse response) {
        long begin = System.currentTimeMillis();
        excelDownloadService.ExcelDownload_XSLX(response, "zzid,月度,周度,品类,品牌,机型编码,机型,单价,销量,销额,电商,评论人,评论日期,总体评价,商品名称,页面信息,促销信息,旗舰店,dataflag,销售类型,货仓,月销量,COLOUR,SIZE,旗舰店类型,写入日期");

        long end = System.currentTimeMillis();

        long d = end - begin;
        System.out.println("消耗：" + d + "毫秒");

        return "消耗：" + d + "毫秒";
    }

    @RequestMapping(value = "/getaccess")
    private String inpuAccess(){
        long begion = System.currentTimeMillis();
        accessDownloadService.getData();
        long end = System.currentTimeMillis();
        long d = end - begion;
        return "消耗 "+d+" 毫秒";
    }


}
