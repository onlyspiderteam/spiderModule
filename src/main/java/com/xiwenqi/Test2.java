package com.xiwenqi;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test2 implements PageProcessor{

    public static InputStream inStream = null;
    Site site = Site.me().setRetryTimes(3).setSleepTime(3000);

    public Site getSite() {
        // TODO Auto-generated method stub
        return site;
    }

    public void process(Page page) {
        // TODO Auto-generated method stub
        //将匹配到的链接都存储到links集合中
        List<String> links = page.getHtml().regex("http://img\\d+\\.sogoucdn\\.com/app/a/\\S+\\.jpg").all();
        //遍历links集合中的链接，然后下载
        for(int i = 0; i < links.size(); i++){
            String link = links.get(i);
            try {
                URL url = new URL(link);
                URLConnection con = url.openConnection();
                inStream = con.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len = 0;
                while((len = inStream.read(buf)) != -1){
                    outStream.write(buf,0,len);
                }
                inStream.close();
                outStream.close();
                File file = new File("d://ideaproject//webmagic//"+i+".jpg");	//图片下载地址
                FileOutputStream op = new FileOutputStream(file);
                op.write(outStream.toByteArray());
                op.close();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        //系统配置文件
        // System.setProperty("selenuim_config", "D:\\jse-workspace\\WebMagicTest\\NovelSpider\\TuPian\\First\\config.ini");
        Spider.create(new Test2())
                .addUrl("http://pic.sogou.com/pics?query=%CD%BC%C6%AC&p=40230500&st=255&mode=255")	//要爬取的总链接
                .setDownloader(new SeleniumDownloader("D:\\ChromeDriver\\chromedriver_win32(2)\\chromedriver.exe"))	//模拟启动浏览器
                .thread(2)	//线程
                .run();
    }
}
