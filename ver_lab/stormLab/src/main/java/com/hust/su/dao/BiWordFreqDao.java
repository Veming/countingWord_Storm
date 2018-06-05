package com.hust.su.dao;

import com.hust.su.enity.BiWordFreq;
import redis.clients.jedis.Jedis;

import java.util.Date;

public class BiWordFreqDao {
    Jedis jedis;

    public void saveBiWordFreq(BiWordFreq wf) {
        if (!jedis.ping().equals("PONG")) {
            jedis = new Jedis("localhost");
        }

        String connectionWord = wf.getWord1() + "_" + wf.getWord2();
        if (jedis.exists(connectionWord)) {
            int nbr = Integer.parseInt(jedis.get(connectionWord));
            jedis.set(connectionWord, String.valueOf(++nbr));
            wf.setFreq(nbr);
            System.out.println(connectionWord + "\t\t" + nbr);
        } else {
            jedis.set(connectionWord, "1");
            wf.setFreq(1);
            System.out.println(connectionWord + "\t\t1");
        }
    }
}
