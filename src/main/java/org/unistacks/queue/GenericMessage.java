package org.unistacks.queue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mazean on 2017/9/21
 */
public class GenericMessage implements Serializable{

    private static final long serialVersionUID = 1278317782542184688L;

    public GenericMessage() {
        super();
        messageTimestamp = System.currentTimeMillis();
        properties = new HashMap<String, String>();
    }

//    /**
//     * If ext.auto.ack property is set to false when creating the Consumer, this
//     * method must be invoked for every message received and processed,
//     * otherwise may not be able to receive new message.
//     *
//     * @throws ServerNotAvailableException
//     */
//    public void ack() throws ServerNotAvailableException {
//        ackableConsumer.ack();
//    }

    private long messageTimestamp;

    private String messageGroupId;

    private byte[] objectBytes;

    private Map<String, String> properties;

//    private AckableConsumer ackableConsumer;
//
//    public void setAckableConsumer(AckableConsumer ackableConsumer) {
//        this.ackableConsumer = ackableConsumer;
//    }

    public long getMessageTimestamp() {
        return messageTimestamp;
    }

    public void setMessageTimestamp(long messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
    }

    public byte[] getObjectBytes() {
        return objectBytes;
    }

    public void setObjectBytes(byte[] objectBytes) {
        this.objectBytes = objectBytes;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getMessageGroupId() {
        return messageGroupId;
    }

    public void setMessageGroupId(String messageGroupId) {
        this.messageGroupId = messageGroupId;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("messageTimestamp=").append(messageTimestamp).append(", messageGroupId=").append(messageGroupId);
        return str.toString();
    }

    public int getSize() {
        return this.getObjectBytes() == null ? 0 : this.getObjectBytes().length;
    }
}
