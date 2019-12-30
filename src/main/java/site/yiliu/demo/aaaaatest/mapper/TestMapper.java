package site.yiliu.demo.aaaaatest.mapper;

import org.apache.ibatis.annotations.*;
import site.yiliu.demo.aaaaatest.entity.Test;
import site.yiliu.demo.mybatis.dynamicsql.SimpleInsertExtendedLanguageDriver;
import site.yiliu.demo.mybatis.dynamicsql.SimpleSelectExtendedLanguageDriver;
import site.yiliu.demo.mybatis.dynamicsql.SimpleUpdateExtendedLanguageDriver;

import java.util.List;

public interface TestMapper {

  @Select("select (#{test})")
  @Lang(SimpleSelectExtendedLanguageDriver.class)
  List<Test> test(Test test);


  @Insert("insert test (#{test})")
  @Lang(SimpleInsertExtendedLanguageDriver.class)
  @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
  int Inserttest(Test test);

  @Update("UPDATE test (#{test}) WHERE id =#{id}")
  @Lang(SimpleUpdateExtendedLanguageDriver.class)
  int Updatetest(Test test);
}
