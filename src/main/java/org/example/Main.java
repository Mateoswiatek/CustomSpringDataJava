package org.example;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String[] methodNames() default {};
}

@MyAnnotation
interface MyInterface {
    void myMethod();
    void myMethod2();
    void myMethod3();
}

public class Main {
    public static void main(String[] args) {
        MyAnnotation annotation = MyInterface.class.getAnnotation(MyAnnotation.class);

        if(annotation == null) { throw new RuntimeException("Annotation not found"); }
        Method[] methods = MyInterface.class.getMethods();
        String[] methodNames = new String[methods.length];

        for(int i = 0; i < methods.length; i++) {
            methodNames[i] = methods[i].getName();
        }

        for (String methodName : methodNames) {
            System.out.println(methodName);
        }

    }
}