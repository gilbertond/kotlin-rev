##My stuff
Disable /error page, can achieve that with any of 3 ways below
> * `server.error.whitelabel.enabled=false` in properties file
> *  `spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration` OR `org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration` for springboot 2.0
> * `@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})` on main class

