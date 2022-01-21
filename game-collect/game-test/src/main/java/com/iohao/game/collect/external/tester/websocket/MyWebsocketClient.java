package com.iohao.game.collect.external.tester.websocket;

import cn.hutool.core.util.StrUtil;
import com.iohao.game.collect.common.ActionCont;
import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.collect.proto.LoginVerify;
import com.iohao.little.game.common.kit.ProtoKit;
import com.iohao.little.game.net.external.bootstrap.ExternalCont;
import com.iohao.little.game.net.external.bootstrap.handler.codec.ExternalDecoder;
import com.iohao.little.game.net.external.bootstrap.handler.codec.ExternalEncoder;
import com.iohao.little.game.net.external.bootstrap.message.ExternalMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

/**
 * @author 洛朱
 * @date 2022-01-13
 */
@Slf4j
public class MyWebsocketClient {
    WebSocketClient webSocketClient;

    public static void main(String[] args) throws Exception {
        MyWebsocketClient websocketClient = new MyWebsocketClient();
        websocketClient.init();
        websocketClient.start();

        log.info("start ws://127.0.0.1:10088/websocket");
    }

    private ExternalMessage getExternalMessage() {
        ExternalMessage request = new ExternalMessage();
        request.setCmdCode((short) 1);
        request.setProtocolSwitch((byte) 100);

        // 路由
        int subCmd = 1;
        int cmd = ActionCont.userModuleCmd;
        request.setCmdMerge(cmd, subCmd);

        // 业务数据
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.jwt = ("test");
        request.setData(loginVerify);


        return request;
    }

    private ByteBuf toByteBuf(ExternalMessage message) {
        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();
        ByteBuf byteBuf = Unpooled.buffer(headLen);
        ExternalEncoder.encode(message, byteBuf);
        return byteBuf;
    }

    public void start() {
        webSocketClient.connect();
    }

    public void init() throws Exception {
        var url = "ws://{}:{}" + GameConfig.websocketPath;

        var wsUrl = StrUtil.format(url, GameConfig.externalIp, GameConfig.externalPort);
        log.info("ws url : {}", wsUrl);
        webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {

            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                // 建立连接后 执行的方法
                ExternalMessage request = getExternalMessage();

//                // 发送
//                for (int i = 0; i < 6; i++) {
//
//                }


                var byteBuf = toByteBuf(request);
                byte[] data1 = byteBuf.array();
                data1 = new byte[]{0, 21, 0, 1, 100, 0, 1, 0, 1, 0, 0, 0, 0, 0, 6, 10, 4, 116, 101, 115, 116};

                log.info("client 发送消息 {}", request);
                log.info("client 发送消息 {}", data1);
                System.out.println();

                webSocketClient.send(data1);
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收消息
                log.info("client 收到消息 {}", byteBuffer.array());

                ByteBuf in = Unpooled.wrappedBuffer(byteBuffer);

                ExternalMessage message = ExternalDecoder.decode(in);
                byte[] data = message.getData();
                LoginVerify loginVerify = ProtoKit.parseProtoByte(data, LoginVerify.class);

//                LoginVerify.

                log.info("client 收到消息 {}", message);
                log.info("LoginVerify {}", loginVerify);
            }

            @Override
            public void onMessage(String msg) {
                // 接收消息
                log.info("收到 String 消息========== {}", msg);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                log.info("链接已关闭: [{}] - [{}] - [{}]", i, s, b);
            }

            @Override
            public void onError(Exception e) {
                log.error(e.getMessage(), e);
                e.printStackTrace();
                System.out.println("发生错误已关闭");
            }
        };

    }
}
