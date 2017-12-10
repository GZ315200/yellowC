package org.unistacks.crawler;

import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.unistacks.queue.Producer;
import org.unistacks.vo.News;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mazean on 2017/9/4
 */
public class SinaNews {


    private String host = "www.fortunechina.com";
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";



    public static Date cronDateTime(int num){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateFrom = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, num);
            String yesterday = dateFormat.format(calendar.getTime());
            dateFrom = dateFormat.parse(yesterday);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //参考日期
        return  dateFrom;
    }
    /**
     * 头条
     *
     * @return
     */
    public boolean getCrawlerData(String keyword,boolean isAsync) {



        Document document = null;
        String title = null;
        String posttime = null;
        String source = null;
        String article_url = null;
        for (int pageNo = 1; pageNo <= 10000; pageNo++) {



            String referer = null;


//q=%D6%D0%B9%FA%D2%F8%D0%D0&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=2&pf=2131425461&ps=2134309112&dpc=1
            try {
                String key = URLEncoder.encode(keyword, "gbk");
                if (pageNo == 1) {
                    referer = "http://search.sina.com.cn/?q=" + key + "&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page=2&pf=2131425461&ps=2134309112&dpc=1";
                } else {
                    referer = "http://search.sina.com.cn/?q=" + key + "&range=all&c=news&sort=time&col=&source=&from=&country=&size=&time=&a=&page="+ pageNo + "&pf=2131425461&ps=2134309112&dpc=1";
                }
                String url = new StringBuilder("http://search.sina.com.cn/?")
                        .append("q=").append(key)
                        .append("&range=all&")
                        .append("c=news&")
                        .append("sort=time&")
                        .append("source=&")
                        .append("from=&")
                        .append("country=&")
                        .append("size=&")
                        .append("time=&")
                        .append("a=&")
                        .append("page=").append(pageNo)
                        .append("&pf=2131425461")
                        .append("&ps=2134309112")
                        .append("&dpc=1")
                        .toString();
                document = Jsoup.connect(url)
                        .header("host", host)
                        .header("Referer",referer)
                        .header("User-Agent", userAgent)
                        .timeout(30 * 1000)
                        .get();
//                http://search.sina.com.cn/?q=B%25B9%25C9&range=all&c=news&sort=time&source=&from=&country=&size=&time=&a=&page=2&pf=2131425461&ps=2134309112&dpc=1

                System.out.println(url);
                Elements resultList = document.select("div[class=box-result clearfix]");
                for (Element item : resultList) {
                    article_url = item.select("h2 > a").attr("href");
                    title = item.select("h2 > a").text();
                    String string = item.select("span[class=fgray_time]").text();
                    posttime = string.substring(string.indexOf("2017"));
//                    source = string.substring(string.indexOf("2017")-10);
                    System.out.println(string);
                    System.out.println(article_url);
                    System.out.println(title);
//                    System.out.println(source);
                    System.out.println(posttime);

//                    DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                    java.util.Date date = dateFormat1.parse(posttime);
//                    if (date.before(cronDateTime(-7))) {
//                        pageNo = 1000000;
//                        continue;
//                    }
                    boolean flag = getCrawlerContent(article_url,title,posttime,source,isAsync);

                    if (flag){
                        System.out.println("爬取成功");
                    } else {
                    System.out.println("爬取失败");
                    }
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }

        }
        return true;
    }



    public static String getContent(String value) {
        String reg = "[^\\x00-\\xff]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(value);
        String result = matcher.replaceAll(" ").trim();
        return result;
    }


//    public static String getSource(String value){
//        String reg = "";
//        Pattern pattern = Pattern.compile(reg);
//        Matcher matcher = pattern.matcher(value);
//        String result = matcher.replaceAll("").trim();
//        return result;
//    }

    /**
     * 获得内容
     *
     * @param
     * @param title
     * @return
     */
    public boolean getCrawlerContent(String article_url, String title, String posttime, String source,boolean isAsync) {
        Producer producer = new Producer();
        Random random = new Random();
        int messageNo = (int) (System.currentTimeMillis()/1000) + random.nextInt(8);
        try {
            Document document = Jsoup.connect(article_url)
                    .header("User-Agent", userAgent)
                    .timeout(30 * 1000)
                    .get();
            Elements content = document.select("div[id=articleContent]");
            String article_content = content.select("p").text()
                    .replace("，","")
                    .replace("。","")
                    .replace("!","")
                    .replace("?","")
                    .replace("、","")
                    .replace("：","")
                    .replace("（","")
                    .replace("）","")
                    .replaceAll("\\s*", "")
                    .replaceAll("\\d+","");

//            FileOutputStream outputStream = fileInputStream("/Users/mazean/project/temp/content.txt");
            News news = new News();
            news.setContent(article_content);
            news.setId(UUID.randomUUID().toString().replace("-",""));
            news.setPosttime(posttime);
            news.setSource(source);
            news.setTitle(title);
            ObjectMapper objectMapper = new ObjectMapper();
            String value = objectMapper.writeValueAsString(news);
            System.out.println(value);
//            producer.sentMessage(value,messageNo,"igeek",isAsync);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return true;
    }


    public static void main(String[] args) {
        SinaNews sinaNews = new SinaNews();
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        List<String> keywordList = new ArrayList<String>();
//        keywordList.add("中国银行");
//        keywordList.add("央行");
//        keywordList.add("A股");
//        keywordList.add("B股");
//        keywordList.add("中信银行");
        keywordList.add("赤字");
        keywordList.add("美联储");
        keywordList.add("中国建设银行");
        keywordList.add("中国农业银行");
        keywordList.add("招商银行");
        keywordList.add("工商银行");
        keywordList.add("浦发银行");
        keywordList.add("交通银行");
        keywordList.add("花旗银行");
        keywordList.add("经济");

        for (String keyword : keywordList) {
            sinaNews.getCrawlerData(keyword,isAsync);
        }

        System.out.println("***************************************done");

    }




//    /**
//     * inputStream to String
//     *
//     * @param
//     * @return
//     * @throws IOException
//     */
//    public static void write2file(byte[] content,OutputStream outputStream) throws IOException {
//        IOUtils.write(content, outputStream);
//    }
//
//
//    /**
//     * 读取文件
//     *
//     * @param path 本地文件路径
//     * @return fileInputStream
//     */
//    private static FileOutputStream fileInputStream(String path) {
//        FileOutputStream fileOutputStream = null;
//        try {
////        File file = new File(path);
////        if (!file.exists()){
////            file.createNewFile();
////        }
//            fileOutputStream = new FileOutputStream(path);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fileOutputStream;
//    }

}
