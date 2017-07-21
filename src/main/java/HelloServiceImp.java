import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageProducer;


import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


public class HelloServiceImp implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImp.class);
    @Resource
    private MessageProducer messageProducer;
    @Value(value = "sayHelloSubject")
    private String sayHelloSubject;

    public void sendMessage(String name){
        Message message = messageProducer.generateMessage(sayHelloSubject,1, TimeUnit.HOURS);
        message.setProperty("content",name);
        messageProducer.sendMessage(message);
        logger.info("Send QMQ Message:{}", JSON.toJSON(message));
    }

}
