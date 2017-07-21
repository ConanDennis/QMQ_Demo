import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import qunar.tc.qmq.ListenerHolder;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageConsumer;
import qunar.tc.qmq.MessageListener;

/**
 * Author: peipei.zhou
 * Update: (2014-11-16 22:19)
 * @version $
 *Copyright (c) 2014 Qunar.com. All Rights Reserved.
 */
public class QmqMessageListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(QmqMessageListener.class);
    @Value(value = "message.subject")
    private String subject;

    @Value(value = "message.group")
    private String group;

    private MessageConsumer messageConsumer;

    private ListenerHolder listenerHolder;

    public QmqMessageListener(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @PostConstruct
    public void init() {
        listenerHolder = messageConsumer.addListener(subject, group, this, genThreadPoolExecutor());
    }

    @PreDestroy
    public void destroy() {
        if (listenerHolder != null) {
            listenerHolder.stopListen();
        }
    }
    @Override
    public void onMessage(Message msg) {
        String content = msg.getStringProperty("content");
        logger.info("accept data msgid={},msgidSubject={}, content={}", msg.getMessageId(),msg.getSubject(), content);
    }

    private ThreadPoolExecutor genThreadPoolExecutor() {
        return new ThreadPoolExecutor(5, 5, 1, TimeUnit.HOURS, new LinkedBlockingDeque<Runnable>(500), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable, "qmq.sayHello.consumer.dev");
                return thread;
            }
        });
    }
}
