package com.rayonit.RaEL.mongodb;

import com.rayonit.RaEL.core.*;
import org.json.simple.JSONObject;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public class MongoQueryGenerator implements RaelOperationGenerator<Query, JSONObject> {

    private PairGenerator pairGenerator;
    private RaELParser raELParser;
    private Rael rael;
    private Criteria criteria;


    public MongoQueryGenerator(PairGenerator pairGenerator, RaELParser raELParser, String expression) {
        this.pairGenerator = pairGenerator;
        this.raELParser = raELParser;
        if (pairGenerator.isDynamic(expression)) {
        }
        rael = raELParser.buildRoot(expression);
    }

    public MongoQueryGenerator(String expression) {
        this.pairGenerator = new PairGenerator();
        this.raELParser = new RaELParser();
        this.rael = raELParser.buildRoot(expression);
    }

    @Override
    public Query generate() {
        Query query = new Query();
        criteria = generateCriteria(rael,null);
        query.addCriteria(criteria);
        return query;
    }

    @Override
    public Query generate(JSONObject jsonObject) {
        Query query = new Query();
        query.addCriteria(generateCriteria(rael, jsonObject));
        return query;
    }


    // recursive

    private Criteria generateCriteria(Rael rael, JSONObject object) {
        Criteria criteria;

        if (rael.isAtomic()) {
            RaelPair raelPair;
            if (object == null) {
                raelPair = pairGenerator.generateStaticPair(rael.getExpression());
            } else {
                raelPair = pairGenerator.generateDynamicPair(rael.getExpression(), object);
            }
            criteria = Criteria.where(raelPair.getKey()).is(raelPair.getValue());
            return criteria;
        }

        // kriterias per kete current rael
        Criteria[] criterias = new Criteria[rael.getRecords().size()];

        for (int i = 0; i < rael.getRecords().size(); i++) {
            criterias[i] = generateCriteria(rael.getRecords().get(i), object);
        }

        if (rael.getOperator().equals(RaelOperator.AND)) {
            criteria = Criteria.where("").andOperator(criterias);
        } else {
            criteria = Criteria.where("").orOperator(criterias);
        }
        return criteria;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }
}
