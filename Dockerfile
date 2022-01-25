FROM openjdk:8-jdk-alpine
MAINTAINER lgq0218@gmail.com
COPY target/spring-boot-msa-archetype-1.0.0.jar  app.jar
# expose 的作用是什么？在做宿主机和容器端口映射的时候，可以不用这个端口的。
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]


#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]