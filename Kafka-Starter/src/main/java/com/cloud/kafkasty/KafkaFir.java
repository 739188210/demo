package com.cloud.kafkasty;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.internals.NoOpConsumerRebalanceListener;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: miao
 * @date 2021/12/31
 */

public class KafkaFir {
    //   public static final String kafka_cluster = "10.1.17.179:59092,10.1.17.180:59092,10.1.17.181:59092";
    public static final String kafka_cluster = "115.28.208.160:31943";
    public static final String groupId = "demo_user";    //"gas";
    public static final String topic = "post-nineties";
    public static final Integer partition = 1;
    private static Logger logger = LoggerFactory.getLogger(KafkaFir.class);

    public static void main(String[] args) {
//        consumer(null);
        producer();
    }

    public static void producer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_cluster);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        //  properties.put(ProducerConfig.ACKS_CONFIG,);
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        String val = "{\"device_id\":40,\"gateway_id\":\"WGBH0119\",\"iot_ts\":\"1644279457407\",\"PT-J101\":6.0,\"PT-J141\":20.35,\"PT-J201\":21.62,\"PT-J241\":28.4,\"PT-J271\":40.84,\"PT-J731\":2.79,\"VT-J761\":0.71,\"VT-J762\":-2.7,\"VT-J763\":1.23,\"PDT-J731\":3.09,\"TE-J781\":76.94,\"TE-J101\":21.79,\"TE-J201\":30.82,\"TE-J271\":51.65,\"TE-J131\":59.57,\"TE-J132\":63.93,\"TE-J231\":56.02,\"TE-J232\":63.41,\"TE-J731\":24.29,\"TE-J761\":83.93,\"TE-J762\":85.33,\"TE-J763\":74.83,\"TE-J764\":86.38,\"TS-H001\":1,\"TS-H002\":1224,\"TS-H003\":18,\"AU-0001\":\"\",\"runtime\":\"\",\"last_data\":\"0\"}";

        ProducerRecord<String, String> record = new ProducerRecord<>(topic, partition, System.currentTimeMillis() - 1000000, "cgc", val);

        //同步发送， 连接错误、No Leader错误都可以通过重试解决；消息太大这类错误kafkaProducer不会进行任何重试，直接抛出异常
        try {
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata recordMetadata = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

//        // 异步发送
//        Future<RecordMetadata> future = producer.send(record, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if(exception != null) {
//                    exception.printStackTrace();
//                } else {
//                    System.out.println("The offset of the record we just sent is: " + metadata.offset() + "---- " + metadata.timestamp());
//                }
//            }
//        });
    }

    public static void consumer(Optional<String> instanceId) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka_cluster);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //     instanceId.ifPresent(id -> properties.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, id));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 2000);
        //     properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");

        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 5000);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 10);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000);


        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(properties);
        Map<String, List<PartitionInfo>> stringListMap = consumer.listTopics();

        Map<TopicPartition, Long> boMap = consumer.beginningOffsets(Collections.singleton(new TopicPartition(topic, 1)));
        //   consumer.subscribe(Collections.singleton("gas_streaming_warning"));
        boMap.forEach((key, val) -> {
            System.out.println(key.topic() + "-" + key.partition() + "-" + val);
        });
        //      consumer.subscribe(Collections.singleton("gas_streaming_warning"), new NoOpConsumerRebalanceListener());
        ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(10));
        Iterator<ConsumerRecord<String, String>> iterator = null;
        int i = 0;
        while (((iterator == null) ? iterator = poll.iterator() : iterator).hasNext()) {
            ConsumerRecord<String, String> record = iterator.next();
            String key = record.key();
            int partition = record.partition();
            Optional<Integer> integer = record.leaderEpoch();
            long offset = record.offset();
            record.leaderEpoch();
            String value = record.value();
            JSONObject jsonObject = JSONObject.parseObject(value);
            Integer gasConfigId = Integer.valueOf(String.valueOf(jsonObject.get("gasConfigId")));
            Integer gasDeviceId = Integer.valueOf(String.valueOf(jsonObject.get("gasDeviceId")));
            logger.info("the {} record's info : gasConfigId is {} , gasDeviceId is {}", ++i, gasConfigId, gasDeviceId);
        }
        consumer.commitAsync();
        List<ConsumerRecord<String, String>> gas_streaming_warning = poll.records(new TopicPartition("gas_streaming_warning", 0));


        consumer.subscribe(Collections.singleton("gas_spark_streaming"), new NoOpConsumerRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
            }

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
            }
        });

        //  ProducerRecord<String, String> record = new ProducerRecord<>();

        //    consumer.subscribe();


    }


    public static void timeUtils() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String dateStr = sf.format(new Date());
        logger.info("dateStr: {}", dateStr);

        Date parse = null;
        try {
            parse = sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info("date: {}", parse);

        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String format = sf2.format(new Date());
        logger.info("dateStr: {}", format);
        Date parse1 = null;
        try {
            parse1 = sf2.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logger.info("date: {}", parse1);


    }
}
