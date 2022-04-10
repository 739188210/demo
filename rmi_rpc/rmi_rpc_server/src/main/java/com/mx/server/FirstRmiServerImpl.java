package com.mx.server;

import com.mx.rmi.FirstRmiInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 定义RMI远程服务接口， 必须是标记接口Remote的直接或间接实现
 * 若不会创建基于RMI的服务标准实现，则可继承unicastRemoteObject类型
 * RMI  要求 所有的远程服务方法必须抛出 RemoteException  包括构造方法
 *
 * @author: miao
 * @date 2022/3/30
 */
public class FirstRmiServerImpl extends UnicastRemoteObject implements FirstRmiInterface {

    public FirstRmiServerImpl() throws RemoteException {
        super();
    }

    // 服务端 数据处理的服务方法代码
    @Override
    public String getRmiInfo(String name) throws RemoteException {

        System.out.println("received from the client's params: " + name);
        return "get it~";
    }
}
