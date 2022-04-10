package com.mx.server;

import com.mx.rmi.FirstRmiInterface;

import java.rmi.Naming;

/**
 * @author: miao
 * @date 2022/3/30
 */

public class FirstRmiClient {

    public static void main(String[] args) {
        FirstRmiInterface firstrmi = null;
        try {
            // 类型为Object，且是Proxy的子类型。
            firstrmi = (FirstRmiInterface) Naming.lookup("rmi://localhost:9999/firstrmi");

            String result = firstrmi.getRmiInfo("hello rmi");

            System.out.println(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
