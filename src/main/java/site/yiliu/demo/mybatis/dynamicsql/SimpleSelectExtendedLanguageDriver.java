package site.yiliu.demo.mybatis.dynamicsql;

// 一次编写，受益终生

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import site.yiliu.demo.mybatis.annotation.Invisible;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 动态注解查询 */
public class SimpleSelectExtendedLanguageDriver extends XMLLanguageDriver
    implements LanguageDriver {

  private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

  @Override
  public SqlSource createSqlSource(
      Configuration configuration, String script, Class<?> parameterType) {
    Matcher matcher = inPattern.matcher(script);
    if (matcher.find()) {
      StringBuffer ss = new StringBuffer(" ");

      for (Field field : parameterType.getDeclaredFields()) {
        ss.append(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()) + ", ");
      }
      ss.deleteCharAt(ss.lastIndexOf(","));
      ss.append(
          "  FROM  "
              + CaseFormat.LOWER_CAMEL.to(
                  CaseFormat.LOWER_UNDERSCORE, parameterType.getSimpleName())
              + " where ");
      for (Field field : parameterType.getDeclaredFields()) {
        if (!field.isAnnotationPresent(Invisible.class)) {
          String temp = "<if test=\"__field != null\">__column like '%\\${__field}%' and </if>";
          ss.append(
              temp.replaceAll("__field", field.getName())
                  .replaceAll(
                      "__column",
                      CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName())));
        }
      }
      int index = ss.lastIndexOf("and");
      ss.deleteCharAt(index);
      ss.deleteCharAt(index);
      ss.deleteCharAt(index);
      ss.append(" ");
      script = matcher.replaceAll(ss.toString());

      script = "<script>" + script + "</script>";
    }
    return super.createSqlSource(configuration, script, parameterType);
  }
}
