package cn.org.hentai.test;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by matrixy on 2017-11-13.
 */
public class TestEventListener implements ApplicationListener<ContextRefreshedEvent>
{
    // 需要在spring-web.xml里注册bean
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        System.err.println("Event: " + event.getClass().getName());
    }
}
