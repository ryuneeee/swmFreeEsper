package so.tree.example.esper.crawler;


import com.espertech.esper.client.EPServiceProvider;
import so.tree.example.esper.event.Jagei;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Ryun
 * Date: 13. 2. 15.
 * Time: 오전 8:13
 * To change this template use File | Settings | File Templates.
 */

public class Crawler extends Thread {

    EPServiceProvider epService;

    public Crawler(EPServiceProvider epService) {
        this.epService = epService;
    }

    @Override
    public void run() {

        StringBuffer html = new StringBuffer();
        String line;
        HashMap<String, Jagei> antiDupleMap = new HashMap<String, Jagei>();

        while (true) {
            html.setLength(0);
            try {
                URL url = new URL("http://www.slrclub.com/bbs/zboard.php?id=free");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setUseCaches(false);

                InputStreamReader is = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(is);

                while (true) {
                    line = br.readLine();

                    if (line == null) {
                        break;
                    } else {
                        html.append(line);
                    }
                }

                Thread.sleep(1000);

                //Parser
                int startListIdx = html.indexOf("<td class=\"list_num no_att\">");
                int endListIdx = html.indexOf("bbs_foot");

                String listBone = html.substring(startListIdx, endListIdx);
                String articleNo;
                String articleTitle;
                String articleTime;
                //String viewCount;
                int dulCount=0;


                for(int i=0;i<28;i++){

                    dulCount =0;
                    articleNo = listBone.substring(listBone.indexOf("list_num no_att\">")+17, listBone.indexOf("list_num no_att\">")+25);
                    articleTitle = listBone.substring(listBone.indexOf("no="+articleNo)+13, listBone.indexOf("</a>"));
                    articleTime = listBone.substring(listBone.indexOf("list_date no_att")+18, listBone.indexOf("list_date no_att")+26);
                    //viewCount = listBone.substring(listBone.indexOf("list_click no_att")+19, listBone.indexOf("</td>", listBone.indexOf("list_click no_att")));
                    //System.out.println(articleNo + " / " +articleTitle + " / " + articleTime + " / " + viewCount);

                    for (int j=0; j<articleTitle.length();j++) {
                        if (articleTitle.charAt(j) == 'ㄷ') dulCount++;
                    }

                    if(antiDupleMap.get(articleNo)==null){
                        Jagei jagei = new Jagei();
                        jagei.setNo(Long.parseLong(articleNo));
                        jagei.setTitle(articleTitle);
                        jagei.setDulCount(dulCount);
                        antiDupleMap.put(articleNo, jagei);
                        System.out.print("Add-");
                        System.out.println(jagei.getTitle());
                        epService.getEPRuntime().sendEvent(jagei);
                    }

                    listBone = listBone.substring(listBone.indexOf("<tr>")+4);

                }







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

