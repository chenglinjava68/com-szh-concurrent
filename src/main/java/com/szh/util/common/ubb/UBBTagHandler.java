package com.szh.util.common.ubb;

public interface UBBTagHandler {
    String[] parseTag(String var1, boolean var2);

    String compose(String var1, String[] var2, String var3, boolean var4);
}