package com.szh.util.common.ubb;


import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UBBDecoder {
    private static final int MAX_SEARCH_LENGTH_FOR_END_BRACKET = 1024;

    public UBBDecoder() {
    }

    public static String decode(String input, UBBTagHandler tagHandler, UBBDecoder.DecoderMode mode) {
        return decode(input, tagHandler, mode, true);
    }

    public static String decode(String input) {
        return decode(input, new SimpleTagHandler(), UBBDecoder.DecoderMode.MODE_CLOSE);
    }

    public static String decode(String inputUbbString, UBBTagHandler tagHandler, UBBDecoder.DecoderMode mode, boolean convertBr) {
        if(inputUbbString.indexOf(91) >= 0 && inputUbbString.indexOf(93) >= 0) {
            StringBuffer buffer = new StringBuffer();
            char[] charOfUbbString = inputUbbString.toCharArray();
            int len = charOfUbbString.length;
            int position = 0;
            UBBNode root = new UBBNode((UBBNode)null, "", (String[])null, "", false);
            UBBNode node = root;
            Stack stack = new Stack();
            stack.push(root);

            while(true) {
                while(position < len) {
                    char show = charOfUbbString[position];
                    if(convertBr && show == 10) {
                        buffer.append("<br />");
                        ++position;
                    } else if(convertBr && show == 9) {
                        buffer.append("&nbsp;&nbsp;&nbsp;&nbsp;");
                        ++position;
                    } else if(convertBr && show == 32) {
                        buffer.append("&nbsp;");
                        ++position;
                    } else if(show != 91) {
                        buffer.append(show);
                        ++position;
                    } else {
                        int pattern = indexOf(charOfUbbString, ']', position + 1, 1024);
                        if(pattern == -1) {
                            buffer.append(show);
                            ++position;
                        } else {
                            String matcher;
                            if(charOfUbbString[position + 1] != 47) {
                                String[] var20;
                                UBBNode var21;
                                if(charOfUbbString[pattern - 1] == 47) {
                                    matcher = (new String(charOfUbbString, position + 1, pattern - position - 2)).trim();
                                    var20 = tagHandler.parseTag(matcher, true);
                                    if(var20 != null && var20.length == 3) {
                                        addTextChild(node, buffer);
                                        var21 = new UBBNode(node, var20[0].toLowerCase(), var20[1].split(","), new String(charOfUbbString, position, pattern + 1 - position), true);
                                        node.addChild(var21);
                                        position = pattern + 1;
                                        continue;
                                    }
                                }

                                matcher = (new String(charOfUbbString, position + 1, pattern - position - 1)).trim();
                                var20 = tagHandler.parseTag(matcher, false);
                                if(var20 != null && var20.length == 2) {
                                    addTextChild(node, buffer);
                                    var21 = new UBBNode(node, var20[0].toLowerCase(), var20[1].split(","), new String(charOfUbbString, position, pattern + 1 - position), false);
                                    node.addChild(var21);
                                    position = pattern + 1;
                                    stack.push(var21);
                                    node = var21;
                                } else {
                                    buffer.append('[');
                                    ++position;
                                }
                            } else if(charOfUbbString[position + 2] == 93) {
                                buffer.append("[/]");
                                position += 3;
                            } else {
                                matcher = (new String(charOfUbbString, position + 2, pattern - position - 2)).trim().toLowerCase();
                                matcher = tagHandler.parseTag(matcher, false)[0];
                                int ss = 1;
                                boolean nd = false;

                                for(UBBNode nd1 = node; nd1 != null; ++ss) {
                                    if(nd1.tag.equals(matcher)) {
                                        nd = true;
                                        break;
                                    }

                                    nd1 = nd1.parent;
                                }

                                if(!nd) {
                                    buffer.append("[/");
                                    position += 2;
                                } else {
                                    addTextChild(node, buffer);

                                    for(; ss-- > 0; node = (UBBNode)stack.pop()) {
                                        if(mode.compareTo(UBBDecoder.DecoderMode.MODE_CLOSE) == 0) {
                                            node.closed = true;
                                        }
                                    }

                                    node.closed = true;
                                    node = node.parent;
                                    position = pattern + 1;
                                }
                            }
                        }
                    }
                }

                addTextChild(node, buffer);
                String var17 = decodeNode(tagHandler, root);
                Pattern var18 = Pattern.compile("\\[br\\]", 2);
                Matcher var19 = var18.matcher(var17);
                return var19.replaceAll("<br />");
            }
        } else {
            inputUbbString = inputUbbString.replaceAll("[　 ]+", "&nbsp;");
            if(convertBr) {
                inputUbbString = inputUbbString.replaceAll("\n", "<br/>").replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
            }

            return inputUbbString;
        }
    }

    private static void addTextChild(UBBNode node, StringBuffer buf) {
        if(buf.length() > 0) {
            node.addChild(new UBBNode(node, buf.toString()));
            buf.setLength(0);
        }

    }

    private static String decodeNode(UBBTagHandler tagHandler, UBBNode node) {
        StringBuilder outputBuffer = new StringBuilder();
        if("<text>".equalsIgnoreCase(node.tag)) {
            outputBuffer.append(node.img);
        } else {
            List childNodeList;
            if(!node.closed) {
                outputBuffer.append(node.img);
                childNodeList = node.children;
                if(childNodeList != null && childNodeList.size() > 0) {
                    Iterator tmp = childNodeList.iterator();

                    while(tmp.hasNext()) {
                        UBBNode aChildNodeList = (UBBNode)tmp.next();
                        outputBuffer.append(decodeNode(tagHandler, aChildNodeList));
                    }
                }
            } else {
                childNodeList = node.children;
                StringBuilder tmp1 = new StringBuilder();
                if(childNodeList != null && childNodeList.size() > 0) {
                    Iterator aChildNodeList2 = childNodeList.iterator();

                    while(aChildNodeList2.hasNext()) {
                        UBBNode aChildNodeList1 = (UBBNode)aChildNodeList2.next();
                        tmp1.append(decodeNode(tagHandler, aChildNodeList1));
                    }
                }

                outputBuffer.append(tagHandler.compose(node.tag, node.attr, tmp1.toString(), node.isEmpty));
            }
        }

        return outputBuffer.toString();
    }

    private static int indexOf(char[] cc, char c, int idx, int len) {
        int end = idx + len;
        if(end > cc.length) {
            end = cc.length;
        }

        for(int i = idx; i < end; ++i) {
            if(cc[i] == c) {
                return i;
            }
        }

        return -1;
    }

    public static enum DecoderMode {
        MODE_IGNORE(0),
        MODE_CLOSE(1);

        private int value;

        private DecoderMode(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
