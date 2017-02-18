package com.szh.util.common.ubb;

import com.szh.util.common.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class UBBNode {
    public static final String TEXT = "<text>";
    String tag = null;
    String[] attr = null;
    String img = null;
    UBBNode parent = null;
    List<UBBNode> children = null;
    boolean closed = false;
    boolean isEmpty = false;

    UBBNode(UBBNode parent, String tag, String[] attr, String img, boolean isEmpty) {
        this.parent = parent;
        this.tag = tag.toLowerCase();
        if(attr != null) {
            this.attr = (String[])Arrays.copyOf(attr, attr.length);
        }

        this.img = img;
        this.isEmpty = isEmpty;
        this.closed = isEmpty;
        this.children = isEmpty?null:new ArrayList();
    }

    UBBNode(UBBNode parent, String text) {
        String[] text1 = new String[]{text};
        this.parent = parent;
        this.tag = "<text>";
        this.attr = text1;
        this.img = text;
        this.closed = true;
        this.isEmpty = true;
    }

    final void addChild(UBBNode child) {
        this.children.add(child);
    }

    public final String toString() {
        return "[tag=\"" + this.tag + "\",attr=\"" + StringUtil.join(this.attr, "_") + "\",closed=" + this.closed + ",children=" + (this.children != null?"" + this.children.size():"null") + "]";
    }

    final String toString(int i) {
        StringBuilder buf = new StringBuilder();
        int j = i;

        while(true) {
            --j;
            if(j < 0) {
                buf.append(this.toString()).append("\n");
                if(this.children != null && this.children.size() > 0) {
                    Iterator var5 = this.children.iterator();

                    while(var5.hasNext()) {
                        UBBNode aChildren = (UBBNode)var5.next();
                        buf.append(aChildren.toString(i + 2));
                    }
                }

                return buf.toString();
            }

            buf.append(' ');
        }
    }
}
