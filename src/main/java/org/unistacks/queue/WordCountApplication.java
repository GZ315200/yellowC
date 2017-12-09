package org.unistacks.queue

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.{KStream, KStreamBuilder, KTable}
;

/**
 * Created by Gyges on 2017/10/18
 */
object WordCountApplication {

    def main(args : Array[String]) {
        val config : Properties = {
            val p = new Properties()
            p.put(StreamsConfig.APPLICATION_ID_CONFIG,"wordcount-application")
            p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.1.202:9092")
            p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,Serdes.String().getClass)
            p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,Serdes.String().getClass);
            p
        }

        val builder : KStreamBuilder = new KStreamBuilder()
        val textLines : KStream[String,String] = builder.stream("TextLinesTopic")
        val wordCounts : KTable[String,Long] = textLines
          .flatMapValues(textLine => textLine.toLowerCase.split("\\W+").toIterable.asJava)
          .groupBy((_, word) => word)
          .count("Counts")
    }
}
