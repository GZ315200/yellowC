package org.unistacks.queue;

/**
 * Created by mazean on 2017/9/21
 */
public class KafkaSerializerFactory {

    private KafkaSerializerFactory() {
        throw new AssertionError();
    }

    private static class SerializerHolder {
        static final KafkaSerializer serializer = new KafkaJsonSerializer();
    }

    // lazy initialization of static field
    public static KafkaSerializer getSerializer() {
        return SerializerHolder.serializer;
    }
}
