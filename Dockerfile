########################
# 1) Build (Maven)
########################
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /src

# 先拷贝 pom 预热依赖缓存（加速增量构建）
COPY pom.xml .


# 再拷贝源码并编译打包（-T 1C 并行；按需关闭跳测）
COPY src ./src
ARG SKIP_TESTS=true
ARG MVN_PROFILES=
RUN mvn clean package -DskipTests

########################
# 2) Runtime (JRE)
########################
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

# 创建非 root 用户运行更安全
RUN useradd -m -s /bin/bash appuser
USER appuser

# 复制可执行 JAR（文件名不固定也能匹配）
COPY --from=build /src/target/*.jar /app/app.jar

# 容器友好的 JVM 参数与时区/编码
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai"

EXPOSE 8080

# 如启用 Actuator，可加健康检查；未使用可注释掉
# HEALTHCHECK --interval=30s --timeout=5s --start-period=20s \
#   CMD wget -qO- http://127.0.0.1:8080/actuator/health | grep -q UP || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
