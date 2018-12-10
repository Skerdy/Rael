package com.rayonit.RaEL.core;

import java.util.Stack;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public class RaELParser {

    private final char OPENING_BRACKET = '{';
    private final char CLOSING_BRACKET = '}';

    private final String BRACKET = "Bracket";

    private Rael buildRael(String string){
        int recordsSize = -1;
        Stack<String> brackets = new Stack<>();
        char currentChar;
        Rael rael = new Rael();
        int size = string.length();

        for (int i = 0; i < string.length(); i++) {
            currentChar = string.charAt(i);
            rael.addChar(new StringBuilder(), currentChar);
            String subString = string.substring(i+1);
            if (currentChar == OPENING_BRACKET) {
                brackets.push(BRACKET);
                if(brackets.size()>1){
                    rael.addRecord(buildRael("{"+ subString));
                    recordsSize++;
                    rael.appendExpression(rael.getRecords().get(recordsSize).getExpression().substring(1));
                    string = string.replace(rael.getRecords().get(recordsSize).getExpression(),"");
                    i--;
                }
            }
            else if(currentChar==CLOSING_BRACKET){
                brackets.pop();
                break;
            }
            else if(currentChar== RaelOperator.OR.getOperator()){
                rael.setOperator(RaelOperator.OR);
            }
            else if(currentChar== RaelOperator.AND.getOperator()){
                rael.setOperator(RaelOperator.AND);
            }
            else if(currentChar== RaelOperator.CUNJUCTION.getOperator()){
                rael.setOperator(RaelOperator.CUNJUCTION);
            }
            if(brackets.empty()){
                if(currentChar!= RaelOperator.AND.getOperator() && currentChar!= RaelOperator.OR.getOperator())
                return  rael;
            }
        }
        return rael;
    }


    public Rael buildRoot(String str){
        Rael root = buildRael(str);
        root.setRoot(true);
        return root;
    }


}
