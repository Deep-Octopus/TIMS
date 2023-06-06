package com.example.tims.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MySqlField {

        String name() default "";
        String type() default "VARCHAR";
        boolean notNull() default false;
        boolean autoIncrement() default false;
        boolean primaryKey() default false;

        int length() default 255;

        String comment() default "";
}
