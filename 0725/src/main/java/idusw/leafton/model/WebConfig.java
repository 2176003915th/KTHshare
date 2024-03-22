package idusw.leafton.model;


import groovy.util.logging.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/home/passion/images/**").addResourceLocations("file:/home/passion/images/");

//        registry.addResourceHandler("/images/**").addResourceLocations("file:C:\\images\\");
        // 만약 위치가 c:\\images\\event\\main이라면 handler = db쪽은 /images/event/main으로 경로로찾아갈수있게해야함

    }
}