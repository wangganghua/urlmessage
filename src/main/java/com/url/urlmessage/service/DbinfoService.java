package com.url.urlmessage.service;

import com.google.common.collect.Lists;
import com.url.urlmessage.domain.model.Dbinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbinfoService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    Dbinfo dbinfo;

    /**
     * 添加
     *
     * @param dbinfo:实体
     * @return 影响条数
     */
    public int addMumbers(Dbinfo dbinfo) {
        int res = jdbcTemplate.update("insert into dbinfo(name) values(?)",
                new PreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                        preparedStatement.setString(1, dbinfo.getName());
                    }
                });
        return res;
    }

    /**
     * 返回map类型的list集合
     *
     * @return Dbinfo
     */
    public List<Dbinfo> getListMap() {
        String sql = "SELECT * FROM DBINFO";
        return (List<Dbinfo>) jdbcTemplate.query(sql, new RowMapper<Dbinfo>() {
            @Override
            public Dbinfo mapRow(ResultSet rst, int rowNum) throws SQLException {
                dbinfo.setId(rst.getLong("id"));
                dbinfo.setName(rst.getString("name"));
                return dbinfo;
            }
        });
    }

    /**
     * 返回list集
     *
     * @return list
     */
    public List<Object> getList() {
        String sql = "SELECT TOP 100 '{\"url\": \"'+页面信息+'\",\"attr\": {\"urlleibie\": \"'+品类+'\",\"urlweb\": \"'+电商+'\",\"brand\": \"'+品牌+'\",\"model\": \"'+机型+'\"}}' FROM URLDATA WHERE NEED=0 AND 电商!='天猫商城'";
        SqlRowSet st = jdbcTemplate.queryForRowSet(sql);
        List list = Lists.newArrayList();
        while (st.next())
            list.add(st.getString(1));
        return list;
    }

    /**
     * 查询这个表的数据，保存成List<Map<String,Object>>
     * @return  list data
     */
    @SuppressWarnings("rawtypes")
    public List getMapList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT TOP 5000 页面信息,品类,品牌,机型,旗舰店 FROM URLDATA WHERE NEED=0 AND 电商!='天猫商城'");
//        stringBuilder.append("SELECT TOP 50000 zzid,月度,周度,品类,品牌,机型编码,机型,单价,销量,销额,电商,评论人,评论日期,总体评价,商品名称,页面信息,促销信息,旗舰店,dataflag,销售类型,货仓,月销量,COLOUR,SIZE,旗舰店类型,写入日期 FROM dbase..av_线上周度彩电永久表2018年");
        return jdbcTemplate.queryForList(stringBuilder.toString());
    }
}
