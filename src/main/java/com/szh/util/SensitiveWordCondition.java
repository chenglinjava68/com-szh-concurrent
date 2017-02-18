package com.szh.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SensitiveWordCondition {
    private static Logger logger = LoggerFactory.getLogger(SensitiveWordCondition.class);

    private static HashMap rootMap = new HashMap();
    private static Set ignoreSymbol = null;

    public SensitiveWordCondition() {
        try {
            //   createNode(readKeyWord());
        } catch (Exception e) {
            logger.error("SensitiveWordFilter init error:{}" + e.getMessage(), e);
        }
    }

    public Set<String> filter(String input) {
        Set<String> keyWords = new HashSet<String>();
        Map current = rootMap;
        StringBuffer sb = new StringBuffer();
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ignoreSymbol != null && ignoreSymbol.contains(String.valueOf(ch))) {
                continue;  //忽略字符 跳过
            }
            if (current.containsKey("isEnd") && current.get("isEnd").equals("1")) {
                keyWords.add(sb.toString());
                current = rootMap;
                sb.setLength(0);
            }
            //匹配成功
            if (current.containsKey(ch)) {
                current = (Map) current.get(ch);
                sb.append(ch);
                if (sb.length() == 1) {
                    index = i;  //qw色狼   'w'存在于树中，匹配到'色'失败，然后从'色'开始，否则将无法匹配到'色狼'
                    //index 记录匹配上的位置
                }
            }
            //匹配失败
            else {
                if (sb.length() > 0) {
                    i = index;  //只有sb中有匹配内容并且匹配失败后，才回归
                }
                current = rootMap;
                sb.setLength(0);
            }
        }
        //输入内容匹配完成　但sb中还有未完成判断的字符
        if (current.containsKey("isEnd") && current.get("isEnd").equals("1")) {
            keyWords.add(sb.toString());
        }
        return keyWords;
    }

    private static Map createNode(Set<String> keyWords) {
        Map currentMap = new HashMap();
        Iterator<String> iterator = keyWords.iterator();
        while (iterator.hasNext()) {
            currentMap = rootMap;
            String keyWord = iterator.next();
            for (int i = 0; i < keyWord.length(); i++) {
                char ch = keyWord.charAt(i);
                if (currentMap.containsKey(ch)) {
                    currentMap = (Map) currentMap.get(ch);
                } else {
                    Map addMap = new HashMap();
                    addMap.put("isEnd", "0");  //isEnd为0代表没有构造出完整词
                    currentMap.put(ch, addMap);
                    currentMap = addMap;  //当前指针指向新添加Map
                }
                if (i == keyWord.length() - 1) {
                    currentMap.put("isEnd", "1");  //重写　isEnd为１代表到词节点出现了完整词
                }
            }
        }
        ignoreSymbol = readIgnoreSymbol();
        logger.info("keyWordsEncode size:{} ignoreSymbol size:{}", rootMap.size(), ignoreSymbol.size());
        return currentMap;
    }

    private Set<String> readKeyWord() throws IOException {
        Set keyWords = new HashSet();
        try {
            System.out.println("readKeyWord start");
            BufferedReader br = new BufferedReader(new FileReader(SensitiveWordCondition.class.getResource("keyWordsEncode.txt").getFile()));
            String line = "";
            while ((line = br.readLine()) != null) {
                keyWords.add(line);
            }
            System.out.println("readKeyWord end size:" + keyWords.size());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("readKeyWord error:{}" + e.getMessage(), e);
        }
        return keyWords;
    }

    private static Set<String> readIgnoreSymbol() {
        //空格 在vim下打出的
        Set ignoreSymbol = new HashSet();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(SensitiveWordCondition.class.getResource("ignoreSymbol.txt").getFile()));
            String line = "";
            while ((line = br.readLine()) != null) {
                ignoreSymbol.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("readIgnoreSymbol error:{}" + e.getMessage(), e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("readIgnoreSymbol error:{}" + e.getMessage(), e);
        }
        return ignoreSymbol;
    }

    public void reloadSensitiveWord() {
        try {
            createNode(readKeyWord());
        } catch (Exception e) {
            logger.error("SensitiveWordFilter reloadSensitiveWord error:{}" + e.getMessage(), e);
        }
    }

    public String test(String name) {
        if (name == null)
            return name;

        if (!name.startsWith("/"))

        {
            Class c = SensitiveWordCondition.class;
            while (c.isArray()) c = c.getComponentType();
            String baseName = c.getName();
            int index = baseName.lastIndexOf('.');
            if (index != -1) {
                name = baseName.substring(0, index).replace('.', '/')
                        + "/" + name;
            }
        } else

        {
            name = name.substring(1);
        }

        return name;
    }

    public static void main(String[] args) throws IOException {
        SensitiveWordCondition sensitiveWordFilter = new SensitiveWordCondition();
        System.out.println(SensitiveWordCondition.class.getResource(""));
        System.out.println(SensitiveWordCondition.class.getResource(""));
        System.out.println(sensitiveWordFilter.getClass());
        System.out.println(sensitiveWordFilter.getClass().getClassLoader());
        System.out.println(sensitiveWordFilter.getClass().getClassLoader().getResource(""));
        System.out.println(sensitiveWordFilter.getClass().getClassLoader().getResource("/"));

        System.out.println("===========");

        System.out.println(sensitiveWordFilter.getClass().getResource("keyWordsEncode.txt"));
        System.out.println(sensitiveWordFilter.getClass().getResource("/main/resources/ignoreSymbol.txt"));
        System.out.println(sensitiveWordFilter.getClass().getClassLoader().getResource("main/java/com/szh/util/keyWordsEncode.txt"));
        System.out.println(sensitiveWordFilter.getClass().getClassLoader().getResource("main/resources/ignoreSymbol.txt"));
        System.out.println(sensitiveWordFilter.getClass().getClassLoader().getResource("Main.class"));
        System.out.println(sensitiveWordFilter.test("/ignoreSymbol.txt"));



      /*  System.out.println(SensitiveWordCondition.class.getResource("/"));
        long start = System.currentTimeMillis();
        System.out.println("init cost:" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        System.out.println(sensitiveWordFilter.filter("收到BB样B样代开发票 你好，本公司代理各地国地税发票\n" +
                "普通-专用，点数低，质量保证！联系13632624378，郑先生。qq：1730773476\n" +
                "有餐饮住宿，租赁，技术服务，咨询，商品销售，印刷,图文制作 建筑安装，材料等等\n" +
                "有需要请保留，打扰请见谅。"));

        System.out.println("filter cost:" + (System.currentTimeMillis() - start));
        System.out.println(sensitiveWordFilter.main.java.com.szh.test.test("ignoreSymbol.txt"));

        System.out.println(sensitiveWordFilter.main.java.com.szh.test.test("/main.resources/ignoreSymbol.txt"));*/

    }
}
