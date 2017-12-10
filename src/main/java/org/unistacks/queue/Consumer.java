package org.unistacks.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Created by mazean on 2017/9/5
 */
public class Consumer implements Runnable{

    private final KafkaConsumer<String,String> consumer;
    private final String topic;
    private ExecutorService executor;
    private long delay;


    public Consumer(Properties props, String topic) {
        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    public void shutdown() {
        if (consumer != null) {
            consumer.close();
        }
        if (executor != null) {
            executor.shutdown();
        }
    }
    private ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("demo-pool-%d").build();

    @Override
    public void run() {
        consumer.subscribe(Collections.singletonList(this.topic));

        ExecutorService pool = new ThreadPoolExecutor(5,200,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        pool.execute(() -> {
            while(true) {
                try {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("Received message: (" + String.valueOf(record.key()) + ", " + record.value() + ") at offset " + record.offset());
                    }
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    /**
     * consumer 配置.
     * @param groupId 组名
     * @return
     */
    private static Properties createConsumerConfig(String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "60000");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public static void main(String[] args) throws InterruptedException {
        String groupId = "tamboo_sanitycheck_ok";//ESBridge
        Properties props = createConsumerConfig(groupId);
        Consumer example = new Consumer(props, KafkaProperties.TOPIC);
        example.run();

        Thread.sleep(24*60*60*1000);

        example.shutdown();
    }
}
