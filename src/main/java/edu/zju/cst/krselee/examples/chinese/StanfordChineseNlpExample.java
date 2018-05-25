//package edu.zju.cst.krselee.examples.chinese;
//
//import edu.stanford.nlp.dcoref.CorefChain;
//import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
//import edu.stanford.nlp.ling.CoreAnnotations;
//import edu.stanford.nlp.ling.CoreLabel;
//import edu.stanford.nlp.pipeline.Annotation;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import edu.stanford.nlp.semgraph.SemanticGraph;
//import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
//import edu.stanford.nlp.trees.Tree;
//import edu.stanford.nlp.trees.TreeCoreAnnotations;
//import edu.stanford.nlp.util.CoreMap;
//import edu.stanford.nlp.util.PropertiesUtils;
//import edu.stanford.nlp.util.StringUtils;
////import edu.zju.cst.krselee.examples.english.StanfordEnglishNlpExample;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * Created by KrseLee on 2016/11/4.
// */
//public class StanfordChineseNlpExample {
//
//
//    public static void main(String[] arg) {
////        StanfordCoreNLP pipline = new StanfordCoreNLP("StanfordCoreNLP-chinese.properties");
//
//        String[] args = new String[] {"-props", "StanfordCoreNLP-chinese.properties"};
//        Properties properties = StringUtils.argsToProperties(args);
////        StanfordCoreNLP pipline = new StanfordCoreNLP(PropertiesUtils.asProperties(
////                "annotators", "tokenize,ssplit",
////                "ssplit.isOneSentence", "true",
////                "tokenize.language", "zh",
////                "segment.model", "edu/stanford/nlp/models/segmenter/chinese/ctb.gz",
////                "segment.sighanCorporaDict", "edu/stanford/nlp/models/segmenter/chinese",
////                "segment.serDictionary", "edu/stanford/nlp/models/segmenter/chinese/dict-chris6.ser.gz",
////                "segment.sighanPostProcessing", "true"
////        ));
//
//        StanfordCoreNLP pipline = new StanfordCoreNLP(properties);
//        Annotation annotation = new Annotation("我觉得还行吧？");
//        pipline.annotate(annotation);
//        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
//        for (CoreMap sentence : sentences){
//            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)){
//                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                System.out.println(word);
//            }
//        }
//
//
//
//    }
//
//}
