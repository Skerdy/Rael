package com.rayonit.RaEL.mongodb;

import com.rayonit.RaEL.core.RaelPairGenerator;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

@Component
public class PairGenerator extends RaelPairGenerator<JSONObject> {

    // nese gjen nje payload regex ne expression kthen true ( Thirr nga rooti)
    public boolean isDynamic(String expression){
        if(expression.contains(PAYLOAD_REGEX)){
            return true;
        }
        return false;
    }

}
