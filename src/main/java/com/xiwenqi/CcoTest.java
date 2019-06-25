package com.xiwenqi;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 */
public class CcoTest implements PageProcessor {
    //   列表信息要匹配的正则表达式
    // public static final String URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";
    //  图片信息要匹配的正则表达式
     public static final String URL_POST = "https://cc0-cn/images/cco-\\w+\\.html";

    private Site site = Site
            .me()
            .setDomain("https://cc0.cn")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        //列表页
        if (page.getUrl().regex("https://cc0.cn/yujinxiang-786/page_\\d+").match()) {
//            缩略图
//            page.addTargetRequests(page.getHtml().xpath("//div[@id=\"cc0-cn\"]/a/img/@src").all());
//            下载页地址
            page.addTargetRequests(page.getHtml().xpath("//div[@id=\"cc0-cn\"]/a/@href").all());
            List<String> pages = page.getHtml().links().regex("page_\\d+").all();
            for(String pae : pages){
                pae = "https://cc0.cn/yujinxiang-786/" + pae;
            }
            page.addTargetRequests(pages);
            //文章页
        } else {
            page.putField("Title", page.getHtml().xpath("//*[@id=\"layout\"]/div[2]/div[1]/h1"));
            page.putField("download", page.getHtml().xpath("//ul[@class='xiazai']/a/@href"));

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CcoTest()).addUrl("https://cc0.cn/yujinxiang-786/page_2")
                .run();
    }
}
