package alipayConfig;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092700604701";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCShEVMHGha95nVIjUWRWf/GRP6edgIQ+vfFult3bLA1tq+jGkTd3H5KSAjhxR/vaAzuDoxvOVBWZekfqYFvrThMWTqnKDL+ibl/BMcvewCPzD0FlKUkjafAZmeo0ybB+/ZIaDhStKZtm5Ns8xi3Q6IHbJrU9tHc+IyMZKlhc94HfKZ6YOuu2XwudNL1m4WXotnJTmboobZlVfwYiM8MBArKTiD8ow8q2S6qdmOINkBU+gdw+qmhU8iQ8ytyIJPC1b87SXvE+Kq2PIpD2g/X+0p+zkvYZyYiJaiVo07LN65Q3Qzc0PXlLbAcGAtR7HHAmKuWrEOZxFy7kGdMQt4YQpXAgMBAAECggEAE2xVg3GApA6O5iJSx1EMKFU6wMnGSdI3QqvW5imtuMLC3N/gTiRXh6BcElml7glFY5XYoR9ZhQBLihMAD2Lot7HEnxWEe4X+Ah8YqYbD8shNxQ3whKGdfBbKNq2PSj4THcXHfhZdNONqXAKp5bFIa+MD9BZ5y08hfWX0+txyVInv2n8p92cExrvReBPdQ1ZUXDEtJhwrzQFVRlGKmHJEzJLLYtFlOZEtw8izuAt+zQu84QNzsCnmfIds44tYR+F+UKKqpr551DxL6ZMtZLvmC18CrTncSriLw9Yf2fmf9q7bIi0YzzVvvBsHPMzMPHB0CTx7F6IdVBctwWXyYKIgAQKBgQDh1XxiJd08VnAJpXxUhjfnlkwe/Mn2Yxcb7cl+tXj9rTvEfs/lvqBlFDpGADy5NawL3LDDRCb9Hvoi/fLIlOBJbxtwI/Yws37fKA1wlaBhBwjRz881GNi2ymaLXb3SACLE3n2XK54iJJNQSCMtS2ErX3Hrigtyyzfu73MVMS5VAQKBgQCmFn0HaUBWdQaZclWzC5D2PwFftaww9WYff1S56zo3xV1syGmaYgnW9XMgf0yV4Qg3Vzk0AcqPHM7Om8qImdI8owF3SLm62A4qZ1cYborl+F3z+k+mETlZHS+LhuDZU1QFGzMcg/D0RMx2cQu8UFp72uqHop0BelHZQxWKl68nVwKBgCuuIzTIphX5kgoLnBStMTr18xs7QcUKPXoFcCe16gUG8Lx82rFRQazLUMohWZ1pKDHTKc1a7T+gMjl6bO8m/Iq3ltMF4aRh03RORuszxaV+utH6I9XdTG1ukI78Pq7Tn+X8MsZ+hUONV4D7svIb1luRRnOMa9J0RAlO/y9mJ9QBAoGAQ4CoWDbC22C8yejaQ+bTiAGbgVbJISzb0LljqhyckMY6blgV85UdAqzHmdGjQtcvC677pNOFjBzdQMO40UQTWmm10tFQC0x8lWdi0p86/LSxGtQFQy2E7V8bT84F3M4tO9YOM+y8JctAfnL7nhHadLRI1c+VmGAqm+7T9nwnyAkCgYADkQzS0ghzX1wuydtpePGOvt15+TuIY/P9EQEKYyEWHNdddJEdwmjX29Yj9jlzQoU9gHrYZARDDzqtvtORSIBwGjZbCj7ol/G5G5o4cQ3pat+9jTWyeRgrp3x7sHfIQT2XooigCNdqoTNw/vuRkgWaTPrWWX9l4gwpPKq1GMl+hg==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAouib+E8J/uvplsYlO8L1WFi0/gw2XCjXh6IW3O45kme0/Y3HdLr+MkcOhLTMcSoG25TkYqNUfT/adPcFhoCOIImAE4VI2x9EJoQCJGZRedA3AhTp7DivhtBRJR33XQ92wO13VO9d3u4h2ISlTyslzPDkVJVp9VJ2+UwH6sxPeHuTRKdvlxu792WA3a0Iu3kSrSVmOSt9KJMKwKSrQ7fu96yBw0SxusxaAb5vH/XLnAQlcXQz9nPalU4Hl59Zj7h0u3fU8abJan5IXSJptdHEvgXcwWOMnYn1aPDZX6FiroxzruzC1R67z+YC71gbJQCdXFoOzmJUUbI9VY5qTmZ88QIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost:8080/claim_listBySelf.action";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    // 支付宝网关
    public static String log_path = "C:\\";



    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
