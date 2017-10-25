package com.vain.manager.util;

import com.vain.manager.entity.BNews;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by vain on 2017/8/18.
 * 百度网页抓取帮助类 java爬虫实验 可删除
 */
public class ReptileUtils {
    private static Log log = LogFactory.getLog(ReptileUtils.class);
    /**
     * 等待数据连接时间
     */
    private static final int connectionTimeout = 10000;
    /**
     * 等待数据返回时间
     */
    private static final int soTimeout = 20000;
    private static final int soLinger = 60;

    /**
     * 通过get方式获取网页数据
     *
     * @param url
     * @param contentEncode
     * @return
     */
    private static String getHttp(String url, String contentEncode) {
        //1.设置连接时长等默认参数
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, connectionTimeout);
        HttpConnectionParams.setSoTimeout(httpParameters, soTimeout);
        HttpConnectionParams.setLinger(httpParameters, soLinger);
        //2.使用默认的配置的httpclient
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        String html = "";
        try {
            //3.使用get方法
            HttpGet httpget = new HttpGet(url);
            //4.执行请求，获取响应
            HttpResponse response = httpClient.execute(httpget);
            //看请求是否成功，这儿打印的是http状态码
            log.debug("reponseCode:" + response.getStatusLine().getStatusCode());
            //5.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity httpEntity = response.getEntity();


            if (httpEntity != null) {
                InputStream instream = httpEntity.getContent();
                if (instream == null) {
                    return null;
                }
                try {
                    if (httpEntity.getContentLength() > Integer.MAX_VALUE) {
                        throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
                    }
                    int i = (int) httpEntity.getContentLength();
                    if (i < 0) {
                        i = 4096;
                    }

                    Reader reader = new InputStreamReader(instream, contentEncode);
                    // Reader reader = new InputStreamReader(instream);
                    CharArrayBuffer buffer = new CharArrayBuffer(i);
                    char[] tmp = new char[1024];
                    int l;
                    while ((l = reader.read(tmp)) != -1) {
                        buffer.append(tmp, 0, l);
                    }
                    return buffer.toString();
                } finally {
                    instream.close();
                    EntityUtils.consume(httpEntity);
                }
            }
        } catch (Exception e) {
            log.error(ReptileUtils.class.getName() + " getMethod error:", e);
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        String encoding = getEncoding(html);
        if (StringUtils.isEmpty(encoding))
            try {
                return new String(html.getBytes(), encoding);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        return html;

    }


    /**
     * 通过meta的标签获取网页的字符编码
     *
     * @param html
     * @return
     */
    private static String getEncoding(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select("meta");
        String meta = elements.toString();
        if (null == meta)
            return null;
        Pattern pattern = Pattern.compile("charset=(\\w){1,13}");
        Matcher matcher = pattern.matcher(meta);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        if (!list.isEmpty() && list.size() == 1) {
            String[] split = list.get(0).split("=");
            if (split.length == 2)
                return split[1];
            return null;
        }
        return null;
    }

    //获取实时热点
    public static List<BNews> getNowNews() {
        String url = "http://top.baidu.com/buzz?b=1&c=513&fr=topbuzz_b341_c513";
        String http = ReptileUtils.getHttp(url, "gb2312");
        Document html = Jsoup.parse(http);
        Elements select = html.select("table.list-table > tbody > tr");
        List<BNews> news = new ArrayList<>();
        for (int i = 1; i < 15 && select.size() > 10; i++) {
            if (StringUtils.isEmpty(select.get(i).select("td.keyword > a.list-title").text()))
                continue;
            BNews entity = new BNews(select.get(i).select("td.keyword > a.list-title").text(),
                    select.get(i).select("td.keyword > a.list-title").attr("href"),
                    Integer.valueOf(select.get(i).select("td.first > span").first().text()),
                    Integer.valueOf(select.get(i).select("td.last > span").text()),
                    select.get(i).select("td.last > span").hasClass("icon-rise"),
                    select.get(i).select("td.keyword > span").hasClass("icon-new")
            );
            news.add(entity);
        }
        return news;
    }

    //获取今日热点
    public static List<BNews> getTodayNews() {
        String url = "http://top.baidu.com/buzz?b=341&c=513&fr=topbuzz_b1";
        String http = ReptileUtils.getHttp(url, "gb2312");
        Document html = Jsoup.parse(http);
        Elements select = html.select("table.list-table > tbody > tr");
        select.removeAttr("tr.item-tr");
        //select.remove(select.select("tr.item-tr"));
        List<BNews> news = new ArrayList<>();
        for (int i = 1; i < 11 && select.size() > 10; i++) {
            if (select.get(i).hasClass("item-tr"))
                continue;
            BNews entity = new BNews(select.get(i).select("td.keyword > a.list-title").text(),
                    select.get(i).select("td.keyword > a.list-title").attr("href"),
                    Integer.valueOf(select.get(i).select("td.first > span").text()),
                    Integer.valueOf(select.get(i).select("td.last > span").text()),
                    select.get(i).select("td.last > span").hasClass("icon-rise"),
                    select.get(i).select("td.keyword > span").hasClass("icon-new")
            );
            news.add(entity);
        }
        return news;
    }
}
