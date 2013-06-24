package so.tree.example.esper.listener;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Ryun
 * Date: 13. 2. 14.
 * Time: 오전 2:01
 * To change this template use File | Settings | File Templates.
 */
public class JageiListener implements UpdateListener{


    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans2) {
        EventBean event = eventBeans[0];
        Double avgDulCount = (Double) event.get("avg(dulCount)");
        BigDecimal bd = new BigDecimal(avgDulCount);
        System.out.println("[실시간 ㄷㄷㄷ 지수 : " + bd.setScale(4, BigDecimal.ROUND_UP) + "] ");

    }
}
