##My stuff
Disable /error page, can achieve that with any of 3 ways below
> * `server.error.whitelabel.enabled=false` in properties file
> *  `spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration` OR `org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration` for springboot 2.0
> * `@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})` on main class


##Links
###Contracts
`https://piotrminkowski.wordpress.com/tag/spring-cloud-contract/`
`https://dzone.com/articles/defining-spring-cloud-contracts-in-open-api`
`https://github.com/spring-cloud/spring-cloud-contract/tree/master/samples/standalone/yml`
`https://dzone.com/articles/apache-kafka-vs-integration-middleware-mq-etl-esb`

###CF deploy script
`https://developer.ibm.com/answers/questions/193849/how-to-deploy-shell-script/`
`https://github.com/fe01134/djangobluemix`