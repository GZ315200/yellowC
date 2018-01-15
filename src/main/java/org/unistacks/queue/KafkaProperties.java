/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.unistacks.queue;

import kafka.cluster.Broker;
import kafka.cluster.EndPoint;
import kafka.utils.ZkUtils;
import scala.collection.JavaConversions;
import scala.collection.Seq;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class KafkaProperties {
    public static final String TOPIC = "test";
//    public static final String TOPIC = "tamboo_check";

    private static final String JAAS_NAME = "kafka_client_jaas.conf";


    public static final String KAFKA_SERVER_URL = "192.168.1.217";
    public static final int KAFKA_SERVER_PORT = 9093;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "tamboo_consumer_metrics";
    public static final String TOPIC3 = "tamboo_client_metrics";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {
    }

    static String getPath() {
        String s = KafkaProperties.class.getClassLoader().getResource(JAAS_NAME).getPath();
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) {
        ZkUtils zkUtils = ZkUtils.apply("192.168.1.193:2181", 30000, 30000, false);

        Map<String, String> protocolServerMap = new HashMap<String, String>();
        TreeMap<Integer, String> portProtocolMap = new TreeMap<Integer, String>();

        StringBuilder bootstrapServers = new StringBuilder();
            for (Broker broker : JavaConversions.asJavaIterable(zkUtils.getAllBrokersInCluster())) {
                Seq<EndPoint> endPoints = broker.endPoints();
                if (endPoints == null) {
                    continue;
                }
                for (EndPoint point : JavaConversions.asJavaIterable(endPoints))
                {

                    portProtocolMap.put(point.port(),point.securityProtocol().name);
                    bootstrapServers.append(point.host() + ":" + point.port() + ",");
                    String str = bootstrapServers.toString();
                    if (bootstrapServers.length() != 0) {
                        protocolServerMap.put(point.securityProtocol().name,str.substring(0,str.length()-1));
//                        protocolServerMap.put(point.securityProtocol().name,bootstrapServers.toString());
                    }
                }
                //System.out.println(bootstrapServers.deleteCharAt(bootstrapServers.length()-1));
            }
//        zkUtils = ZkUtils.apply("192.168.1.193:2181", 30000, 30000, false);
//        StringBuilder bootstrapServers = new StringBuilder();
//        //modify by hzq 20170919, upgrade v0.11  不再分协议类型，而是直接获取全部节点信息
//        for (Broker broker : JavaConversions.asJavaIterable(zkUtils.getAllBrokersInCluster())) {
//            Seq<EndPoint> endPoints = broker.endPoints();
//
//            if (endPoints == null) {
//                continue;
//            }
//            for (EndPoint point : JavaConversions.asJavaIterable(endPoints)) {
//                bootstrapServers.append(point.host() + ":" + point.port()).append(",");
//                portProtocolMap.put(point.port(), point.securityProtocol().name);
//                if (bootstrapServers.length() != 0) {
//                    protocolServerMap.put(point.securityProtocol().name, bootstrapServers.deleteCharAt(bootstrapServers.length() - 1).toString());
//                }
//            }
//
//        }
        System.out.println(protocolServerMap.toString());//protocolServerMapt
        System.out.println();
    }
}
