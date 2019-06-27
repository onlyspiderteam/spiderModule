package com.ctbri;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;

public class WebmagicUtilP implements PageProcessor {
    private Site site = Site.me().setSleepTime(1000).setRetryTimes(3);
    int j = 1;

    public void process(Page page) {
        Html html = page.getHtml();
        //System.out.println(html);
        System.out.println("获取某一页id为cco-cn标签下所有a标签下的img标签的src属性");
        List<String> srcs = html.xpath("//*[@id=\"cc0-cn\"]/a/img/@src").all();
        // String src = html.xpath("//*[@id=\"cc0-cn\"]/a/img/@src").toString();
        for (String src:srcs){
        System.out.println(src);
        }

        //*[@id="cc0-cn"]/a[1]/img
        //*[@id="cc0-cn"]/a[2]/img
        //获取图片
       /*while(page.getHtml().xpath("//span[@class=\"RichText ztext CopyrightRichText-richText\"]/figure["+j+"]/img").css("img","data-original").toString()!=null) {
           String string1 = page.getHtml().xpath("//span[@class=\"RichText ztext CopyrightRichText-richText\"]/figure[" + j + "]/img").css("img", "data-original").toString();
           j++;
           System.out.println(string1);
       }*/



    }
    /**
     * 设置属性
     */
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        System.out.println("开始爬取...");
//        String url = "https://www.zhihu.com/question/29784516/answer/54897151";
         String  url = "https://cc0.cn/shaonv-1561/";
        //启动爬虫
        Spider.create(new WebmagicUtilP())
                //添加初始化的URL
                .addUrl(url)
                .addPipeline(new ConsolePipeline())
                .thread(1)
                //运行
                .run();
        System.out.println("爬取结束");
    }


}
