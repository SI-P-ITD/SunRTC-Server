package com.sinosig.global.utils;


import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 字符串工具类
 * @author: Aladdin.Cao
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 获得一个UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        return s.replace("-", "");
    }

    public static int getRamdom() {
		return (int) (Math.random() * 100000000);
    }

    /**
     * 将object 转string
     */
    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 生成短信验证码
     */
    public static String getSmsCode() {
        return (new SecureRandom().nextInt(999999 - 100000 + 1) + 100000) + "";
    }

    // 半角符号
    private static final String hSymbol = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,./:;<=>?@[\\]^_`{|}-~ ";
    // 全角符号
    private static final String zSymbol = "ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＥＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ０１２３４５６７８９！”＃＄％＆’（）＊＋，．／：；＜＝＞？＠［￥］＾＿‘｛｜｝－～　";

    /**
     * 单引号取拼接拼接字段串 <br>
     * 注意：在IN中使用时要自己在字段串两端各加入一个单引号!
     * <p>
     * 被拼接字符源
     *
     * @param key 被拼接字段名
     * @return 拼接成110001','210001','310001','410001形式的字符串
     */
    public static String joinString(List<Map<String, Object>> list, String key) {
        String deptListString = "";
        StringBuilder stringBuffer = new StringBuilder();

        if (!list.isEmpty()) {
            for (Map<String, Object> oneDept : list) {
                stringBuffer.append("'").append((String) oneDept.get(key)).append("',");
            }
            deptListString = stringBuffer.toString();
            if (!"".equals(deptListString)) {
                deptListString = deptListString.substring(1, deptListString.length() - 2);
            }

        }
        return deptListString;
    }


    /**
     * 将字符串数组，转换为字符串List
     *
     * @param array 字符串数组
     * @return 字符串List
     */
    public static List<String> array2List(String[] array) {
        List<String> list = new ArrayList<>();
        for (String s : array) {
            if (s == null) {
                continue;
            }
            list.add(s.trim());
        }
        return list;
    }

    // 将字符串中的半角字符转换成全角字符
    public static String toSBC(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        String tmp = str;
        for (int i = 0; i < hSymbol.length(); i++) {
            tmp = tmp.replace(hSymbol.charAt(i), zSymbol.charAt(i));
        }
        return tmp;
    }

    // 将字符串中的全角字符转换成半角字符
    public static String toDBC(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        String tmp = str;
        for (int i = 0; i < zSymbol.length(); i++) {
            tmp = tmp.replace(zSymbol.charAt(i), hSymbol.charAt(i));
        }
        return tmp;
    }


    public static boolean isNum(Object o) {
        String s = o.toString();
        if (s.length() > 1 && s.toLowerCase().charAt(s.length() - 1) == 'e') {
            s = doubleTo(o);
        }
        String regex = "^[1-9][0-9]*\\.[0-9]+$|^[1-9][0-9]*$|^0+\\.[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        char c = s.charAt(0);
        if (c == '+' || c == '-') {
            s = s.substring(1);
        }
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static String doubleTo(Object value) {
        DecimalFormat decimalFormat = new DecimalFormat("###0.000000");// 格式化设置
        return decimalFormat.format(value);
    }


    public static boolean isNull(String pa) {
        if (pa != null) {
            String pas = pa.trim();
            return "".equalsIgnoreCase(pas) || pas.length() < 1;
        } else {
            return true;
        }
    }

    public static boolean isEmpty(Object pa) {
        if (pa != null) {
            String pas = pa.toString().trim();
            return "".equalsIgnoreCase(pas) || pas.length() < 1;
        } else {
            return true;
        }
    }

    public static boolean isValue(String pa) {
        if (pa != null) {
            String pas = pa.trim();
            return "".equalsIgnoreCase(pas) || "*".equalsIgnoreCase(pa) || pas.length() < 1;
        } else {
            return true;
        }
    }

    public static String trim(String pas) {
        if (isNull(pas)) {
            return "";
        } else {
            return pas.trim();
        }
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]*)?$");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    public static String find(List<String> listYm, String code) {
        if (!listYm.isEmpty()) {
            for (String s : listYm) {
                if (s.equalsIgnoreCase(code)) {
                    return s;
                }
            }
        }
        return "";
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 获取调用者的类名、方法名、形成固定格式的日志信息
     */
    public static String getErrorInfo(String errMsg) {
        StackTraceElement[] ste = new Exception().getStackTrace();
        return "" + "class:" + ste[1].getClassName() + ",method:" + ste[1].getMethodName() + ",error info {" + errMsg + "}";
    }

    /**
     * 获取文件名称后缀
     */
    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 根据保单链接获取文件名称
     */
    public static String getFileName(String policyUrl) {
        return policyUrl.substring(policyUrl.lastIndexOf("/") + 1, policyUrl.lastIndexOf("."));
    }


	 /*
    校验过程：
    1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
    2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
    3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
    */

    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if (bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard) {
        if (nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postCode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isPostCode(String postCode) {
        String reg = "[1-9]\\d{5}";
        return Pattern.matches(reg, postCode);

    }

}