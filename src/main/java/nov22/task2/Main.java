package nov22.task2;

public class Main {
    public static void main(String[] args) {
        First first = new First() {
            @Override
            public void method(Integer a) {
                System.out.println(a);
            }

            @Override
            public void method2(Integer a) {
                System.out.println(a);
            }
        };

        Second second = (a) -> System.out.println(a + 1);

        first.method(1);

        second.method(2);
    }
}

interface First {
    void method(Integer a);

    void method2(Integer a);
}

interface Second {
    void method(Integer b);
}