package com.example.bean_repository;

import com.example.filters.EmployeeResourceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanRepository {

    @Bean
    public FilterRegistrationBean<EmployeeResourceFilter> employeeResourceFilter(){
        FilterRegistrationBean<EmployeeResourceFilter> filterFilterRegistrationBean = new FilterRegistrationBean();
        filterFilterRegistrationBean.setFilter(new EmployeeResourceFilter());
        filterFilterRegistrationBean.addUrlPatterns("/employee", "/employee/*");//var args
        return filterFilterRegistrationBean;
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        return new RestTemplate(factory);
    }
}
