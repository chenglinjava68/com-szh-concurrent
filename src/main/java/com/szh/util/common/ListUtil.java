package com.szh.util.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.math.NumberUtils;

public class ListUtil {
    public ListUtil() {
    }

    public static List<String> intListToStrList(List<Integer> intList) {
        ArrayList strList = new ArrayList();
        if(intList != null && !intList.isEmpty()) {
            Iterator var2 = intList.iterator();

            while(var2.hasNext()) {
                Integer item = (Integer)var2.next();
                strList.add(String.valueOf(item));
            }
        }

        return strList;
    }

    public static List<Integer> strListToIntList(List<String> strList) {
        if(!strList.isEmpty()) {
            ArrayList intList = new ArrayList(strList.size());
            Iterator var2 = strList.iterator();

            while(var2.hasNext()) {
                String item = (String)var2.next();
                int id = NumberUtils.toInt(item);
                if(id > 0) {
                    intList.add(Integer.valueOf(id));
                }
            }

            return intList;
        } else {
            return Collections.emptyList();
        }
    }

    public static List<Integer> IdStrToIntStr(String idStr) {
        return idStrToIntStr(idStr, ",");
    }

    public static List<Integer> idStrToIntStr(String idStr, String delimiter) {
        List tempList = StringUtil.splitStringByDelimiter(idStr, delimiter, "[0-9]+");
        return strListToIntList(tempList);
    }

    public static <K, V> Map<K, V> sortMap(Map<K, V> targetMap, List<K> sortedList) {
        LinkedHashMap sortedMap = new LinkedHashMap();
        Iterator var3 = sortedList.iterator();

        while(var3.hasNext()) {
            Object k = var3.next();
            if(targetMap.get(k) != null) {
                sortedMap.put(k, targetMap.get(k));
            }
        }

        return sortedMap;
    }
}
