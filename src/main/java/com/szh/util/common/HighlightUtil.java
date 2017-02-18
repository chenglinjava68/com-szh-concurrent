package com.szh.util.common;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class HighlightUtil {
    public HighlightUtil() {
    }

    public static String highlight(String str, String keywords) {
        if(str != null && !StringUtil.isEmpty(keywords)) {
            String[] keywordsArray = keywords.split(",");
            String[] var3 = keywordsArray;
            int var4 = keywordsArray.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String s = var3[var5];
                str = str.replace(s, "<span class=\"highlight\">" + s + "</span>");
            }

            return str;
        } else {
            return str;
        }
    }

    public static String highlight(String str, String keywords, int length) {
        return highlight(str, keywords, "<span class=\"highlight\">%s</span>", length);
    }

    public static String highlight(String str, String keywords, String highlightPattern, int length) {
        if(str == null) {
            return str;
        } else if(!StringUtil.isEmpty(keywords) && highlightPattern != null) {
            keywords = keywords.replace(".", "").replace("+", "");

            Pattern p;
            try {
                p = Pattern.compile("(" + keywords.replace(",", "|") + ")", 2);
            } catch (PatternSyntaxException var14) {
                p = Pattern.compile("(" + Pattern.quote(keywords) + ")", 2);
            }

            if(p == null) {
                return str.length() > length?str.substring(0, length) + "...":str;
            } else {
                Matcher m = p.matcher(str);
                ArrayList matches = new ArrayList();

                while(m.find()) {
                    int[] sliceLength = new int[]{m.start(), m.end()};
                    matches.add(sliceLength);
                }

                if(matches.size() == 0) {
                    return str.length() > length?str.substring(0, length):str;
                } else {
                    int var15 = 10;
                    int silceSize = length / 20;
                    if(silceSize > matches.size()) {
                        var15 = length / (matches.size() * 2);
                    }

                    StringBuffer sb = new StringBuffer();
                    int position = 0;

                    for(int i = 0; i < matches.size() && i + 1 <= silceSize; ++i) {
                        int[] pos = (int[])matches.get(i);
                        String highlightStr = highlightPattern.replace("%s", str.substring(pos[0], pos[1]));
                        if(position > 0) {
                            if(pos[0] - position <= var15 * 2) {
                                sb.append(str.substring(position, pos[0]));
                            } else {
                                sb.append(str.substring(position, position + var15));
                                sb.append("…").append(str.substring(pos[0] - var15, pos[0]));
                            }
                        } else if(pos[0] - position > var15) {
                            sb.append("…").append(str.substring(pos[0] - var15, pos[0]));
                        } else {
                            sb.append(str.substring(position, pos[0]));
                        }

                        sb.append(highlightStr);
                        position = pos[1];
                    }

                    if(position < str.length() - var15) {
                        sb.append(str.substring(position, position + var15)).append("…");
                    } else {
                        sb.append(str.substring(position));
                    }

                    return sb.toString();
                }
            }
        } else {
            return str.length() > length?str.substring(0, length) + "...":str;
        }
    }
}
