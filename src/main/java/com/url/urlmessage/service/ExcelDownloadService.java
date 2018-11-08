package com.url.urlmessage.service;

import com.google.common.collect.Lists;
import com.url.urlmessage.utils.SystemConfig;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 导出数据至 excel文件
 */

@Service
@Transactional
public class ExcelDownloadService {

    @Autowired
    private DbinfoService dbinfoService;
    //获取配置文件中对应属性值
    private String getProperties;

    @Transactional
    public void ExcelDownload_XSL(HttpServletResponse httpServletResponse, String properName) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet(properName);
        getProperties = SystemConfig.getProperty("cqwf2." + properName);
        String[] info = properName.split(",");


        List<String> keylist = Lists.newArrayList();//保存英文字段
        List<String> valuelist = Lists.newArrayList();//保存中文字段
        List<Map<String, Object>> tableInfoList_xls = Lists.newArrayList();
        for (int i = 0; i < info.length; i++) {
            keylist.add(info[i]);
            valuelist.add(info[i]);
        }
        String fileName = "wghtest.xlsx";

        //创建表头
        HSSFRow hssfRow = hssfSheet.createRow(0);
        //循环添加表头
        int index = 0;

        for (String item : keylist) {
            HSSFCell hssfCell = hssfRow.createCell(index);
            HSSFRichTextString hssfRichTextString = new HSSFRichTextString(valuelist.get(index));
            hssfCell.setCellValue(hssfRichTextString);
            index++;
        }

        //循环获取数据库中每行数据

        tableInfoList_xls = dbinfoService.getMapList();

        for (int i = 0; i < tableInfoList_xls.size(); i++) {
            // 一个List对象是一个Map，一行数据，一个Map对象对应一行里的一条数据
            Map tableMap = tableInfoList_xls.get(i);
            //表头是第0行，所以从第一行开始创建
            Row row = hssfSheet.createRow(i + 1);
            for (int j = 0; j < keylist.size(); j++) {
                //获取指定字段的值，判断是否为空
                Object object = tableMap.get(keylist.get(j));
                String val = "";
                if (object != null) {
                    val = object.toString();
                }
                row.createCell(j).setCellValue(val);
            }
        }
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            httpServletResponse.flushBuffer();
            hssfWorkbook.write(httpServletResponse.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void ExcelDownload_XSLX(HttpServletResponse httpServletResponse, String properName) {
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = hssfWorkbook.createSheet("sheet1");
        getProperties = SystemConfig.getProperty("cqwf2." + properName);
        String[] info = properName.split(",");

        List<String> keylist_xlsx = Lists.newArrayList();//保存英文字段
        List<String> valuelist = Lists.newArrayList();//保存中文字段
        List<Map<String, Object>> tableInfoList = Lists.newArrayList();
        for (int i = 0; i < info.length; i++) {
            keylist_xlsx.add(info[i]);
            valuelist.add(info[i]);
        }
        String fileName = "wghtest.xlsx";

        //创建表头
        XSSFRow xssfRow = xssfSheet.createRow(0);
        //循环添加表头
        int index = 0;

        for (String item : keylist_xlsx) {
            XSSFCell xssfCell = xssfRow.createCell(index);
            XSSFRichTextString xssfRichTextString = new XSSFRichTextString(valuelist.get(index));
            xssfCell.setCellValue(xssfRichTextString);
            index++;
        }

        //循环获取数据库中每行数据
        tableInfoList = dbinfoService.getMapList();

        for (int i = 0; i < tableInfoList.size(); i++) {
            //一个List对象是一个Map，一行数据，一个Map对象对应一行里的一条数据
            Map tableMapxlsx = tableInfoList.get(i);
            //表头是第0行，所以从第一行开始创建
            Row rowxlsx = xssfSheet.createRow(i + 1);
            for (int j = 0; j < keylist_xlsx.size(); j++) {
                //获取指定字段的值，判断是否为空
                Object object_xlsx = tableMapxlsx.get(keylist_xlsx.get(j));
                String val_xlsx = "";
                if (object_xlsx != null) {
                    val_xlsx = object_xlsx.toString();
                }
                rowxlsx.createCell(j).setCellValue(val_xlsx);
            }
            System.out.println("开始---:" + (i + 1) + " / " + tableInfoList.size());
        }
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            httpServletResponse.flushBuffer();
            hssfWorkbook.write(httpServletResponse.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
