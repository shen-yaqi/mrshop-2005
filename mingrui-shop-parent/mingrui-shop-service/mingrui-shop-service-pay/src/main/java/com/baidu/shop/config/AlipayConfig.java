package com.baidu.shop.config;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName AlipayConfig
 * @Description: TODO
 * @Author shenyaqi
 * @Date 2021/3/16
 * @Version V1.0
 **/
public class AlipayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static final String app_id = "2016101600702852";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static final String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCFCJ5BbsEy1qFhUUSQLdKZQHHQDldiKQD+n7VcvdxiUn8nyJvGV8DTmFlc3TjRF1PgyFLxwtDpHhZxfwlhjPLDaN5MK4/YzJIntMud22adD1ev0nx4EsdRvBVZ+JkWHLukDaglJqLhxNpvv5O1fMwlHQ/cCaGiJiW+pedH+uaHMQ6Xla3aw+Aqk/DNlfihR4JiMJC4RdykXhG5+Jy8cZt5riNECfE7zm5nYUpLE1fbXCILHFB7m0L9aQbFsA683lU9U8by+/t2Vk86zRNocUIFwLI6klEb3P7j5otkgFek/sJXHl9F4gd4pSr+FpfhuJfi0IZL6vqYAdgi0+DpIqFVAgMBAAECggEAR7XYkM7h58SYYzwKaGm67w81CoDgi7IsrhRrD93sMTflAQ37IlRlReoGqa86FEOt3Co/w8BPGoo0cOOYq/PtXHZn9ck5lhTX5/1mEOaWjq0ZiFY+7/63YJCxNbly09+TKiCOddA41thlIv2y4o6uAIeE6AhtD1ne2MDXRg6yO0bjR4CajDbRixGNS1eHisPAfYw61FrGTXwNbaQSgEsee0oKSh+2cWLnsRSI4oUOuD4lldu2MvieME+oOGwnvSj6K7DkRBlpNFIwR1atPPRb+vHqrxz638mc+VRGPPx7aR+EClaX+hKD/AunJf5WmwFWpft2G2qIxf5Agz2TbZ1fQQKBgQDnpHsinsAWxtuQoy3t/xxfRlMQzbyKFGWP8Y9Ud3VtNglv7pG9nDkjsgx1qbW/SH5HsNp9A4bEnB/cgfq5Bazgk/uih/mTHe9Jt048XJy+ES+e0FyUqouQzEDmoU+XYv+BwvmlhpXZOcSdwt39yuBpWP81YD+exlL6YJVootPBsQKBgQCTBba4y4YAlI+9zoc9KWjKCffcKTF3qbrRQ/2nsJei0pI3sQXYsyV71xsl5n29dsA3NdG03nm/vqUGspt4eaSM/3gv3ETBTmnuKU4JkJcm4MbrroEOopOqYInilMcxxWg5naOeQZK0N2MGZZEMoirrJI9zhh5NdbO3wQ4Qp4W+5QKBgGuORdZ3W723q+9mtUbSs3vRjXQ58xcyDU5e3SxdI1sIQi2E2ort5OIcJAjmOguNP4VOKsTtREA6NdkPSTlr1y+Xxrla5ZlXxum1gVApuGXQWQomuTYWUiNzk5cCw5AJWZYI0B35dc6dMhwGhUj4UGbZ+fXwsNmqQJEdE4K4SBahAoGBAI3OeQuM4tc0c+Bzj+XiRvx42OU1pkzxHQwzNDnGDrwxtm6ZBDabtI/aTokj5CwWmhemhXqbC3tyeuIW6/Yr/hcxYc9n+bkp1OquVGLNX71UGQnB6yEelqdDLjupnOfYrErGTcabINFKiuzkkh9JW3Qqc2LN/fRgjtOyFcriihIxAoGAIpZ8Z2S3umzYL0YA67yDw7zMUwdsoGRAieizY0EM3Bsp0HAsNWdDmLJ06lbll6wQBHPvg4aYboCKW16o/M8m3UBbVqVWX3MeSEXQ2J0NqkIzjjBtvsxD6NyOty4QfgF24D3zOoTNWvM+sDYiQ0lezPM30fHvSXgG1KeKmVdScjE=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static final String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkiy64E7hlFatIPF2GkxabPGV/ZQyKD6vs0n9SdYCKHk7wnEODEuKE6WFyevZuiTb98t9ZK+vuVmsaUlF95dpLeU/JwABSe9CF/x0d3OSVMMFvjzECTJXlZHzRKw8PA0M9RwgUYmBX82etMLK3QY//IEakxyzWoWES4RQypoPdgIzSTICTeN1XNazTuRBaIQLjSm6GpKRUd+0r0ei9K8hBK8b+9UFoPsdKyd/qKcI76jOEe/w+qimsT8vrCRv3KNTyrOc/wwYtZkJEVVcv/sOFGrFZweW9lpB5bMqR4h0dkhPxAkpYfRd8Brj52d8yLDVDM9Xvl4wXkdVZLcNTBm7cQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String notify_url = "http://localhost:8900/pay/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static final String return_url = "http://localhost:8900/pay/returnUrl";

    // 签名方式
    public static final String sign_type = "RSA2";

    // 字符编码格式
    public static final String charset = "utf-8";

    // 支付宝网关
    public static final String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static final String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

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
