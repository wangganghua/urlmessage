package com.url.urlmessage.service;


import javax.servlet.http.HttpServletResponse;

/**
 * 导出数据至 excel文件
 */

public interface ExcelDownloadService {

    public void ExcelDownload_XSL(HttpServletResponse httpServletResponse, String properName);

    public void ExcelDownload_XSLX(HttpServletResponse httpServletResponse, String properName);
}
