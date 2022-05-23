package com.iohao.game.bolt.broker.server.processor;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 渔民小镇
 * @date 2022-05-23
 */
@Slf4j
public class InnerModuleRequestCollectMessageBrokerProcessorTest {

    @Test
    public void main() {
        List<Integer> futures = new ArrayList<>();

        Integer[] empty = new Integer[0];

        var a = futures.toArray(empty);
        log.info("a : {}", Arrays.toString(a));

        futures.add(1);
        futures.add(2);

        var b = futures.toArray(empty);
        log.info("b : {}", Arrays.toString(b));


        var c = futures.toArray(empty);
        log.info("c : {}", Arrays.toString(c));

        futures.clear();
        var d = futures.toArray(empty);
        log.info("d : {}", Arrays.toString(d));


        futures.clear();
        var e = futures.toArray(empty);
        log.info("e : {}", Arrays.toString(e));

        futures.add(1);
        var f = futures.toArray(empty);
        log.info("f : {}", Arrays.toString(f));
    }
}