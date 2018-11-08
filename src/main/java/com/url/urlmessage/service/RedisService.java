package com.url.urlmessage.service;

import java.util.List;

public interface RedisService {
    public Object addListString(List<String> list, String keyName) throws Exception;
}
