package com.sinosig.global.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * Copyright © 2020 SIG.Co.Ltd. All rights reserved.
 *
 * @describe: 加密解密的公共类
 * @author: Aladdin.Cao
 */
@Slf4j
public class DES3Util {

    private DES3Util() {
    }
    // 密钥  
    private static final String secretKey = "密钥" ;
    // 向量  
    private static final String iv = "向量" ;
    // 加解密统一使用的编码方式  
    private static final Charset encoding = StandardCharsets.UTF_8;

    /**
     * 3DES加密
     */
    public static String encryptDES(String plainText)  {
        Key deskey;
        String base64Str="";
        try{
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte [] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            base64Str = Base64.encode(encryptData);
        }catch (Exception e){
            log.error("DES3加密异常，原文:{},异常信息:{}",plainText,e);
        }
        return base64Str;
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     */
    public static String decryptDES(String encryptText) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte [] decryptData = cipher.doFinal(Base64.decode(encryptText));
        return new String(decryptData, encoding);
    }

}  