import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Author: peipei.zhou
 * Update: (2014-11-16 22:19)
 * @version $
 *Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
public class StartProviderService {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloServiceImp helloServiceImp = (HelloServiceImp) applicationContext.getBean("helloService");
        helloServiceImp.sendMessage("HELLO");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
