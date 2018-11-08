package com.url.urlmessage.service;

import com.google.common.collect.Lists;
import com.url.urlmessage.utils.AccessUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 导出 access 文件
 */
@Component
public interface AccessDownloadService {

    public void getData();

    public String removeCharAt(String s, int pos);
}
