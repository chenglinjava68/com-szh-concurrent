package com.szh.util.common.proxy;

import java.util.HashMap;
import java.util.Map;

public class DefaultValueMapper {
    private static final Map<Class, Object> DEFAULT_MAPPER = new HashMap();
    private final Map<Class, Object> defaultValueMap;

    public DefaultValueMapper(Map<Class, Object> defaultValueMap) {
        this.defaultValueMap = defaultValueMap;
    }

    public DefaultValueMapper() {
        this(DEFAULT_MAPPER);
    }

    public Object getDefaultValueOfClass(Class cls) {
        return this.defaultValueMap.get(cls);
    }

    static {
        DEFAULT_MAPPER.put(Integer.TYPE, Integer.valueOf(-1));
        DEFAULT_MAPPER.put(Long.TYPE, Long.valueOf(-1L));
        DEFAULT_MAPPER.put(Double.TYPE, Double.valueOf(-1.0D));
        DEFAULT_MAPPER.put(Boolean.TYPE, Boolean.valueOf(false));
    }
}