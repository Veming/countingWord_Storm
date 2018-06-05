package com.hust.su.blot;

import com.hust.su.dao.BiWordFreqDao;
import com.hust.su.enity.BiWordFreq;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Map;

public class ConnectionWordsCount extends BaseBasicBolt {

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        BiWordFreqDao biWordFreqDao = new BiWordFreqDao();
//        Jedis jedis = new Jedis("127.0.0.1");
        ArrayList<String> words = (ArrayList<String>) input.getValueByField("words");
        for (int i=0; i < words.size() - 1;i++){
            for (int j=i+1; j < words.size(); j++){
                BiWordFreq bf = new BiWordFreq();
                bf.setWord1(words.get(i));
                bf.setWord1(words.get(j));
                biWordFreqDao.saveBiWordFreq(bf);

            }
        }
//        System.out.println(word);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("ConnectionWordsCount"));
    }
}
