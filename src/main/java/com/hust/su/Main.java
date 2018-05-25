package com.hust.su;

import com.hust.su.blot.WordCount;
import com.hust.su.blot.WordSplit;
import com.hust.su.spout.RedisReader;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.Bolt;
import org.apache.storm.testing.TestWordSpout;
import org.apache.storm.topology.SpoutDeclarer;
import org.apache.storm.topology.TopologyBuilder;

public class Main {
    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
//        Spout节点
        builder.setSpout("redisSpout",new RedisReader());
//        blot节点
        builder.setBolt("wordSplit",new WordSplit()).shuffleGrouping("redisSpout");
        builder.setBolt("wordCount",new WordCount()).shuffleGrouping("wordSplit");

        Config conf = new Config();
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("redisSpout", conf, builder.createTopology());

    }
}
