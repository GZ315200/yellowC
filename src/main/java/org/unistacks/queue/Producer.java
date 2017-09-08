package org.unistacks.queue;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Clock;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by mazean on 2017/9/5
 */
public class Producer {

    public void sentMessage(String messageStr,int messageNo,String topic,boolean isAsync) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","192.168.1.202:9092");
        properties.put("client.id","DemoProducer");
        properties.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(properties);
            Clock clock = Clock.systemUTC();
            long startTime = clock.millis();
            if (isAsync) {
                producer.send(new ProducerRecord(topic,messageNo,messageStr),new MyCallBack(startTime,messageNo,messageStr));
            } else {
                try {
                    producer.send(new ProducerRecord(topic,messageNo,messageStr)).get();
                    System.out.println("Sent message: (" + messageNo + "," + messageStr + ")");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
    }

    class MyCallBack implements Callback {

        private final long startTime;
        private final int key;
        private final String message;

        public MyCallBack(long startTime, int key, String message) {
            this.startTime = startTime;
            this.key = key;
            this.message = message;
        }

        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (recordMetadata != null) {
                System.out.println("message (" + key + "," + message + ") sent to partition (" +
                recordMetadata.partition() + ") ," + "offset (" + recordMetadata.offset() + ") in" + elapsedTime + "ms");
            } else {
                e.printStackTrace();
            }
        }
    }

}
