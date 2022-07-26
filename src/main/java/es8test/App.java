package es8test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = { ElasticsearchDataAutoConfiguration.class })
public class App implements ApplicationRunner {


    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        log.info("startup TLA backend app");
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory(8090);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info(
            "process command line args: {}", String.join(
                ", ", args.getOptionNames().stream().map(arg -> "--%s".formatted(arg)).toList()
            )
        );
        if (args.containsOption("shutdown")) {
            shutdown(0);
        }
    }

    /**
     * terminates the application with the specified exit code.
     * If exit code is not 0, or if any <code>ExitCodeGenerator</code> beans exist in the context,
     * an <code>ExitCodeEvent</code> is being published to the application context.
     */
    public void shutdown(int exitCode) {
        System.exit(
            SpringApplication.exit(
                applicationContext, () -> exitCode
            )
        );
    }
}
