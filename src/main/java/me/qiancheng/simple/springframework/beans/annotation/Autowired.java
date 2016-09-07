package me.qiancheng.simple.springframework.beans.annotation;

import java.lang.annotation.*;

/**
 * @author qian.cheng
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

}
