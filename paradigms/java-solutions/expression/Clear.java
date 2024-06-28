package expression;

import java.util.List;

// :NOTE: unused
public class Clear implements General {
    @Override
    public int evaluate(int x) {
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return 0;
    }

    @Override
    public int evaluate(List<Integer> variables) {
        return 0;
    }

}
