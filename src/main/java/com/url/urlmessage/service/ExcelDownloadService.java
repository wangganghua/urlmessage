package com.url.urlmessage.service;

import com.google.common.collect.Lists;
import com.url.urlmessage.utils.SystemConfig;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 导出数据至 excel文件
 */

public interface ExcelDownloadService {

    public void ExcelDownload_XSL(HttpServletResponse httpServletResponse, String properName);

    public void ExcelDownload_XSLX(HttpServletResponse httpServletResponse, String properName);
}
