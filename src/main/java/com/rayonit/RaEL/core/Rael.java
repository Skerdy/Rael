package com.rayonit.RaEL.core;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public class Rael {

    /**
     * {@link Rael#operator} is a {@link RaelOperator} which defines the operation to apply to this specific {@link Rael} instance's childs.
     * It might be null, when the children list {@link Rael#records} is empty.
     */
    private RaelOperator operator;

    /**
     * {@link Rael#records} are the current {@link Rael} instance childrens if there are any. By default the list is initialised empty.
     * It determines whether this {@link Rael} is "atomic" or not.
     */
    private List<Rael> records;

    private boolean root;
    private String expression = "";

    public Rael() {
        this.root =false;
        this.records = new ArrayList<>();
    }

    public Rael(RaelOperator operator) {
        this.operator = operator;
        this.root = false;
    }

    public void addChar(StringBuilder stringBuilder, char ch){
        this.expression = stringBuilder.append(expression).append(ch).toString();
    }

    public void appendExpression(String exp){
        this.expression+=exp;
    }

    public void addRecord(Rael rael){
        this.records.add(rael);
    }

    public RaelOperator getOperator() {
        return operator;
    }

    public void setOperator(RaelOperator operator) {
        this.operator = operator;
    }

    public boolean isAtomic() {
        return records.isEmpty();
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public List<Rael> getRecords() {
        return records;
    }

    public void setRecords(List<Rael> records) {
        this.records = records;
    }
}
