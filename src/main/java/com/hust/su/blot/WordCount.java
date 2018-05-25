package com.hust.su.blot;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

public class WordCount extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        Jedis jedis = new Jedis("127.0.0.1");
        String word = (String) input.getValueByField("word");
        if (jedis.exists(word)) {
            int nbr = Integer.parseInt(jedis.get(word));
            jedis.set(word, String.valueOf(++nbr));
            System.out.println(word + "\t\t" + nbr);
        }
        else {
            jedis.set(word,"1");
            System.out.println(word + "\t\t1" );
        }
//        System.out.println(word);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("count"));
    }
}
