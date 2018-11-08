package com.url.urlmessage.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * 操作 mdb (Access)文件
 */
public class AccessUtils {

    @Value("${access.mdb.jdbc.url}")
    private static String access_url;
    @Value("${access.mdb.driverClassName}")
    private static String access_driver;

    @Value("${spring.thymeleaf.prefix}")
    private static String access_temp_path;

    private static ClassPathResource classPathResource = new ClassPathResource("templates/temp.mdb");

    private static String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

    /**
     * 连接 access
     *
     * @param path_fileName: access文件全路径
     */
    public static Connection getCon(String path_fileName) {
        //加载驱动
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        } catch (ClassNotFoundException cnfex) {
            System.out.println("Problem in loading or registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }

        try {
//            return DriverManager.getConnection("jdbc:unanaccess://" + path_fileName);
            return DriverManager.getConnection("jdbc:ucanaccess://E:\\wgh.accdb;showSchema=true");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 关闭资源
     *
     * @param con               连接
     * @param preparedStatement
     * @param resultSet
     */
    public static void close(Connection con, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 通过 create table 语句创建 access table
     *
     * @param fullPath : 路径及文件名
     * @param strSql   ：create sql语句
     */
    public static boolean CreateAccessTable(String fullPath, String strSql) {
        if (!strSql.trim().toUpperCase().contains("CREATE")) {
            System.out.println("create access file need 'create table' begin.......");
            return false;
        }
        PreparedStatement ps = null;
        Connection con = getCon(fullPath);
        try {
            con.setSchema("wag");
            //Statement statement = con.createStatement(); //创建 table
            PreparedStatement preparedStatement = null;
            preparedStatement = con.prepareStatement(strSql);
            preparedStatement.execute();
            //statement.execute(strSql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(con, ps, null);
        }
    }

    public static boolean insertInto(String fullPath, List<String> listSql) {
//        if (!strSql.trim().toUpperCase().contains("INSERT")) {
//            System.out.println("create access file need 'create table' begin.......");
//            return false;
//        }
        Connection con = getCon(fullPath);
        try {
            Statement statement = con.createStatement(); //创建 table
            listSql.forEach(item -> {
                try {
                    statement.executeUpdate(item.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制temp.mdb 空模板
     *
     * @param fileTo : 复制到目的地
     * @return : 返回 true:成功、false:失败
     */
    public static boolean copyTemp(String fileTo) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + classPathResource.getPath());
            FileOutputStream fileOutputStream = new FileOutputStream(fileTo);
            byte[] bytes = new byte[20480];
            int count;
            while ((count = fileInputStream.read(bytes)) > 0) {
                fileOutputStream.write(bytes, 0, count);
            }
            fileInputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(classPathResource.getPath());
            return false;
        }
    }
}

