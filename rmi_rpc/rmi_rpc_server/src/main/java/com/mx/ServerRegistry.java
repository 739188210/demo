package com.mx;

import com.mx.rmi.FirstRmiInterface;
import com.mx.server.FirstRmiServerImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * RPC: Remote Procedure Call
 * 服务器端的启动类，创建注册 服务到 Registry 注册中心上。
 *
 * @author: miao
 * @date 2022/3/30
 */
public class ServerRegistry {

    public static void main(String[] args) {
        try {
            // 创建 服务对象
            FirstRmiInterface firstRmi = new FirstRmiServerImpl();
            // 注册到(创建)Registry注册中心 创建守护线程
            LocateRegistry.createRegistry(9999);
            // 注册服务 bind 服务注册重复抛异常 ， rebind服务注册重复直接覆盖
            Naming.bind("rmi://localhost:9999/firstrmi", firstRmi);
            //    Naming.rebind("rmi://localhost:9999/firstrmi", firstRmi);
            System.out.println("rmi server has been started!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
