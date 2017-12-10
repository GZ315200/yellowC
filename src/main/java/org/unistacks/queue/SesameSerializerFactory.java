package org.unistacks.queue;

/**
 * Created by mazean on 2017/9/21
 */
public class SesameSerializerFactory {

    private SesameSerializerFactory() {
        throw new AssertionError();
    }

    private static class SerializerHolder {
        static final SesameSerializer serializer = new JsonSerializer();
    }

    // lazy initialization of static field
    public static SesameSerializer getSerializer() {
        return SerializerHolder.serializer;
    }
}
