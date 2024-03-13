package com.gdu.prj01.anno02;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public Student student() {
    Student student = new Student();
    student.setScores(Arrays.asList(90, 91, 92));
    student.setContacts(new HashSet<String>(Arrays.asList("010-1111-2222", "02-3333-4444")));
    student.setFriends(Map.of("동네친구", "길동", "운동친구", "또치"));
    return student;
  }
}
