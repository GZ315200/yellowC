package org.unistacks.zookeeper;


import kafka.cluster.EndPoint;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.kafka.common.network.ListenerName;
import org.apache.kafka.common.protocol.SecurityProtocol;
import scala.collection.JavaConversions;
import kafka.cluster.Broker;
import scala.collection.Seq;

/**
 * @author Gyges Zean
 * @date 2017/12/21
 */
public class ZkHelper {

    /**
     * 获取ZK连接
     * @param zkUrl
     * @return
     */
    public  ZkClient getZkClient(String zkUrl) {
        final ZkClient zkClient = new ZkClient(zkUrl, 30000, 10000, new ZkSerializer() {

            @Override
            public byte[] serialize(Object data) throws ZkMarshallingError {
                return data.toString().getBytes();
            }

            @Override
            public Object deserialize(byte[] bytes) throws ZkMarshallingError {
                return new String(bytes);
            }
        });
        return zkClient;
    }

    public static void close(ZkClient zkClient){
        if(zkClient != null){
            zkClient.close();
        }
    }


    public static ZkUtils getZkUtils(String zkUrl) {
        return ZkUtils.apply(zkUrl,30000,10000,false);
    }


    public static void main(String[] args) {



        ZkUtils zkUtils = getZkUtils("192.168.1.193:2181");
        StringBuilder bootstrapServers = new StringBuilder();
        for (Broker broker : JavaConversions.asJavaIterable(zkUtils.getAllBrokersInCluster()))
        {

            Seq<EndPoint> endPoints = broker.endPoints();
            if(endPoints == null) continue;
            for (EndPoint point : JavaConversions.asJavaIterable(endPoints))
            {
                bootstrapServers.append(point.host() + ":" + point.port() + ",");
                System.out.printf("host = %s " + "port = %s " ,point.host(),point.port());
                System.out.println();
            }
        }

        System.out.println(bootstrapServers.deleteCharAt(bootstrapServers.length() - 1).toString());


//        zkUtils.getChildren()
//        ZkHelper zkHelper = new ZkHelper();
//        ZkClient zkClient = zkHelper.getZkClient("192.168.1.217:2181");
//        List<String> child = zkClient.getChildren("/brokers/ids");
//        for (String node : child) {
//            System.out.println(zkClient.readData("/brokers/ids/"+node).toString());
//        }

//        System.out.println(zkClient.readData("/brokers/ids").toString());
    }
}
