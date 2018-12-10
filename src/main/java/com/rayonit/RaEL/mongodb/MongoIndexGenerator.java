package com.rayonit.RaEL.mongodb;

import com.rayonit.RaEL.core.RaELParser;
import com.rayonit.RaEL.core.Rael;
import com.rayonit.RaEL.core.RaelPair;
import com.rayonit.RaEL.core.StaticOperationGenerator;
import org.bson.Document;
import org.springframework.data.mongodb.core.index.CompoundIndexDefinition;
import org.springframework.data.mongodb.core.index.Index;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public class MongoIndexGenerator implements StaticOperationGenerator<Index> {

    private PairGenerator pairGenerator;
    private RaELParser raELParser;
    private Rael rael;

    public MongoIndexGenerator(PairGenerator pairGenerator, RaELParser raELParser, String expression){
        this.pairGenerator = pairGenerator;
        this.raELParser = raELParser;
        this.rael = raELParser.buildRoot(expression);
    }

    public MongoIndexGenerator(String expression) {
        this.pairGenerator = new PairGenerator();
        this.raELParser = new RaELParser();
        this.rael = raELParser.buildRoot(expression);
    }

    @Override
    public Index generate() {
        return createCompound();
    }

    private CompoundIndexDefinition createCompound() {
        Document document = new Document();
        if (this.rael.getRecords().isEmpty()) {
            RaelPair pair = pairGenerator.generateStaticPair(this.rael.getExpression());
            document.append(pair.getKey(), pair.getValue());
        } else {
            for (Rael rael : rael.getRecords()) {
                RaelPair pair = pairGenerator.generateStaticPair(rael.getExpression());
                document.append(pair.getKey(), pair.getValue());
            }
        }
        CompoundIndexDefinition compoundIndexDefinition = new CompoundIndexDefinition(document);
        return compoundIndexDefinition;
    }

}
