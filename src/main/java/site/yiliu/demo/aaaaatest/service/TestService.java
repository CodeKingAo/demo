package site.yiliu.demo.aaaaatest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.yiliu.demo.aaaaatest.entity.Test;
import site.yiliu.demo.aaaaatest.mapper.TestMapper;

import java.util.List;

@Controller
public class TestService {

  @Autowired TestMapper testMapper;

  @GetMapping("/test")
  @ResponseBody
  Object test() {
      Test test = new Test();
      test.setAText1("1111");
      test.setAText2("2222");
      testMapper.Inserttest(test);
      test.setAText2("3333");
      testMapper.Updatetest(test);
      return testMapper.test(test);
  };
}
