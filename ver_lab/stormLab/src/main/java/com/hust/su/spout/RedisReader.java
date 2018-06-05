package com.hust.su.spout;


import com.hust.su.enity.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import java.util.Map;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import java.util.HashMap;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
/**
 * ${DESCRIPTION}
 * @author
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 **/
@Slf4j
public class RedisReader extends BaseRichSpout {
    //BaseRichSpout是ISpout接口和IComponent接口的简单实现，接口对用不到的方法提供了默认的实现

    private SpoutOutputCollector collector;
    private TopologyContext context;
    private Jedis jedis;
    //非弹出代码
//    private static int index = 0;

    private boolean createJedisContext(){
        jedis = new Jedis("localhost");
        if ("PONG".equals(jedis.ping())){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * open()方法中是ISpout接口中定义，在Spout组件初始化时被调用。
     * open()接受三个参数:一个包含Storm配置的Map,一个TopologyContext对象，提供了topology中组件的信息,SpoutOutputCollector对象提供发射tuple的方法。
     * 在这个例子中,我们不需要执行初始化,只是简单的存储在一个SpoutOutputCollector实例变量。
     */
    @Override
    public void open(Map map, TopologyContext context, SpoutOutputCollector collector) {
        jedis = new Jedis("127.0.0.1",6379);

        this.collector = collector;
        this.context = context;
    }

    /**
     * nextTuple()方法是任何Spout实现的核心。
     * Storm调用这个方法，向输出的collector发出tuple。
     * 在这里,我们只是发出当前索引的句子，并增加该索引准备发射下一个句子。
     */
    @Override
    public void nextTuple() {
//        Utils.sleep(100);
        String title = jedis.lpop("title");
        String time = jedis.lpop("time");
//        String title = jedis.lindex("title",index);
//        String time = jedis.lindex("time",index);
        Data data = new Data();
        data.setTitle(title);
        data.setTime(time);
        System.out.println("title:\t"+title);
        System.out.println("time:\t"+time);
        if (title == null ||time == null){
            Utils.sleep(1600);
        }
//        else {
//            //尚未弹出
//            index++;
//        }
        collector.emit(new Values(data));
    }

    /**
     * declareOutputFields是在IComponent接口中定义的，所有Storm的组件（spout和bolt）都必须实现这个接口
     * 用于告诉Storm流组件将会发出那些数据流，每个流的tuple将包含的字段
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("data"));
    }
}

