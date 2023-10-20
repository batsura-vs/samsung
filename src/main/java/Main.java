/*
        Предметная область - которая нравится:
        1) Выбрать три или четыре сущности
        (сущность - это автомобиль, Car - описание сущности в виде класса)
        2) Создать на основе сущностей - классы
           с полями(от 3х полей(св-в)
        3) Создать переопределение (1 или 2)
        4) Создать перегрузку (1 или 2)
        5) Создать наследование (1 или 2)
        6) Создать экзепляры классов (от 3х)

         */

public class Main {
    public static void main(String[] args) {
        GrandParent grandParent = new GrandParent("example");
        grandParent.method();
        grandParent.method("example");
        grandParent.method(1);

        Parent parent = new Parent();
        parent.method();
        parent.method("example");
        parent.method(1);
        parent.method(1, "example2");

        Child child = new Child();
        child.method();
        child.method("example");
        child.method(1);
        child.method(1, "example2");
        child.method(1, "example2", 3);

    }
}

class GrandParent {

    protected String someData;

    public String getSomeData() {
        return someData;
    }

    public void setSomeData(String someData) {
        this.someData = someData;
    }

    public GrandParent() {

    }

    public GrandParent(String someData) {
        this.someData = someData;
    }

    public void method() {
        System.out.println("GrandParent");
    }

    public void method(String example) {
        System.out.println("GrandParent " + example);
    }

    public void method(int example) {
        System.out.println("GrandParent " + example);
    }
}

class Parent extends GrandParent {
    public void method() {
        System.out.println("Parent");
    }

    public void method(String example) {
        System.out.println("Parent " + example);
    }

    public void method(int example) {
        System.out.println("Parent " + example);
    }

    public void method(int example, String example2) {
        System.out.println("Parent " + example + " " + example2);
    }
}

class Child extends Parent {
    public void method() {
        System.out.println("Child");
    }

    public void method(String example) {
        System.out.println("Child " + example);
    }

    public void method(int example) {
        System.out.println("Child " + example);
    }

    public void method(int example, String example2) {
        System.out.println("Child " + example + " " + example2);
    }

    public void method(int example, String example2, int example3) {
        System.out.println("Child " + example + " " + example2 + " " + example3);
    }
}
