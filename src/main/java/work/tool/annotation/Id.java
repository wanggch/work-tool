package work.tool.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 主键注解
 * @author: wanggc
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface Id {
}
