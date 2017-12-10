package org.unistacks.queue;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

/**
 * Created by mazean on 2017/9/21
 */
public class GenericDataModel {
    private static final long serialVersionUID = -166701310989888706L;

    @JSONField(ordinal = 1)
    private String key;

    @JSONField(ordinal = 3)
    private Map<String, String> metaInfo;

    @JSONField(ordinal = 2)
    private byte[] content;

    @JSONField(serialize = false)
    private String entityName;

    @JSONField(ordinal = 4)
    private Long timestamp;

    public GenericDataModel() {
    }

    public GenericDataModel(String key, Map<String, String> metaInfo, byte[] content, String entityName) {
        this.key = key;
        this.metaInfo = metaInfo;
        this.content = content;
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /**
     * @return the globally unique identified key that is associated with this
     * message
     */
    public String getKey() {
        return key;
    }

    /**
     * Set a key for this message. Note that the key must be globally unique
     * identified key that the receivers know how to identify
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Retrieves the content of the message envelop. The senders and the
     * receivers must agree on the encoding and decoding scheme for the content
     * before using it.
     *
     * @return the actual message in byte arrays
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * Retrieves meta information about the message. For example, it might have
     * information regarding the sender's name. Receivers should not rely on the
     * meta information for processing since there are no requirement that the
     * senders should provide the meta information.
     *
     * @return a map that contains the meta information of the message envelop
     */
    public Map<String, String> getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(Map<String, String> metaInfo) {
        this.metaInfo = metaInfo;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int compareTo(GenericDataModel data) {
        return new String(this.key).compareTo(new String(data.key));
    }

    public String toString() {
        String contentStr;
        if (content == null) {
            contentStr = "";
        } else {
            contentStr = new String(content);
        }

        return "Key: " + key + ", Content: " + contentStr + ", MetaInfo = " + metaInfo;
    }

    @JSONField(serialize = false)
    private long size;

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        long contentSize = this.getContent() == null ? 0 : this.getContent().length;
        long metaSize = 0;
        Map<String, String> metaInfo = this.getMetaInfo();
        if (metaInfo != null) {
            for (Map.Entry<String, String> keyValue : metaInfo.entrySet()) {
                metaSize += keyValue.getKey().length() + keyValue.getValue().length();
            }
        }
        return contentSize + metaSize;
    }
}
