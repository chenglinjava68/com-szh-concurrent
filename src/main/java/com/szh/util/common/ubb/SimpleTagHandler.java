package com.szh.util.common.ubb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.szh.util.common.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTagHandler implements UBBTagHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTagHandler.class);
    private static final Set<String> SUPPORTED_TAG_SET = new HashSet(Arrays.asList(new String[]{"b", "i", "u", "face", "size", "color", "quote", "url", "img", "*", "fly", "list", "move", "align", "code", "sign", "ps", "spans"}));

    public SimpleTagHandler() {
    }

    public String[] parseTag(String s, boolean isEmpty) {
        if(isEmpty) {
            return null;
        } else {
            String tag = s;
            String attr = "";
            int idx = s.indexOf(61);
            if(idx > 0) {
                tag = s.substring(0, idx).replaceAll("^[　 ]+|[　 ]+$", "").replaceAll("&gt;|&quot;|&amp;|&lt;|&nbsp;", "").trim();
                attr = s.substring(idx + 1).replaceAll("^[　 ]+|[　 ]+$", "").replaceAll("&gt;|&quot;|&amp;|&lt;|&nbsp;", "").trim();
            } else {
                idx = s.indexOf(58);
                if(idx >= 0) {
                    tag = s.substring(0, idx);
                    attr = s.substring(idx + 1);
                }
            }

            String tmp = tag.toLowerCase();
            return SUPPORTED_TAG_SET.contains(tmp)?new String[]{tag, attr}:new String[]{tag};
        }
    }

    public String compose(String tag, String[] attr, String data, boolean isEmpty) {
        if(!checkAttr(tag, attr)) {
            return "";
        } else if("b".equalsIgnoreCase(tag)) {
            return "<strong>" + data + "</strong>";
        } else if("i".equalsIgnoreCase(tag)) {
            return "<em>" + data + "</em>";
        } else if("u".equalsIgnoreCase(tag)) {
            return "<span style=\'text-decoration: underline;\'>" + data + "</span>";
        } else if("size".equalsIgnoreCase(tag)) {
            return "<span style=\"font-size:" + attr[0] + "\">" + data + "</span>";
        } else if("color".equalsIgnoreCase(tag)) {
            return "<span style=\"color:" + attr[0] + "\">" + data + "</span>";
        } else if("url".equals(tag)) {
            String lastIndex1;
            if(attr != null && attr.length >= 0) {
                lastIndex1 = attr[0].trim();
            } else {
                lastIndex1 = "javascript:void(0);";
            }

            return "<a href=\'" + lastIndex1 + "\' target=_blank>" + deLinkImg(data) + "</a>";
        } else {
            int lastIndex;
            String sourceImgFileName;
            String sourceImgUrl;
            String imageBoundary;
            int width;
            int height;
            if("img".equalsIgnoreCase(tag)) {
                lastIndex = data.lastIndexOf(47);
                if(lastIndex <= 0 || !data.contains("dajie.com") && !data.contains("bpimg") && !data.contains("dajieimg.com")) {
                    return "";
                } else {
                    sourceImgFileName = data.substring(lastIndex + 1);
                    if(!data.contains("dajieimg.com")) {
                        sourceImgFileName = sourceImgFileName.replaceAll("m", "");
                    }

                    sourceImgUrl = data.substring(0, lastIndex) + "/" + sourceImgFileName;
                    if(!checkImgUrl(sourceImgUrl)) {
                        return "";
                    } else {
                        imageBoundary = "";
                        if(attr != null && attr.length == 2) {
                            width = Integer.parseInt(attr[0]);
                            height = Integer.parseInt(attr[1]);
                            imageBoundary = " width=" + (width > 550?550:width) + " height=" + (height > 550?550:height);
                        }

                        return "<a href=\"" + sourceImgUrl + "\" target=_blank title=\"点击看原图\"><img src=\"" + data + "\" " + imageBoundary + " border=0 /></a>";
                    }
                }
            } else if("*".equals(tag)) {
                return "<li>" + data + "</li>";
            } else if("list".equalsIgnoreCase(tag)) {
                return "<ul>" + data + "</ul>";
            } else if("fly".equalsIgnoreCase(tag)) {
                return "<marquee width=90% behavior=alternate scrollamount=3>" + data + "</marquee>";
            } else if("move".equalsIgnoreCase(tag)) {
                return "<marquee scrollamount=3>" + data + "</marquee>";
            } else if("align".equalsIgnoreCase(tag)) {
                String style = !attr[0].isEmpty()?attr[0]:"left";
                return "<div align=\'" + style + "\'>" + data + "</div>";
            } else if("sign".equalsIgnoreCase(tag)) {
                return "<a name=\'" + attr[0] + "\'>" + data + "</a>";
            } else if(!"face".equalsIgnoreCase(tag)) {
                return "quote".equalsIgnoreCase(tag)?"<span class=\'quoteStyle\'>" + data + "</span>":("code".equalsIgnoreCase(tag)?"<span class=\'codeStyle\'>" + data + "</span>":("ps".equalsIgnoreCase(tag)?(StringUtil.isEmpty(attr[0])?"<p>" + data + "</p>":"<p style=\'" + attr[0] + "\'>" + data + "</p>"):("spans".equalsIgnoreCase(tag)?(StringUtil.isEmpty(attr[0])?"<span>" + data + "</span>":"<span style=\'" + attr[0] + "\'>" + data + "</span>"):data)));
            } else {
                lastIndex = data.lastIndexOf(47);
                if(lastIndex > 0 && (data.contains("dajie.com") || data.contains("bpimg") || data.contains("dajieimg.com"))) {
                    sourceImgFileName = data.substring(lastIndex + 1).replaceAll("m", "");
                    sourceImgUrl = data.substring(0, lastIndex) + "/" + sourceImgFileName;
                    if(!checkImgUrl(sourceImgUrl)) {
                        return "";
                    } else {
                        imageBoundary = "";
                        if(attr != null && attr.length == 2) {
                            width = Integer.parseInt(attr[0]);
                            height = Integer.parseInt(attr[1]);
                            imageBoundary = " width=" + (width > 550?550:width) + " height=" + (height > 550?550:height);
                        }

                        return "<a href=\"" + sourceImgUrl + "\" target=_blank title=\"点击看原图\"><img src=\"" + data + "\" " + imageBoundary + " border=0 /></a>";
                    }
                } else {
                    return "";
                }
            }
        }
    }

    private static String deLinkImg(String data) {
        String pattern = "<\\s*a\\s+([^>]*)\\s*>\\s*<\\s*img\\s+([^>]*)\\s*/>\\s*</a>";
        Pattern p = Pattern.compile(pattern, 2);
        Matcher matcher = p.matcher(data);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find()) {
            String replacement = StringUtil.join(retrieveImgHtmlTag(data.substring(matcher.start(), matcher.end())), "");
            matcher.appendReplacement(buffer, replacement == null?"":replacement);
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static List<String> retrieveImgHtmlTag(String data) {
        String pattern = "\\s*<\\s*img\\s+([^>]*)\\s*/>\\s*";
        Pattern p = Pattern.compile(pattern, 2);
        Matcher matcher = p.matcher(data);
        ArrayList imgTags = new ArrayList();

        while(matcher.find()) {
            imgTags.add(data.substring(matcher.start(), matcher.end()));
        }

        return imgTags;
    }

    private static boolean checkImgUrl(String imgUrl) {
        imgUrl = imgUrl.toLowerCase();
        if(!imgUrl.endsWith("png") && !imgUrl.endsWith("jpg") && !imgUrl.endsWith("gif")) {
            LOGGER.warn("###Check imgUrl fail!ImgUrl is {}", imgUrl);
            return false;
        } else if(findOnEvent(imgUrl)) {
            LOGGER.warn("###Check imgUrl fail!Find On event is {}", imgUrl);
            return false;
        } else {
            LOGGER.info("####Check img url is {}", imgUrl);
            if(imgUrl.contains("script")) {
                LOGGER.warn("###Check imgUrl fail!Contains scripts !ImgUrl is {}", imgUrl);
                return false;
            } else if(!imgUrl.contains("\"") && !imgUrl.contains("\'")) {
                return true;
            } else {
                LOGGER.warn("###Check imgUrl fail!Contains  \" or \'!ImgUrl is {}", imgUrl);
                return false;
            }
        }
    }

    private static boolean checkAttr(String tag, String[] attrs) {
        String[] var2 = attrs;
        int var3 = attrs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String attr = var2[var4];
            if(attr != null) {
                attr = attr.toLowerCase();
                if(attr.contains("{") || findOnEvent(attr) || attr.contains("\\") || attr.contains("expression") || attr.contains("script") || attr.contains("\"") || attr.contains("\'") || attr.contains("jquery")) {
                    LOGGER.warn("###Check attr fail! attr is {} tag is :{}", attr, tag);
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean findOnEvent(String content) {
        if(!StringUtil.isEmpty(content)) {
            String regx = "(on\\w*\\x20*=)";
            Pattern pattern = Pattern.compile(regx, 10);
            Matcher matcher = pattern.matcher(content);
            if(matcher.find()) {
                LOGGER.warn("#####Find on event!content is :{}", content);
                return true;
            }
        }

        return false;
    }
}
