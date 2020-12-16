package com.sinosig.suntrtc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author caoguangcong
 * @date 2020/11/2 13:43
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置了Swagger Docket的bean实例
     *
     * @return Docket对象
     */
    @Bean
    public Docket docket(Environment environment) {

        //设置要显示Swagger的环境
        //Profiles profiles = Profiles.of("dev", "test");
        //通过environment.acceptsProfiles(profiles)判断是否处在自己设定的环境中
        //boolean flag = environment.acceptsProfiles(profiles);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                //配置分组，可以使用不同的groupName定义多个Docket bean，分为多个组，用于协同开发
                .groupName("sinosig")
                //enable是否启动Swagger，如果为false，则Swagger不能再浏览器中访问
                .enable(true)
                .select()
                /*
                RequestHandlerSelectors：配置要扫描接口的方式
                    basePackage：指定要扫描的包（常用）
                    any：扫描全部
                    none：都不扫描
                    withClassAnnotation：扫描带该注解的类，参数是一个注解的class对象
                    withMethodAnnotation：扫描带该注解的方法，参数是一个注解的class对象
                 */
                .apis(RequestHandlerSelectors.basePackage("com.sinosig"))
                /*
                .paths(PathSelectors.ant("/sinosig/**"))
                设置扫描指定路径，
                    ant：只扫描 sinosig 路径下的接口（举例）
                    any：扫描全部
                    none：都不扫描
                    regex：根据正则表达式去匹配
                 */
                .build();
    }

    /**
     * 配置Api信息并获取ApiInfo对象
     *
     * @return ApiInfo对象
     */
    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("sinosig", "https://www.sinosig.com/", "");
        //参数：标题、描述、版本、服务条款地址、作者信息、许可、许可url地址
        return new ApiInfo("Api Documentation",
                "Api Documentation",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}
