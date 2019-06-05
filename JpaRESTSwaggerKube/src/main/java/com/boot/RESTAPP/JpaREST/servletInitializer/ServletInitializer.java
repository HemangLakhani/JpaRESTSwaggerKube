package com.boot.RESTAPP.JpaREST.servletInitializer;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.boot.RESTAPP.JpaREST.JpaRestApplication;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(JpaRestApplication.class);
  }

}