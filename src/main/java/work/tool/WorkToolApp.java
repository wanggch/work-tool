package work.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * 项目启动类
 * @author: wanggc
 */
@Slf4j
@SpringBootApplication
public class WorkToolApp {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WorkToolApp.class, args);
        String [] names = context.getBeanDefinitionNames();
        Arrays.sort(names);
        log.info("## work tool application run successfully. ##");
        log.info("bean definition names:");
        Arrays.asList(names).forEach(name -> log.info(name));
    }
}