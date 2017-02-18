package com.szh.util.common;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlFilterUtil {
    private static final String regxpForHtml = "<([^>]*)>";
    private static final String regxpForImgTag = "<\\s*img\\s+([^>]*)\\s*>";
    private static final String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\"";
    private static final String regxpForTag = "&lt;|&lt|&gt;|&gt|&quot;|&quot|&amp;|&amp|&apos;|&apos";
    private static final char[] HEADER_NAME_SPECIAL_CHARS = new char[]{'(', ')', '<', '>', '@', ',', ';', ':', '\\', '\"', '/', '[', ']', '?', '=', '{', '}'};

    private HtmlFilterUtil() {
    }

    public static String escapeHTMLTag(String html) {
        if(!hasHTMLTag(html)) {
            return html;
        } else {
            StringBuilder filtered = new StringBuilder(html.length());

            for(int i = 0; i <= html.length() - 1; ++i) {
                char c = html.charAt(i);
                switch(c) {
                case '\"':
                    filtered.append("&quot;");
                    break;
                case '&':
                    filtered.append("&amp;");
                    break;
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                default:
                    filtered.append(c);
                }
            }

            return filtered.toString();
        }
    }

    public static boolean hasHTMLTag(String html) {
        boolean flag = false;
        if(html != null && html.length() > 0) {
            for(int i = 0; i <= html.length() - 1; ++i) {
                char c = html.charAt(i);
                switch(c) {
                case '\"':
                    flag = true;
                    break;
                case '&':
                    flag = true;
                    break;
                case '<':
                    flag = true;
                    break;
                case '>':
                    flag = true;
                }

                if(flag) {
                    break;
                }
            }
        }

        return flag;
    }

    /** @deprecated */
    @Deprecated
    public static String replaceTag(String input) {
        return escapeHTMLTag(input);
    }

    /** @deprecated */
    @Deprecated
    public static boolean hasSpecialChars(String input) {
        return hasHTMLTag(input);
    }

    public static String filterAllHtmlTag(String html) {
        Pattern pattern = Pattern.compile("<([^>]*)>");
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();

        for(boolean result1 = matcher.find(); result1; result1 = matcher.find()) {
            matcher.appendReplacement(sb, "");
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String filterSpecialHtmlTag(String html, String htmlTagName) {
        String regxp = "<\\s*" + htmlTagName + "\\s+([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(html);
        StringBuffer sb = new StringBuffer();

        for(boolean result1 = matcher.find(); result1; result1 = matcher.find()) {
            matcher.appendReplacement(sb, "");
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String replaceSpecialHtmlTag(String html, String beforeTag, String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
        String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
        Pattern patternForTag = Pattern.compile(regxpForTag);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
        Matcher matcherForTag = patternForTag.matcher(html);
        StringBuffer sb = new StringBuffer();

        for(boolean result = matcherForTag.find(); result; result = matcherForTag.find()) {
            StringBuffer sbreplace = new StringBuffer();
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if(matcherForAttrib.find()) {
                matcherForAttrib.appendReplacement(sbreplace, startTag + matcherForAttrib.group(1) + endTag);
            }

            matcherForTag.appendReplacement(sb, sbreplace.toString());
        }

        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    public static String filterHeaderName(String name) {
        if(name != null && name.length() != 0) {
            StringBuilder sb = new StringBuilder(name.length());

            for(int i = 0; i < name.length(); ++i) {
                char c = name.charAt(i);
                if(c > 32 && c < 127 && Arrays.binarySearch(HEADER_NAME_SPECIAL_CHARS, c) < 0) {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String filterHeaderValue(String value) {
        if(value != null && value.length() != 0) {
            StringBuilder sb = new StringBuilder(value.length());

            for(int i = 0; i < value.length(); ++i) {
                char c = value.charAt(i);
                if(c >= 32 && c < 127) {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String filterTag(String content) {
        if(content != null && content.length() != 0) {
            content = content.replaceAll("<p>", "\n");
            content = content.replaceAll("&nbsp;|&nbsp", " ");
            content = content.replaceAll("&lt;|&lt|&gt;|&gt|&quot;|&quot|&amp;|&amp|&apos;|&apos", "");
            content = content.replaceAll("<([^>]*)>", "");
            return content;
        } else {
            return null;
        }
    }

    static {
        Arrays.sort(HEADER_NAME_SPECIAL_CHARS);
    }
}
