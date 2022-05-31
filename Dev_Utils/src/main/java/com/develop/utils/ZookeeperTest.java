//package com.develop.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.neusoft.jereh.gas.data.producer.KafkaProducer;
//import com.neusoft.jereh.rayup.common.util.RedisUtil;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.ZooKeeper;
//
//
//
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.naming.ldap.HasControls;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author: miao
// * @date 2021/12/10
// */
//
////@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class ZookeeperTest {
//
//    private static final String connectString = "10.1.17.178:52181";
//    private static final int sessionTimeout = 2000;
//
//    ZooKeeper zkClient = null;
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @Autowired
//    private KafkaProducer producer;
//
//    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 10000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
//
//    public void init() throws IOException {
//        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
//            // zookeeper可以根据事件作出相应的响应
//            @Override
//            public void process(WatchedEvent event) {
//                // 收到事件通知后的回调函数 (应该是我们自己的事件处理逻辑)
//                System.out.println(event.getType() + " -- " + event.getPath()); // 事件的类型  --   事件发生的节点
//            }
//        });
//    }
//    @Test
//    void contextLoads() throws  Exception{
//        Map<String, Object> map = new HashMap<>();
//        map.put("config", "333");
//        map.put("item", "444");
//        Map<String, Object> map2 = new HashMap<>();
//        map2.put("config", "555");
//        map2.put("item", "666");
////        redisUtil.hmset("test:" + 1, map);
////        redisUtil.hmset("test:" + 2, map2);
////
////
////        redisUtil.hset("text", "22", JSON.toJSONString(map));
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("{ \"gasConfigId\": 6535,\n" );
//        sb.append(" \"gasDeviceId\": 30\n ");
//        sb.append(" }");
//
//
//        StringBuilder sb2 = new StringBuilder();
//        sb2.append("{ \"gasConfigId\": 9538,\n" );
//        sb2.append(" \"gasDeviceId\": 28\n ");
//        sb2.append(" }");
//
//        StringBuilder sb3 = new StringBuilder();
//        sb3.append("{ \"gasConfigId\": 3,\n" );
//        sb3.append(" \"gasDeviceId\": 28\n ");
//        sb3.append(" }");
//        while (true){
//            producer.sendMessage("gas_streaming_warning", sb2.toString());
//            producer.sendMessage("gas_streaming_warning", sb3.toString());
//            Thread.sleep(5000);
//        }
//
//
//
//
////        for (int i = 0; i < 5; i++) {
////            producer.sendMessage("gas_streaming_warning", sb.toString());
////            producer.sendMessage("gas_streaming_warning", sb2.toString());
////        }
////        Tasks2 tasks2 = new Tasks2(28L);
////        Tasks tasks = new Tasks(30L);
////        for (int i = 0; i < 5; i++) {
////            threadPoolExecutor.execute(tasks2);
////            threadPoolExecutor.execute(tasks);
////        }
////        for (int i = 0; i < 5; i++) {
////            new Thread(() -> {
////                StringBuilder sb = new StringBuilder();
////                sb.append("{ \"gasConfigId\": 6535,\n" );
////                sb.append(" \"gasDeviceId\": 30,\n ");
////                sb.append(" }");
////                if (producer != null){
////                    producer.sendMessage("gas_streaming_warning",sb.toString());
////                    System.out.println("Tasks1-----------------------------------===输出完了" + sb.toString());
////                }
////            }).start();
////
////            new Thread( () -> {
////                StringBuilder sb = new StringBuilder();
////                sb.append("{ \"gasConfigId\": 9538,\n" );
////                sb.append(" \"gasDeviceId\": 28,\n ");
////                sb.append(" }");
////                if (producer != null){
////                    producer.sendMessage("gas_streaming_warning",sb.toString());
////                    System.out.println("Tasks2--------------------------------------===输出完了" + sb.toString());
////
////                }
////            }).start();
////        }
//
//
//    }
//
//    class Tasks implements Runnable{
//        private Long deivceId;
//
//        public Tasks (Long deivceId) {
//            this.deivceId = deivceId;
//        }
//        @Override
//        public void run() {
//            StringBuilder sb = new StringBuilder();
//            sb.append("{ \"gasConfigId\": 6535,\n" );
//            sb.append(" \"gasDeviceId\": " + deivceId + ",\n ");
//            sb.append(" }");
//            if (producer != null){
//                producer.sendMessage("gas_streaming_warning",sb.toString());
//                System.out.println("Tasks1-----------------------------------===输出完了" + sb.toString());
//
//            }
//            System.out.println("---------------------------------");
//        }
//    }
//    class Tasks2 implements Runnable{
//        private Long deivceId;
//        public Tasks2 (Long deivceId) {
//            this.deivceId = deivceId;
//        }
//        @Override
//        public void run() {
//            StringBuilder sb = new StringBuilder();
//            sb.append("{ \"gasConfigId\": 6535,\n" );
//            sb.append(" \"gasDeviceId\": " + deivceId + ",\n ");
//            sb.append(" }");
//            if (producer != null){
//                producer.sendMessage("gas_streaming_warning",sb.toString());
//                System.out.println("Tasks2--------------------------------------===输出完了" + sb.toString());
//
//            }
//            System.out.println("---------------------------------");
//        }
//    }
//
//}
