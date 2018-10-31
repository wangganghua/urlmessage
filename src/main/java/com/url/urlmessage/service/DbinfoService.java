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
     * @param dbinfo
     * @return
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
     * @return
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
     * @return
     */
    public List<Object> getList() {
        String sql = "SELECT '{\"url\": \"'+页面信息+'\",\"attr\": {\"urlleibie\": \"'+品类+'\",\"urlweb\": \"'+电商+'\",\"brand\": \"'+品牌+'\",\"model\": \"'+机型+'\"}}' FROM URLDATA WHERE NEED=0 AND 电商!='天猫商城'";
        SqlRowSet st = jdbcTemplate.queryForRowSet(sql);
        List list = Lists.newArrayList();
        while (st.next())
            list.add(st.getString(1));
        return list;
    }
}
