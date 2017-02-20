package com.szh.util.common;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CharacterFilterUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterFilterUtil.class);

    private CharacterFilterUtil() {
    }

    public static String filter(String s, CharacterFilterUtil.CharacterFilterRuleEnum rule) {
        return s != null && !s.isEmpty() && rule != null?rule.getFilter().filter(s):s;
    }

    public static enum CharacterFilterRuleEnum {
        None("r0", new CharacterFilterUtil.CharacterFilterRuleEnum.R0Filter()),
        First("r1", new CharacterFilterUtil.CharacterFilterRuleEnum.R1Filter()),
        Second("r2", new CharacterFilterUtil.CharacterFilterRuleEnum.R2Filter());

        private final String ruleName;
        private final CharacterFilterUtil.CharacterFilterRuleEnum.Filter filter;

        private CharacterFilterRuleEnum(String ruleName, CharacterFilterUtil.CharacterFilterRuleEnum.Filter filter) {
            this.ruleName = ruleName;
            this.filter = filter;
        }

        public String getRuleName() {
            return this.ruleName;
        }

        public CharacterFilterUtil.CharacterFilterRuleEnum.Filter getFilter() {
            return this.filter;
        }

        public static CharacterFilterUtil.CharacterFilterRuleEnum parse(String ruleName) {
            CharacterFilterUtil.CharacterFilterRuleEnum[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CharacterFilterUtil.CharacterFilterRuleEnum rule = var1[var3];
                if(rule.getRuleName().equalsIgnoreCase(ruleName)) {
                    return rule;
                }
            }

            return null;
        }

        private static class R2Filter implements CharacterFilterUtil.CharacterFilterRuleEnum.Filter {
            private static final int[] FORBIDDEN = new int[]{33, 34, 36, 37, 38, 39, 60, 62, 92, 94, 96, 123, 125, 126};

            private R2Filter() {
            }

            public String filter(String s) {
                String r1Result = CharacterFilterUtil.CharacterFilterRuleEnum.First.getFilter().filter(s);
                if(r1Result != null && !r1Result.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    char[] result = r1Result.toCharArray();
                    int var5 = result.length;

                    for(int var6 = 0; var6 < var5; ++var6) {
                        char c = result[var6];
                        if(Arrays.binarySearch(FORBIDDEN, c) < 0) {
                            sb.append((char)c);
                        }
                    }

                    String var8 = sb.toString();
                    if(!var8.equals(s)) {
                        CharacterFilterUtil.LOGGER.warn("r2 filtered : [{}] -> [{}]", s, var8);
                    }

                    return var8;
                } else {
                    return r1Result;
                }
            }
        }

        private static class R1Filter implements CharacterFilterUtil.CharacterFilterRuleEnum.Filter {
            private R1Filter() {
            }

            public String filter(String s) {
                if(s != null && !s.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    char[] result = s.toCharArray();
                    int var4 = result.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        char c = result[var5];
                        if(c > 8 && (c < 11 || c > 12) && (c < 14 || c > 31) && c != 127) {
                            sb.append((char)c);
                        }
                    }

                    String var7 = sb.toString();
                    if(!var7.equals(s)) {
                        CharacterFilterUtil.LOGGER.warn("r1 filtered : [{}] -> [{}]", s, var7);
                    }

                    return var7;
                } else {
                    return s;
                }
            }
        }

        private static class R0Filter implements CharacterFilterUtil.CharacterFilterRuleEnum.Filter {
            private R0Filter() {
            }

            public String filter(String s) {
                return s;
            }
        }

        private interface Filter {
            String filter(String var1);
        }
    }
}
