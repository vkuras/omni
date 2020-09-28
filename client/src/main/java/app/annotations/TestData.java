package app.annotations;

import app.constants.TestDataTypeConstants;

import java.lang.annotation.*;

@Repeatable(TestDataContainer.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestData {
    public int amount() default 1;
    public TestDataTypeConstants testDataType();
    public boolean keep() default false;


}
