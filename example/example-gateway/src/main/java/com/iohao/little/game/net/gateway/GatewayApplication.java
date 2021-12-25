package com.iohao.little.game.net.gateway;

import com.alipay.remoting.exception.RemotingException;
import com.iohao.example.common.ActionCont;
import com.iohao.example.common.Apple;
import com.iohao.example.common.Book;
import com.iohao.little.game.action.skeleton.core.CmdInfo;
import com.iohao.little.game.action.skeleton.core.CmdInfoFlyweightFactory;
import com.iohao.little.game.action.skeleton.protocol.RequestMessage;
import com.iohao.little.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.little.game.net.server.module.ModuleInfoManager;
import com.iohao.little.game.net.server.module.ModuleInfoProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println();

        // 启动网关
        GatewayServer gatewayServe = new GatewayServer();
        gatewayServe.init();

    }

    /**
     * 请求 A 服务器的方法
     */
    @GetMapping("/apple/{name}")
    public String apple(@PathVariable String name) {
        log.info("apple: {}", name);

        CmdInfoFlyweightFactory factory = CmdInfoFlyweightFactory.me();
        CmdInfo cmdInfo = factory.getCmdInfo(ActionCont.AppleModule.cmd, ActionCont.AppleModule.name);

        Apple apple = new Apple();
        apple.setAge(name.hashCode());
        apple.setName(name);

        String data = (String) invokeSync(cmdInfo, apple);

        return data;
    }

    /**
     * 请求 B 服务器的方法
     */
    @GetMapping("/book/{name}")
    public String book(@PathVariable String name) {
        log.info("book: {}", name);

        CmdInfoFlyweightFactory factory = CmdInfoFlyweightFactory.me();
        CmdInfo cmdInfo = factory.getCmdInfo(ActionCont.BookModule.cmd, ActionCont.BookModule.name);

        Book book = new Book();
        book.setName(name);
        book.setAge(222);

        String data = (String) invokeSync(cmdInfo, book);

        return data;
    }

    /**
     * 请求 B 服务器的方法， B 服务器方法中 请求A服务器拿数据
     */
    @GetMapping("/book_visit_apple/{age}")
    public String bookVisitApple(@PathVariable Integer age) {
        log.info("book_age: {}", age);

        CmdInfo cmdInfo = ActionCont.BookModule.info.cmdInfo(ActionCont.BookModule.get_apple_age);

        String data = (String) invokeSync(cmdInfo, age);

        return data;
    }

    /**
     * 请求 B 服务器的方法
     * <pre>
     *     B 服务器方法中会发布一个广播（发布订阅）
     *     这个（发布订阅）由网关来消费
     * </pre>
     *
     * @param message
     * @return
     */
    @GetMapping("/message_queue/{message}")
    public String messageQueue(@PathVariable String message) {

        CmdInfo cmdInfo = ActionCont.BookModule.info.cmdInfo(ActionCont.BookModule.message_queue);
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdInfo(cmdInfo);

        requestMessage.setData(message);
        requestMessage.setUserId(100);

        String data = (String) invokeSync(requestMessage);
        log.info("messageQueue : {}", data);

        return data;
    }

    /**
     * @param cmdInfo 平常大部分框架使用一个 cmd 来约定协议,这里使用主子是为了模块的划分清晰, 当然这样规划还有更多好处. 但目前不是讨论的重点.
     * @param data    请求数据
     * @return 响应数据
     */
    private Object invokeSync(CmdInfo cmdInfo, Object data) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setCmdInfo(cmdInfo);
        requestMessage.setData(data);
        return this.invokeSync(requestMessage);
    }

    private Object invokeSync(RequestMessage requestMessage) {
        CmdInfo cmdInfo = requestMessage.getCmdInfo();
        ModuleInfoManager me = ModuleInfoManager.me();
        ModuleInfoProxy moduleInfo = me.getModuleInfo(cmdInfo.getCmdMerge());

        try {
            ResponseMessage responseCommand = (ResponseMessage) moduleInfo.invokeSync(requestMessage);
            Object invokeData = responseCommand.getData();
            log.info("invokeData: {}", invokeData);
            return invokeData.toString();
        } catch (RemotingException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}