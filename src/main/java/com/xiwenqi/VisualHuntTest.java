package com.xiwenqi;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

public class VisualHuntTest implements PageProcessor {
    public static final String IMAGE_URL = "https://visualhunt.com/photo/\\d+";
    public static final String PAGE_URL = "https://visualhunt.com/photos/cat/\\d+";
    public static final int page_nums = 2;
    private Site site = Site
            .me()
            .setDomain("visualhunt.com")
            .setSleepTime(3000);

    @Override
    public void process(Page page) {
        //  抓取前10页
        //  提取出当前的url  如果匹配的是page_url  提取出url中的最后一个数字  如果大于50  跳过
        //     如果小于50  则加一之后加入抓取队列;
        //  匹配的是图片详情页的话  抓取所需要的信息即可
        String url = page.getUrl().toString();
        //System.out.println("url");
         //System.out.println(page.getUrl().regex(IMAGE_URL).match());
         //System.out.println(page.getUrl().regex(PAGE_URL).match());

       // System.out.println(url.substring(34));  //*[@id="layout"]/div[3]/div[2]/div[1]/div[1]/a[1]/img
        if(page.getUrl().regex(PAGE_URL).match()){
            List<String> imageurls = page.getHtml().xpath("//*[@id=\"layout\"]/div[3]/div[2]/div[1]/div[@class=\"vh-Collage-item\"]/a/@href").all();
            for(String str : imageurls){
                str = "https://visualhunt.com" + str;
                System.out.println(str);
            }
            page.addTargetRequests(imageurls);
            String num = url.substring(34);
            int a = Integer.parseInt(num);
            if(a<page_nums){
                a++;
                page.addTargetRequest("https://visualhunt.com/photos/cat/"+a);
            }
         }
        if(page.getUrl().regex(IMAGE_URL).match()){
            page.putField("Title",page.getHtml().xpath("//*[@id=\"layout\"]/div[2]/div[1]/h1"));
            page.putField("ImageType",page.getHtml().xpath("//*[@id=\"layout\"]/div[2]/div[2]/div/div[5]/div/table/tbody/tr[1]/td[2]"));
            page.putField("Tags",page.getHtml().xpath("//*[@id=\"layout\"]/div[2]/div[1]/div/div"));
            // System.out.println("打印提取元素");


        }
        // System.out.println(page.getHtml());

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new VisualHuntTest()).addUrl("https://visualhunt.com/photos/cat/1")
                .addPipeline(new JsonFilePipeline("D:\\ctbri\\spiderModule"))
                .run();
    }
}
