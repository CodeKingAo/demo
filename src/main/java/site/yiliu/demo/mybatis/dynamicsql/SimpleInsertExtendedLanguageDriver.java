package site.yiliu.demo.mybatis.dynamicsql;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import site.yiliu.demo.mybatis.annotation.Invisible;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 动态注解新增 */
public class SimpleInsertExtendedLanguageDriver extends XMLLanguageDriver
    implements LanguageDriver {
  private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

  @Override
  public SqlSource createSqlSource(
      Configuration configuration, String script, Class<?> parameterType) {
    Matcher matcher = inPattern.matcher(script);
    if (matcher.find()) {
      StringBuffer ss = new StringBuffer("(");

      for (Field field : parameterType.getDeclaredFields()) {
        // 如果不是加了忽略注解的字段就去拼接
        if (!field.isAnnotationPresent(Invisible.class)) {
          ss.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) + ", ");
        }
      }
      ss.deleteCharAt(ss.lastIndexOf(","));
      ss.append(") VALUES ( ");
      for (Field field : parameterType.getDeclaredFields()) {
        if (!field.isAnnotationPresent(Invisible.class)) {
          ss.append("#{" + field.getName() + "}, ");
        }
      }

      ss.deleteCharAt(ss.lastIndexOf(","));
      ss.append(") ");
      script = matcher.replaceAll(ss.toString());

      script = "<script>" + script + "</script>";
    }
    return super.createSqlSource(configuration, script, parameterType);
  }
}
