package site.yiliu.demo.aaaaatest.entity;

import com.google.common.base.CaseFormat;
import lombok.Data;
import site.yiliu.demo.mybatis.annotation.Invisible;

@Data
public class Test {

  @Invisible private int id;
  private String aText1;
  private String aText2;
}
