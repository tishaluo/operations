  
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# 让 JAR 文件名不固定也能拷贝
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# 时区/编码用 JVM 参数即可，不必改系统文件
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai"

# 容器内监听端口
EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]