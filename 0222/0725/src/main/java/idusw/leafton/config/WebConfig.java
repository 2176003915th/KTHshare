package idusw.leafton.config;

import groovy.util.logging.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/images/main_category**") //이렇게 경로가 들어오면 ex) img src="/static/images/main_category/이미지.png"
                .addResourceLocations("file:static/images/main_category/")
                .setCachePeriod(0);
    }
}
