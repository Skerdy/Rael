package com.rayonit.RaEL.predicate;

import com.rayonit.RaEL.core.*;
import com.rayonit.RaEL.mongodb.PairGenerator;
import com.rayonit.RaEL.predicate.core.PredicateRegistry;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PredicateGenerator {

    private PairGenerator pairGenerator;
    private RaELParser raELParser;
    private Rael rael;
    private PredicateRegistry predicateRegistry;

    public PredicateGenerator(PredicateRegistry predicateRegistry,String predicateExpression) {
        this.pairGenerator = new PairGenerator();
        this.raELParser = new RaELParser();
        this.rael = raELParser.buildRoot(predicateExpression);
        this.predicateRegistry = predicateRegistry;
        System.out.println("U konstruct nje predicate ");
    }

    public Predicate<Object> generatePredicate(){
       return generateAtomicPredicate(rael);
    }

    private Predicate<Object> generateAtomicPredicate(Rael rael){
        Predicate<Object> predicate = null;
        if(rael.isAtomic()){
            RaelPair raelPair = pairGenerator.generateStaticPair(rael.getExpression());
            predicate = data->predicateRegistry.invoke(data, raelPair.getKey().trim()).equals(((String)raelPair.getValue()).trim());
            System.out.println("U gjenerua nje predicate : " + "key = " + raelPair.getKey() + " value = "  + raelPair.getValue());
            return predicate;
        }

        RaelOperator operator = rael.getOperator();
        for (int i = 0; i < rael.getRecords().size(); i++) {
            if(operator.getOperator()==RaelOperator.AND.getOperator()){
                if(predicate == null){
                    predicate = generateAtomicPredicate(rael.getRecords().get(i));
                }
                else{
                    predicate = predicate.and(generateAtomicPredicate(rael.getRecords().get(i)));
                }
            }
            else if(operator.getOperator() == RaelOperator.OR.getOperator()){
                if(predicate == null){
                    System.out.println("Operator OR predicate eshte null");
                    predicate = generateAtomicPredicate(rael.getRecords().get(i));
                }
                else {
                    System.out.println("Operator OR predicate nuk eshte null");

                    predicate = predicate.or(generateAtomicPredicate(rael.getRecords().get(i)));

                    System.out.println("Pasi u gjenerua dhe ju shtua or");
                }
            }
        }
        return predicate;
    }
}
