package com.buer.job.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * Created by jiewu on 2021/1/30
 */
@Slf4j
public class WeChatUtils {
  public static String decrypt(String encryptedData, String iv, String sessionKey) throws Exception {
    // 被加密的数据
    byte[] dataByte = Base64.decode(encryptedData);
    // 加密秘钥
    byte[] keyByte = Base64.decode(sessionKey);
    // 偏移量
    byte[] ivByte = Base64.decode(iv);
    // 如果密钥不足16位，那么就补足.
    int base = 16;
    if (keyByte.length % base != 0) {
      int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
      byte[] temp = new byte[groups * base];
      Arrays.fill(temp, (byte) 0);
      System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
      keyByte = temp;
    }
    // 初始化
    Security.addProvider(new BouncyCastleProvider());
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
    SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
    AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
    parameters.init(new IvParameterSpec(ivByte));
    cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
    byte[] resultByte = cipher.doFinal(dataByte);
    if (null != resultByte && resultByte.length > 0) {
      return new String(resultByte, "UTF-8");
    }
    throw null;
  }
}
