package com.hust.su.blot;

import com.hust.su.enity.Data;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.StringUtils;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 订阅sentence spout发射的tuple流，实现分割单词
 * @author su
 * @date 2018/5/23
 */
public class WordSplit extends BaseRichBolt {
    //BaseRichBolt是IComponent和IBolt接口的实现
    //继承这个类，就不用去实现本例不关心的方法

    private OutputCollector collector;

    /**
     * prepare()方法类似于ISpout 的open()方法。
     * 这个方法在blot初始化时调用，可以用来准备bolt用到的资源,比如数据库连接。
     * 本例子和Spout类一样,Bolt类不需要太多额外的初始化,
     * 所以prepare()方法只保存OutputCollector对象的引用。
     */
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    /**
     * Bolt核心功能是在类IBolt定义execute()方法，这个方法是IBolt接口中定义。
     * 每次Bolt从流接收一个订阅的tuple，都会调用这个方法。
     * 本例中,收到的元组中查找“sentence”的值,
     * 并将该值拆分成单个的词,然后按单词发出新的tuple。
     */
    @Override
    public void execute(Tuple input) {
        Data data = (Data) input.getValueByField("data");
        if (data.getTime() == null || data.getTitle() == null ){
            return;
        }
//        System.out.println("title:\t"+data.getTitle()+"\t\t\t\t\t\t\t\t\ttime:\t"+data.getTime());
        String title = data.getTitle().substring(2,data.getTitle().length()-2);

        String[] args = new String[] {"-props", "StanfordCoreNLP-chinese.properties"};
        Properties properties = StringUtils.argsToProperties(args);
//        StanfordCoreNLP pipline = new StanfordCoreNLP(PropertiesUtils.asProperties(
//                "annotators", "tokenize,ssplit",
//                "ssplit.isOneSentence", "true",
//                "tokenize.language", "zh",
//                "segment.model", "edu/stanford/nlp/models/segmenter/chinese/ctb.gz",
//                "segment.sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese",
//                "segment.serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz",
//                "segment.sighanPostProcessing", "true"
//        ));

        StanfordCoreNLP pipline = new StanfordCoreNLP(properties);
        Annotation annotation = new Annotation(title);
        pipline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        ArrayList<String> words = new ArrayList<String>();
        for (CoreMap sentence : sentences){
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                if ("".equals(word.replaceAll("\\pP|\\pS", ""))){
                    continue;
                }
                if (word.length() <= 1){
                    continue;
                }
                words.add(word);
            }
        }
        collector.emit(new Values(words));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("words"));
    }


}
