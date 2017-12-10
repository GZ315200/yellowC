package org.unistacks.queue;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.time.Clock;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by mazean on 2017/9/5
 */
public class Producer {

    public void sentMessage(String messageStr, String messageNo, String topic, boolean isAsync) {
        Properties props = new Properties();
        props.put("bootstrap.servers", KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(props);
        Clock clock = Clock.systemUTC();
        long startTime = clock.millis();
        if (isAsync) {
            producer.send(new ProducerRecord<>(topic, messageNo, messageStr), new MyCallBack(startTime, messageNo, messageStr));
//            List<Partition> partitions = producer.partitionsFor(topic);
//            System.out.println("partitions size : "+partitions.size());
            producer.close();
        } else {
            try {
                producer.send(new ProducerRecord<>(topic, messageNo, messageStr)).get();
                System.out.println("Sent message: (" + messageNo + "," + messageStr + ")");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class MyCallBack implements Callback {

        private final long startTime;
        private final String key;
        private final String message;

        public MyCallBack(long startTime,

                          String key, String message) {
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


    public static void main(String[] args) {

//        GenericDataModel gdm = new GenericDataModel();
//        gdm.setEntityName(KafkaProperties.TOPIC);
//        byte[] b = new byte[200];
//        gdm.setContent("hello,world".getBytes());
//        SesameSerializer ss = SesameSerializerFactory.getSerializer();
//
//        List<GenericDataModel> gdmList = Lists.newArrayList();
//        gdmList.add(gdm);
//        GenericMessage gm = new GenericMessage();
//        gm.setObjectBytes(ss.serializeGDMList(gdmList));
//        System.out.println(gm.getObjectBytes());

        Producer producer = new Producer();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        executorService.scheduleAtFixedRate(()-> {
                        for (int i = 0; i < 10; i++) {
                            String key = Math.abs(i + new Random(1).nextInt()) + "";
                            producer.sentMessage("hello,world", key, KafkaProperties.TOPIC, true);
                        }
                    },100,10*1000,TimeUnit.MILLISECONDS);
                     System.out.println("send finished");
                }


//    @SuppressWarnings("rawtypes")
//    private static ProducerRecord getKeyedMessage(GenericMessage msg) throws Exception {
//        KafkaSerializer serializer = KafkaSerializerFactory.getSerializer();
//        byte[] serializedMsg;
//        try {
//            serializedMsg = serializer.serializeGenericMessage(msg);
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
//
//        String messageGroupId = msg.getMessageGroupId();
//
//        // if don't set messageGroupId, random to different partitions.
//        if (messageGroupId == null) {
//            messageGroupId = Math.abs(new Random().nextInt()) + "";
//        }
//        return new ProducerRecord<>("a.topic", messageGroupId.getBytes(), serializedMsg);
//
//    }

    }
