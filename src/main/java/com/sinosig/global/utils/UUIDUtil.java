package com.sinosig.global.utils;

import java.util.UUID;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: UUID工具类
 * @author: wangzi
 */
public class UUIDUtil {
    private UUIDUtil() {
    }
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
