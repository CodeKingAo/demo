package site.yiliu.demo.mybatis.annotation;

import java.lang.annotation.*;

/** 动态注解忽略字段 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Invisible {
}
