package com.lnnk;

import com.lnnk.netty.server.NettyServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 在IOC的容器的启动过程，当所有的bean都已经处理完成之后，spring ioc容器会有一个发布事件的动作。
 * 让我们的bean实现ApplicationListener接口，这样当发布事件时，[spring]的ioc容器就会以容器的实例对象作为事件源类，
 * 并从中找到事件的监听者，此时ApplicationListener接口实例中的onApplicationEvent(E event)方法就会被调用，
 *
 * @author Lnnk
 * @date 2021/2/28 17:29
 **/
@Configuration
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    private final NettyServer nettyServer;

    public NettyBooter(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            try {
                nettyServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}