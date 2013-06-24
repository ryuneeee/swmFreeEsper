package so.tree.example.esper.engine;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import org.apache.log4j.*;
import so.tree.example.esper.crawler.Crawler;
import so.tree.example.esper.listener.JageiListener;

/**
 * Created with IntelliJ IDEA.
 * User: Ryun
 * Date: 13. 2. 14.
 * Time: 오전 1:29
 * To change this template use File | Settings | File Templates.
 */
public class Engine {
    public static void main(String[] args) {


        //log4j
        SimpleLayout layout = new SimpleLayout();
        ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel((Level) Level.DEBUG);


        Configuration cepConfig = new Configuration();
        cepConfig.addEventTypeAutoName("so.tree.example.esper.event");

        // <Creating a Statement>
        final EPServiceProvider epService = EPServiceProviderManager
                .getDefaultProvider(cepConfig);
        //String epl = "select avg(dulCount) from Jagei";
        String epl = "select avg(dulCount) from Jagei.win:time(5 min) output every 10 second";
        EPStatement statement = epService.getEPAdministrator().createEPL(epl);
        // </Creating a Statement>

        // <Adding a Listener>
        JageiListener listener = new JageiListener();
        statement.addListener(listener);

        Crawler crawler = new Crawler(epService);
        crawler.start();

    }

}
