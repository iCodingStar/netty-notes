package cn.codingstar.netty.spi;

/**
 * @version: java8
 * @author: CodingStar
 * @contact: shixing.cs@gmail.com
 * @file: RawMessageService.java
 * @time: 2018/2/11 11:02
 * @software: Intellij Idea
 * @desc:
 */
public class RawMessageService implements MessageService {
    public String getMessage() {
        return "Raw Message";
    }
}
