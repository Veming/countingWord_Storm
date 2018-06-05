package com.hust.su.dao;

import com.hust.su.enity.WordFreq;
import redis.clients.jedis.Jedis;

public class WordFreqDao {

    public void saveWordFreq(WordFreq wf){
        Jedis jedis = new Jedis("localhost");
        String word = wf.getWord();
        if (jedis.exists(word)) {
            int nbr = Integer.parseInt(jedis.get(word));
            wf.setFreq(nbr);
            jedis.set(word, String.valueOf(++nbr));
            System.out.println(word + "\t\t" + nbr);
        }
        else {
            jedis.set(word,"1");
            wf.setFreq(1);
            System.out.println(word + "\t\t1" );
        }
//        System.out.println(word);
    }
}
