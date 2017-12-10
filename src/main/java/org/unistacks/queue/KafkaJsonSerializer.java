package org.unistacks.queue;

import com.alibaba.fastjson.JSON;

/**
 * Created by mazean on 2017/9/21
 */
public class KafkaJsonSerializer implements KafkaSerializer {
    public byte[] serializeGenericMessage(GenericMessage gmsg) {
        String jsonString = JSON.toJSONString(gmsg);
        return jsonString.getBytes();
    }

    public GenericMessage deserializeGenericMessage(byte[] genericMessageInBytes) {
        return (GenericMessage) JSON.parseObject(new String(genericMessageInBytes), GenericMessage.class);
    }
}
