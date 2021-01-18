//package com.hzsf.chronicanalysis.config.cros;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//@Configuration
//public class CORSConfiguration extends WebMvcConfigurationSupport {
//
//    /*
//     * 这里主要为了解决跨域问题,所以重写addCorsMappings方法
//     */
//    @Override
//    protected void addCorsMappings(CorsRegistry registry) {
//        System.out.println("----------------------");
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "DELETE", "PUT")
//                .maxAge(3600);
//    }
//}
//
