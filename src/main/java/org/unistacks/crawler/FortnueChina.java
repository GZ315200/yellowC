package org.unistacks.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Gyges on 2017/11/16
 */
public class FortnueChina {






    public static void main(String[] args) throws IOException {

        String textArray = null;
        String orgName = null;
        String rankNum = null;

        String industry = null;

        for (int i = 1; i <= 5000;i ++) {
            String url = "http://www.fortunechina.com/china500/" + i +"/2017";
            Document document = Jsoup.connect(url).get();//
            orgName = document.select("div[class=articlehead] > p").text();
            Elements elements = document.select("div[id=articleabs]");
            for (Element element : elements ) {
                rankNum = element.select("strong").text().trim();
            }
            Elements element = document.select("div[class=thisyeardata]");
            for (Element ele : element) {
                textArray = ele.select("span[class=txt-14]").text();
//                manager =  ele.select("span[class=txt-14]").text();
            }

            Elements elements1 = document.select("div[class=competitorsdata]");

            for (Element element1 : elements1) {
                industry = new String(element1.select("p[class=rankcolname]")
                        .text().replace("- 行业横向比较","").replace("利润营收总资产","")
                        .replace(" ","").getBytes(),"utf-8");
            }
            System.out.println(industry);
            System.out.println(rankNum);
            System.out.println(orgName);
            System.out.println(textArray);

//
            try {
                loadDriver();
                String mysqlUrl = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF8";
                Connection conn = DriverManager.getConnection(mysqlUrl, "root", "2241883");
                Statement st = conn.createStatement();

                st.executeUpdate("INSERT INTO basic (rankNum,industry,basic,orgName) " +
                        "VALUES ('"  + rankNum + "','" + industry + "','" + textArray + "','" + orgName + "')");

                conn.close();
            } catch (Exception e) {
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }

        }


    }


    public static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (java.lang.ClassNotFoundException e) {
            System.err.print("ClassNotFoundException: ");
            System.err.println(e.getMessage());
        }
    }


}
