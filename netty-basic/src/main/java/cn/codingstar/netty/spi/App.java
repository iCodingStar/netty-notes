package cn.codingstar.netty.spi;

import java.util.ServiceLoader;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: App.java
 * @time: 2018/2/11 11:07
 * @software: Intellij Idea
 * @desc:
 */
public class App {

    public static void main(String[] args) {
        ServiceLoader<MessageService> loader = ServiceLoader.load(MessageService.class);
        for (MessageService messageService : loader) {
            System.out.println(messageService.getMessage());
        }
    }
}
