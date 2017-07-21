import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qunar.tc.qmq.Message;
import qunar.tc.qmq.MessageSendStateListener;


public class ProviderListener implements MessageSendStateListener {
    private static final Logger logger = LoggerFactory.getLogger(ProviderListener.class);
    @Override
    public void onSuccess(Message message) {
        logger.info("msgId:{},msgSubject:{} send to broker success",message.getMessageId(),message.getSubject());
    }

    @Override
    public void onFailed(Message message) {
        logger.info("msgId:{},msgSubject:{} send to broker failed",message.getMessageId(),message.getSubject());
    }
}
