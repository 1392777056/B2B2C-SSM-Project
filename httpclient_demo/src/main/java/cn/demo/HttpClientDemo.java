package cn.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/27 16:01
 */
public class HttpClientDemo {

    public static void main(String[] args) throws Exception {

        // 1.打开浏览器
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 2.使用Get
        HttpGet httpGet = new HttpGet("http://www.baidu.com/s?wd=php");
        // 小知识 --- 如果使用httpPost提交的话，必须使用头 为了安全，防止恶意攻击
        // httpPost.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36");
        // 3.访问
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        // 4.获取状态码
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 4.1获取内容
            HttpEntity entity = httpResponse.getEntity();
            String string = EntityUtils.toString(entity, "UTF-8");
            System.out.println(string);
        }
        // 5.关闭流
        httpResponse.close();
        httpClient.close();
    }

}
