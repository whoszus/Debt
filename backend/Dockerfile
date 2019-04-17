FROM nimmis/java-centos:openjdk-8-jdk
#作者信息
MAINTAINER tinker <whoszus@yahoo.com>
# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 设置字符集
RUN locale
RUN localedef -i zh_CN -c -f UTF-8 zh_CN.UTF-8
RUN echo "export LC_ALL=zh_CN.UTF-8" >> /etc/profile && source /etc/profile
ENV LANG zh_CN.UTF-8
ENV LC_CTYPE zh_CN.UTF-8
COPY ./JAVA_OPTS.sh /opt/JAVA_OPTS.sh
RUN source /opt/JAVA_OPTS.sh
COPY tagrt/keros_debt-1.0.0-SNAPSHOT.jar debt.jar
# 启动时执行脚本
ENTRYPOINT ["java","$JAVA_OPTS","-jar","/debt.jar"]