package com.url.urlmessage.service;

import com.google.common.collect.Lists;
import com.url.urlmessage.Utils.AccessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 导出 access 文件
 */
@Component
public class AccessDownloadService {
//    @Autowired
//    AccessUtils accessUtils;
    @Autowired
    DbinfoService dbinfoService;
    /**
     * 导出数据,存放至 access 文件
     */
    public void getData() {
        //第一步,先复制空 .mdb文件 重新命名
        String new_path = "E:\\\\wgh.accdb";
        boolean isTrue = true; //AccessUtils.copyTemp(new_path);
        if (isTrue) {
            System.out.println("成功创建Access文件：" + new_path);
            //第二步, 创建 access 表
            isTrue = false;
            String strSql = "create table wag (页面信息 text(200),品类 text(50),品牌 text(60),机型 text(70),旗舰店 text(70))";
            isTrue = AccessUtils.CreateAccessTable(new_path, strSql);
            isTrue = true;
            if(isTrue) {
                System.out.println("成功创建 access table 。。。。");
                //第三步,开始写入数据
                List<Map<String, Object>> tableInfoList = dbinfoService.getMapList();
                List<String> listSql = Lists.newArrayList();
                //页面信息,品类,品牌,机型,旗舰店
                String[] keylist = {"页面信息", "品类", "品牌", "机型", "旗舰店"};
                for (int i = 0; i < tableInfoList.size(); i++) {
                    //一个List对象是一个Map，一行数据，一个Map对象对应一行里的一条数据
                    Map tableMapxlsx = tableInfoList.get(i);
                    String sql = "insert into wag(页面信息,品类,品牌,机型,旗舰店) values(";
                    String value = "";
                    for (int j = 0; j < keylist.length; j++) {
                        Object object_xlsx = tableMapxlsx.get(keylist[j]);
                        if (object_xlsx == null)
                            object_xlsx = "";
                        value += "'" + object_xlsx.toString() + "',";
                    }
                    value = removeCharAt(value, value.lastIndexOf(","));
                    sql += value+ ")";
                    listSql.add(sql);
                }
                //第四步,开始写入
                isTrue = false;
                isTrue = AccessUtils.insertInto(new_path, listSql);
            }
            else {
                System.out.println("创建 access table 失败.....");
            }

        } else {
            System.out.println("创建Access文件失败：" + new_path);
        }
    }
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
}
