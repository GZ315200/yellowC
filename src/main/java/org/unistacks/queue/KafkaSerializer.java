package org.unistacks.queue;

/**
 * Created by mazean on 2017/9/21
 */
public interface KafkaSerializer {
    public byte[] serializeGenericMessage(GenericMessage gmsg);

    public GenericMessage deserializeGenericMessage(byte[] genericMessageInBytes);
}
