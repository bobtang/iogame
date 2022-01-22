package com.iohao.game.collect.external.tester.websocket;

import cn.hutool.core.util.StrUtil;
import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.collect.proto.LoginVerify;
import com.iohao.little.game.common.kit.ProtoKit;
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

//    public static void main(String[] args) {
//
//        ExternalMessage externalMessage = MyWebsocketClient.getExternalMessage();
//        MyBird myBird = new MyBird();
//        myBird.setId(21);
//
//        byte[] protoBytes = ProtoKit.toBytes(myBird);
//        log.info("{}", protoBytes);
//
//        ByteBuf byteBuf = Unpooled.buffer(2);
//        byteBuf.writeShort(21);
//
//        byte[] temp = byteBuf.array();
//
//        log.info("{}", temp);
//
//    }

    private static ExternalMessage getExternalMessage() {
        ExternalMessage request = new ExternalMessage();
        request.setCmdCode((short) 1);
        request.setProtocolSwitch((byte) 0);

        // 路由
        request.setCmdMerge(1, 1);

        // 业务数据
        LoginVerify loginVerify = new LoginVerify();
        loginVerify.jwt = ("test");
        request.setData(loginVerify);


        return request;
    }

    private static ByteBuf toByteBuf(ExternalMessage message) {
//        int headLen = ExternalCont.HEADER_LEN + message.getDataLength();
        int headLen = 1;
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


//                var byteBuf = toByteBuf(request);
//                byte[] data1 = byteBuf.array();
//                data1 = new byte[]{0, 21, 0, 1, 100, 0, 1, 0, 1, 0, 0, 0, 0, 0, 6, 10, 4, 116, 101, 115, 116};
//
//                log.info("client 发送消息 {}", request);
//                log.info("client 发送消息 {}", data1);
//                System.out.println();

                byte[] bytes = ProtoKit.toBytes(request);

                webSocketClient.send(bytes);
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收消息
                byte[] dataContent = byteBuffer.array();
                log.info("client 收到消息 {}", dataContent);



                ExternalMessage message = ProtoKit.parseProtoByte(dataContent, ExternalMessage.class);

                byte[] data = message.getDataContent();

                LoginVerify loginVerify = ProtoKit.parseProtoByte(data, LoginVerify.class);

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
