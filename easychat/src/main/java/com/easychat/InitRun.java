package com.easychat;

import com.easychat.websocket.netty.NettyWebSocketStarter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component("initRun")
public class InitRun implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);

    @Resource
    private DataSource dataSource;

    @Resource
    private NettyWebSocketStarter nettyWebSocketStarter;

    @Override
    public void run(ApplicationArguments args) {
        try (Connection connection = dataSource.getConnection()) {
            new Thread(nettyWebSocketStarter).start();
            logger.info("服务启动成功，可以开始愉快的开发了");
        } catch (SQLException e) {
            logger.error("数据库连接失败，请检查连接地址和凭据", e);
        } catch (Exception e) {
            logger.error("服务启动失败", e);
        }
    }
}
