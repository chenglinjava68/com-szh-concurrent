package com.szh.demo;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhihaosong on 17-2-23.
 */
public class Test {
    ExecutorService pool = Executors.newFixedThreadPool(5);
    ExecutorService pool2 = Executors.newSingleThreadExecutor();
    ExecutorService pool23 = Executors.newCachedThreadPool();

    //批量接口仅共发送非实时邮件。
   /* public int sendEmailBatch(List<Map<String, String>> mailDataList) {
        int sendCount = 0;
        for (Map<String, String> mailData : mailDataList) {
            try {
                mailData.put(MailTplKeys.KEY_TO_UID, mailData.containsKey(MailTplKeys.KEY_TO_UID) ? mailData.get(MailTplKeys.KEY_TO_UID) : "0");
                mailData.put(MailTplKeys.KEY_TO_UNAME, mailData.containsKey(MailTplKeys.KEY_TO_UNAME) ? mailData.get(MailTplKeys.KEY_TO_UNAME) : "");
                mailData.put(MailTplKeys.KEY_MESSAGE_SERIAL_ID + ".0", mailData.containsKey(MailTplKeys.KEY_MESSAGE_SERIAL_ID) ? mailData.get(MailTplKeys.KEY_MESSAGE_SERIAL_ID) : "0");
                mailData.put(MailTplKeys.KEY_PRIORITY, mailData.containsKey(MailTplKeys.KEY_PRIORITY) ? mailData.get(MailTplKeys.KEY_PRIORITY) : "14");
                //mailDataId = sendMail(Integer.valueOf(mailData.get(MailTplKeys.KEY_TO_UID)), mailData.get(MailTplKeys.KEY_FROM_NAME), mailData.get(MailTplKeys.KEY_FROM_EMAIL), mailData);
            } catch (Exception e) {
                logger.error("sendEmail ERROR:{}" + e.getMessage(), e);
            }
        }
        // mailDataList.add(mailData);
        //  return send(toUid, fromName, fromEmail, mailDataList, false);
        return 3;
    }*/
   /* public int sendBatch(List<Map<String, String>> mailDataList) {
        long start = System.currentTimeMillis();
        for (int i = mailDataList.size() - 1; i >= 0; i--) {
            Map<String, String> mailData = mailDataList.get(i);
            String tplId = mailData.get(MailTplKeys.KEY_TPL_VERSION_ID);
            String toEmail = mailData.get(MailTplKeys.KEY_TO_EMAIL);
            String toUid = mailData.get(MailTplKeys.KEY_TO_UID);
            if (StringUtil.isEmpty(tplId) || StringUtil.isEmpty(toEmail)) {
                logger.info("checkData --> lack key {}. maildata:{}", MailTplKeys.KEY_TO_EMAIL, mailData.toString());
                mailDataList.remove(mailData);
                continue;
            }
            if (REJECT_DOMAINS.contains(toEmail.split("@")[1])) {//查看被拒的邮箱
                logger.info("send --> reject email domain. to_email:{} toUid:{}", toEmail, toUid);
                mailDataList.remove(mailData);
                continue;
            }
            setDefaultParam(mailData);
            String category = mailTemplates.get(tplId);
            if (frequentLimit.isFrequent(toEmail, tplId)) {
                logger.info("send --> overLimit. tpl_version_id:{} toEmail:{} to_uid:{}", tplId, toEmail, toUid);
                mailData.put("read_status", ReturnCode.OVER_SEND_LIMIT + "");
            } else if (Integer.valueOf(toUid) != 0 && mailManager.isInBlacklist(category, Integer.valueOf(toUid))) {
                mailData.put("read_status", ReturnCode.REJECTED_BY_USER + "");
                logger.info("send --> in blacklist. tpl_version_id:{} category:{} to_uid:{}", tplId, category, toUid);
            } else {
                mailData.put("read_status", "0");
            }
            frequentLimit.sendIncrease(toEmail, tplId);
        }
        int result = mailManager.insertBatch(mailDataList);
        logger.info("sendEmailBatch --> cost time:{}.", System.currentTimeMillis() - start);
        return result;
    }*/
    public static void iteratorRemove() {
        List<String> students = new ArrayList<String>();
        students.add("aa");
        students.add("bb");
        students.add("cc");
        students.add("dd");
        students.add("aa");
        students.add("bb");
        students.add("aa");
        students.add("ee");
        students.add("ff");
        students.add("gg");

        System.out.println(students);
        Iterator<String> stuIter = students.iterator();
        while (stuIter.hasNext()) {
            String student = stuIter.next();
            if ("aa".equals(student))
                stuIter.remove();//这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
        }
        System.out.println(students);
    }


    private static void updateMap(Map map) {
        map.put("a", "aaaaa");
        map.put("b", "bbbb");        map.put("c", "bbbb");
        map.put("d", "ddd");
    }
     static void updateMap(String str) {
       str.substring(5);
    }
    public static void main(String[] args) {
        List<Map<String, String>> test = new LinkedList<Map<String, String>>();
        Map<String, String> m = new HashMap<String, String>();
        m.put("a", "1");
        m.put("b", "2");
        updateMap(m);
        test.add(m);
        String res = JSON.toJSONString(m);
        System.out.println(res);
        iteratorRemove();
        String str = "szfsdfsdfsdfsdf11111";
        updateMap(str);
        System.out.println(str);


    }
}
