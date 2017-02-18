package com.szh.demo.qrcode;


/**
 * Created by zhihaosong on 16-6-1.
 */
public class TestCode {
    /*public String sendSmsByHBGet(SMSRequest smsRequest) {
        String result = "";
        int billingCount = 0;
        String content = toHexString(channels.getSignatureName() + smsRequest.getContent());
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet httpRequst = new HttpGet(MessageFormat.format("{0}/mt?un={1}&pw={2}&da={3}&sm={4}&dc=15&rd=1", url, channels.getUserName(), channels.getPassword(), smsRequest.getMobile(), content));
            HttpResponse response = client.execute(httpRequst);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity);//取出应答字符串
            result.replaceAll("\r", "");//去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            if (result.contains("id")) {
                result = result.split("=")[1];
                logger.info("sendSmsByHBGet success ------> smsRequest:{} return result:{}", smsRequest.getId(), result);
            } else {
                logger.info("sendSmsByHBGet fail ------> smsRequest:{} return result:{}", smsRequest.getId(), result);
            }
        } catch (Exception e) {
            logger.error("sendSmsByHBGet error:" + e.getMessage(), e);
        }
        try {
            String finalContent = smsRequest.getContent();
            if (!smsRequest.getContent().contains(channels.getSignatureName()) || !smsRequest.getContent().endsWith(channels.getSignatureName())) {
                finalContent = smsRequest.getContent() + channels.getSignatureName();
            }
            billingCount = finalContent.length() <= 70 ? 1 : (int) Math.ceil((double) finalContent.length() / 67);

        } catch (Exception e) {
            logger.info("send billCount --> error:" + e.getMessage(), e);
        }
    }*/
    public static void main(String[] args) {
        System.out.println(1000==1000);
    }

}
