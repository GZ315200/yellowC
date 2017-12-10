package org.unistacks.elasticsearch;



import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mazean on 2017/9/20
 */
public class Esbridge {

    public static void main(String[] args) {
        Settings settings = Settings.builder().put("cluster.name","elasticsearch_dev").build();
        String esAddr ="192.168.1.201:9300";
        Client client=null;
        try {
            client = new PreBuiltTransportClient(settings).addTransportAddresses(parseESAddr(esAddr));
            GetResponse response = client.prepareGet("zean", "student", "1").execute().actionGet();
            //输出结果
            System.out.println(response.getSourceAsString());
        } catch (UnknownHostException e) {
            e.printStackTrace();;
        }
    }


    private static InetSocketTransportAddress[] parseESAddr(String esAddr)
            throws NumberFormatException, UnknownHostException {
        List<InetSocketTransportAddress> list = new ArrayList<InetSocketTransportAddress>();
        for (String hostAndPort : esAddr.split(",")) {
            list.add(new InetSocketTransportAddress(InetAddress.getByName(hostAndPort.split(":")[0]),
                    Integer.parseInt(hostAndPort.split(":")[1])));
        }
        return list.toArray(new InetSocketTransportAddress[] {});
    }
}
