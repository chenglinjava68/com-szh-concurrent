package com.szh.util.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamXssStripper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamXssStripper.class);
    private static final Set<String> FORBIDDEN_TAGS = new HashSet(Arrays.asList(new String[]{"script", "embed", "object", "style", "meta"}));
    private static final int REGEX_FLAGS_SI = 34;
    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("([0-9a-z]+)(\\s*)=(\\s*)", 34);
    private static final Pattern FORBIDDEN_ATTRIBUTES = Pattern.compile("^(on.+)$", 34);
    private static final Pattern FORBIDDEN_KEYWORDS = Pattern.compile(".*(javascript|mocha|eval|vbscript|livescript|expression|url).*", 34);
    private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", 34);
    private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", 34);

    private ParamXssStripper() {
    }

    public static String filter(String s) {
        if(s != null && !"".equals(s)) {
            StringBuilder sb = new StringBuilder();
            int index = 0;
            boolean from = true;
            int end = -1;

            for(int i = 0; i < s.length(); ++i) {
                if(s.charAt(i) == 60) {
                    int var8 = i;
                    int temp = i;

                    for(int j = i + 1; j < s.length(); ++j) {
                        if(s.charAt(j) == 62) {
                            if(!s.substring(temp, j + 1).contains("<!--") || !s.substring(temp, j + 1).contains("-->")) {
                                i = j;
                                end = j;
                                break;
                            }

                            temp = j;
                        }
                    }

                    sb.append(s.substring(index, i));
                    if(end <= i) {
                        sb.append(s.substring(i));
                        return sb.toString();
                    }

                    index = i + 1;
                    sb.append(processTag(s.substring(var8 + 1, end)));
                }
            }

            sb.append(s.substring(index));
            if(!s.equals(sb.toString())) {
                LOGGER.warn("{} ==>> {}", s, sb.toString());
            }

            return sb.toString();
        } else {
            return s;
        }
    }

    private static String processTag(String s) {
        Matcher m = P_END_TAG.matcher(s);
        String name;
        if(m.find()) {
            name = m.group(1).toLowerCase();
            return FORBIDDEN_TAGS.contains(name)?"":"<" + s + ">";
        } else {
            m = P_START_TAG.matcher(s);
            if(m.find()) {
                name = m.group(1);
                String body = m.group(2);
                if(FORBIDDEN_TAGS.contains(name.toLowerCase())) {
                    return "";
                } else {
                    body = body.replaceAll("[\\r\\n\\t\u0000]*", "").replaceAll("/\\*.*\\*/", "").replaceAll("<!--.*-->", "");
                    return body.length() <= 0?"<" + s + ">":(checkAttributes(body)?"<" + s + ">":"");
                }
            } else {
                return "<" + s + ">";
            }
        }
    }

    private static boolean checkAttributes(String str) {
        int start = 0;

        ParamXssStripper.Attribute attr;
        do {
            attr = findAttribute(str, start);
            if(attr == null) {
                return true;
            }

            start = attr.end;
        } while(!FORBIDDEN_ATTRIBUTES.matcher(attr.name).find() && !FORBIDDEN_KEYWORDS.matcher(attr.value).find());

        return false;
    }

    private static ParamXssStripper.Attribute findAttribute(String str, int fromIndex) {
        Matcher m = ATTRIBUTE_PATTERN.matcher(str);
        if(!m.find(fromIndex)) {
            return null;
        } else {
            ParamXssStripper.Attribute attribute = new ParamXssStripper.Attribute(m.start(), 0, m.group(1), "");
            if(m.end() >= str.length()) {
                attribute.end = str.length();
                return attribute;
            } else {
                int start = m.end();
                int end = start + 1;
                char startChar = str.charAt(start);
                char cur;
                if(startChar != 39 && startChar != 34) {
                    while(end < str.length()) {
                        cur = str.charAt(end);
                        if(cur == 32 || cur == 12288) {
                            break;
                        }

                        ++end;
                    }

                    attribute.end = end;
                    attribute.value = str.substring(start, end);
                    return attribute;
                } else {
                    for(; end < str.length(); ++end) {
                        cur = str.charAt(end);
                        if(cur == 92) {
                            ++end;
                        } else if(cur == startChar) {
                            ++end;
                            break;
                        }
                    }

                    end = end > str.length()?str.length():end;
                    attribute.end = end;
                    attribute.value = str.substring(start, end);
                    return attribute;
                }
            }
        }
    }

    private static class Attribute {
        int start;
        int end;
        String name;
        String value;

        Attribute(int start, int end, String name, String value) {
            this.start = start;
            this.end = end;
            this.name = name;
            this.value = value;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Attribute");
            sb.append("{start=").append(this.start);
            sb.append(", end=").append(this.end);
            sb.append(", name=\'").append(this.name).append('\'');
            sb.append(", value=\'").append(this.value).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
