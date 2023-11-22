package nov22.task1;

abstract class AbstractIntegerOperation {
    abstract Integer operation(Integer a, Integer b);
}

class AddOperation extends AbstractIntegerOperation {
    @Override
    Integer operation(Integer a, Integer b) {
        return a + b;
    }
}

class SubtractOperation extends AbstractIntegerOperation {
    @Override
    Integer operation(Integer a, Integer b) {
        return a - b;
    }
}

class MultiplyOperation extends AbstractIntegerOperation {
    @Override
    Integer operation(Integer a, Integer b) {
        return a * b;
    }
}

public class Main {
    public static void main(String[] args) {
        AddOperation addOperation = new AddOperation();
        SubtractOperation subtractOperation = new SubtractOperation();
        MultiplyOperation multiplyOperation = new MultiplyOperation();

        System.out.println(addOperation.operation(1, 2));
        System.out.println(subtractOperation.operation(4, 2));
        System.out.println(multiplyOperation.operation(1, 2));
    }
}
