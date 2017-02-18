package com.szh.util.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

public class CollectionUtil {
    public CollectionUtil() {
    }

    public static List<Integer> getIntegerListFromString(String src, String sep) {
        if(StringUtil.isEmpty(src)) {
            return Collections.emptyList();
        } else {
            if(StringUtil.isEmpty(sep)) {
                sep = " ";
            }

            LinkedList result = new LinkedList();
            String[] srcArray = src.trim().split(sep);
            String[] var4 = srcArray;
            int var5 = srcArray.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String number = var4[var6];
                if(StringUtil.isInteger(number.trim())) {
                    result.add(Integer.valueOf(Integer.parseInt(number.trim())));
                }
            }

            return result;
        }
    }

    public static List<String> getStringListFromString(String src, String sep) {
        if(StringUtil.isEmpty(src)) {
            return Collections.emptyList();
        } else {
            LinkedList result = new LinkedList();
            String[] srcArray = src.trim().split(sep);
            String[] var4 = srcArray;
            int var5 = srcArray.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String item = var4[var6];
                if(StringUtil.isNotEmpty(item.trim())) {
                    result.add(item.trim());
                }
            }

            return result;
        }
    }

    public static <T> boolean isCollectionEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isCollectionNotEmpty(Collection<T> collection) {
        return !isCollectionEmpty(collection);
    }

    public static boolean isContain(List<Integer> list, Integer i) {
        return !isCollectionEmpty(list) && i != null && Collections.binarySearch(list, i) >= 0;
    }

    public static <T> String getStringFromList(List<T> list, String sep) {
        if(isCollectionEmpty(list)) {
            return "";
        } else {
            if(StringUtil.isEmpty(sep)) {
                sep = " ";
            }

            StringBuilder sb = new StringBuilder();
            Iterator lastSep = list.iterator();

            while(lastSep.hasNext()) {
                Object t = lastSep.next();
                sb.append(t.toString()).append(sep);
            }

            int lastSep1 = sb.lastIndexOf(sep);
            return lastSep1 > 0?sb.substring(0, lastSep1):sb.toString();
        }
    }

    public static <T> List<T>[] subList(List<T> src, int preBatchCount) {
        int count = src.size();
        int batchCount = (count + preBatchCount - 1) / preBatchCount;
        List[] result = new List[batchCount];

        for(int index = 0; index < batchCount; ++index) {
            int begin = index * preBatchCount;
            int end = (index + 1) * preBatchCount;
            if(end > count) {
                end = count;
            }

            result[index] = src.subList(begin, end);
        }

        return result;
    }

    public static <T, E> List<T> convertList(Collection<E> src, CollectionUtil.Converter<E, T> converter, boolean includeNull) {
        if(CollectionUtils.isEmpty(src)) {
            return Collections.emptyList();
        } else {
            ArrayList result = new ArrayList(src.size());
            Iterator var4 = src.iterator();

            while(true) {
                while(var4.hasNext()) {
                    Object e = var4.next();
                    Object t = converter.convert(e);
                    if(!includeNull && t != null) {
                        result.add(t);
                    } else {
                        result.add(t);
                    }
                }

                return result;
            }
        }
    }

    public static <K, V> Map<K, V> transMap(List<V> list, CollectionUtil.Converter<V, K> fun) {
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        } else {
            HashMap maps = new HashMap();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Object v = var3.next();
                if(null != v) {
                    maps.put(fun.convert(v), v);
                }
            }

            return maps;
        }
    }

    public interface Converter<Object, T> {
        T convert(java.lang.Object var1);
    }
}
