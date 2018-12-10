package com.rayonit.RaEL.mongodb;

import com.rayonit.RaEL.core.RaELParser;
import com.rayonit.RaEL.core.Rael;
import com.rayonit.RaEL.core.RaelOperationGenerator;
import com.rayonit.RaEL.core.RaelPair;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public class MongoUpdateGenerator implements RaelOperationGenerator<Update, JSONObject> {

    private PairGenerator pairGenerator;
    private RaELParser raELParser;
    private Rael rael;

    public MongoUpdateGenerator(PairGenerator pairGenerator, RaELParser raELParser, String expression) {
        this.pairGenerator = pairGenerator;
        this.raELParser = raELParser;
        rael = raELParser.buildRoot(expression);
    }

    public MongoUpdateGenerator(String expression) {
        this.pairGenerator = new PairGenerator();
        this.raELParser = new RaELParser();
        this.rael = raELParser.buildRoot(expression);
    }


    @Override
    public Update generate() {
        Update update = new Update();
        if(rael.getRecords().isEmpty()){
            RaelPair raelPair = pairGenerator.generateStaticPair(this.rael.getExpression());
            if(raelPair.getValue()!=null)
            update = update.set(raelPair.getKey(), raelPair.getValue());
        }else {
            for (Rael rael : rael.getRecords()) {
                RaelPair raelPair = pairGenerator.generateStaticPair(rael.getExpression());
                if(raelPair.getValue()!=null)
                update = update.set(raelPair.getKey(), raelPair.getValue());
            }
        }
        return update;
    }

    @Override
    public Update generate(JSONObject object) {
        Update update = new Update();
        if(rael.getRecords().isEmpty()){
            RaelPair raelPair = pairGenerator.generateDynamicPair(this.rael.getExpression(), object);
            if(raelPair.getValue()!=null)
            update = update.set(raelPair.getKey(), raelPair.getValue());
        }else {
            for (Rael rael : rael.getRecords()) {
                RaelPair raelPair = pairGenerator.generateDynamicPair(rael.getExpression(), object);
                if(raelPair.getValue()!=null)
                update = update.set(raelPair.getKey(), raelPair.getValue());
            }
        }
        return update;
    }


}
