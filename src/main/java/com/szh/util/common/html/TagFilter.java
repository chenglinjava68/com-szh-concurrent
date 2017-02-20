package com.szh.util.common.html;

import com.szh.util.common.StringUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagFilter {
    private final Pattern HTML_TAG_PATTERN = Pattern.compile("<\\s*(/?)\\s*(\\w*)[^>/]*(/?)>");
    private final TagFilter.TagAcceptor tagAcceptor;

    public static TagFilter accept(final String... tags) {
        TagFilter.TagAcceptor tagAcceptor = new TagFilter.TagAcceptor() {
            public boolean accept(String src) {
                String[] var2 = tags;
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String s = var2[var4];
                    if(s.equals(src)) {
                        return true;
                    }
                }

                return false;
            }
        };
        return new TagFilter(tagAcceptor);
    }

    public static TagFilter exclude(final String... tags) {
        TagFilter.TagAcceptor tagAcceptor = new TagFilter.TagAcceptor() {
            public boolean accept(String src) {
                String[] var2 = tags;
                int var3 = var2.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    String s = var2[var4];
                    if(s.equals(src)) {
                        return false;
                    }
                }

                return true;
            }
        };
        return new TagFilter(tagAcceptor);
    }

    private TagFilter(TagFilter.TagAcceptor tagAcceptor) {
        this.tagAcceptor = tagAcceptor;
    }

    public String filter(String src) {
        if(StringUtil.isEmpty(src)) {
            return "";
        } else {
            Matcher matcher = this.HTML_TAG_PATTERN.matcher(src);

            ArrayList str2Removeds;
            String replace;
            for(str2Removeds = new ArrayList(); matcher.find(); str2Removeds.add(new TagFilter.Str2Removed(matcher.start(), matcher.end(), replace))) {
                String stringBuilder = matcher.group(1);
                String i = matcher.group(3);
                String str2Removed = matcher.group(2);
                replace = "";
                if(this.tagAcceptor.accept(str2Removed)) {
                    if(StringUtil.isEmpty(stringBuilder) && StringUtil.isEmpty(i)) {
                        replace = "<" + str2Removed + ">";
                    }

                    if(StringUtil.isNotEmpty(stringBuilder)) {
                        replace = "</" + str2Removed + ">";
                    }

                    if(StringUtil.isNotEmpty(i)) {
                        replace = "<" + str2Removed + "/>";
                    }
                }
            }

            StringBuilder var8 = new StringBuilder(src);
            if(!str2Removeds.isEmpty()) {
                for(int var9 = str2Removeds.size() - 1; var9 >= 0; --var9) {
                    TagFilter.Str2Removed var10 = (TagFilter.Str2Removed)str2Removeds.get(var9);
                    var8.replace(var10.start, var10.end, var10.replace);
                }
            }

            return var8.toString();
        }
    }

    public static class Str2Removed {
        private final int start;
        private final int end;
        private final String replace;

        public Str2Removed(int start, int end, String replace) {
            this.start = start;
            this.end = end;
            this.replace = replace;
        }
    }

    public interface TagAcceptor {
        boolean accept(String var1);
    }
}
