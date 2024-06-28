package expression;


import java.util.List;

// :NOTE: неудачный нейминг
public interface General extends Expression, TripleExpression, ListExpression {
    @Override
    int evaluate(int x);

    @Override
    int evaluate(int x, int y, int z);

    @Override
    boolean equals(Object o);
    @Override
    int evaluate(List<Integer> variables);

}
