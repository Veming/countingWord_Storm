package com.hust.su.blot;

import com.hust.su.dao.WordFreqDao;
import com.hust.su.enity.WordFreq;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;

public class WordCount extends BaseBasicBolt {



    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
//        Jedis jedis = new Jedis("127.0.0.1");
        WordFreqDao wordFreqDao = new WordFreqDao();
        ArrayList<String> words = (ArrayList<String>) input.getValueByField("words");
        for (String word:words) {
            WordFreq wordFreq = new WordFreq();
            wordFreq.setWord(word);
            wordFreqDao.saveWordFreq(wordFreq);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("count"));
    }
}
