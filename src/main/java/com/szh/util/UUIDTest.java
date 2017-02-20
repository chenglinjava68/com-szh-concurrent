package com.szh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhihaosong on 16-11-21.
 */

public class UUIDTest {

    public static Pattern smsArgsPattern = Pattern.compile("\\$\\{([a-zA-Z0-9\\._-]+)\\}");

    public static JSONArray tplAnalysis(String tplInfo) {
        JSONArray tplInfoArray = JSON.parseArray(tplInfo);

        for (int i = 0; i < tplInfoArray.size(); i++) {
            //{"tplId":"10000011","JOB":"111","COMPANY":"${UANME}"}
            JSONObject tplInfoObject = tplInfoArray.getJSONObject(i);
            Iterator<Map.Entry<String, Object>> iter = tplInfoObject.entrySet().iterator();
            Set<String> temp = new HashSet<String>();
            while (iter.hasNext()) {
                Map.Entry entry = iter.next();
                //如果所填参数重仍然有变量 将变量名称提取 值为空
                Matcher matcher = smsArgsPattern.matcher(entry.getValue().toString());
                while (matcher.find()) {
                    //不要迭代时加入元素
                    temp.add(matcher.group(1));
                }
            }
            for (String str : temp) {
                tplInfoObject.put(str, "");
            }
        }
        return tplInfoArray;
    }

    public static void main(String[] args) {
        String str = "[{\"tplId\":\"10016\",\"BUSINESS_SERIAL_ID\":\"djgk_111601\",\"content\":\"亲爱的${UNAME},hr${HRNAME}在您住址附近招${JOBNAME}相关管理岗位薪资高于你的预期，点击:URL{http://www.dajie.com/app/phone/download?id=6}查看。\"},{\"tplId\":\"10016\",\"BUSINESS_SERIAL_ID\":\"djgk_111602\",\"content\":\"亲爱的${UNAME},您住址附近hr${HRNAME}正在招${JOBNAME}相关管理岗位，待遇好。点击：url{http://www.dajie.com/app/phone/download?id=6}查看。\"},{\"tplId\":\"10016\",\"BUSINESS_SERIAL_ID\":\"djgk_111603\",\"content\":\"亲爱的${UNAME}，hr${HRNAME}对你的资料很感兴趣，推荐投递他们公司的${JOBNAME}。去看看：url{http://www.dajie.com/app/phone/download?id=6}\"}]";
        System.out.println(UUIDTest.tplAnalysis(str));
        String str1 = "亲爱的${UNAME},hr${HRNAME}在您住址附近招${JOBNAME}相关管理岗位薪资高于你的预期，点";
        Matcher matcher = smsArgsPattern.matcher(str1);
        matcher.find();
        System.out.println(matcher.group(1));
        matcher.find();
        matcher.find();
        matcher.find();

        System.out.println(matcher.group(1));
        Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                UUID uuid = UUID.randomUUID();
                System.out.println(uuid);
            }
        }, 100, 500, TimeUnit.MILLISECONDS);


    }
}
