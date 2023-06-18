# 基础镜像
FROM openjdk:8


# 拷贝jar包到容器中
COPY demo-0.0.1-SNAPSHOT.jar /app.jar


# 生成数据库数据
# 设置参数
CMD ["--server.port=10086"]
# 启动命令
ENTRYPOINT ["java", "-jar", "/app.jar"]