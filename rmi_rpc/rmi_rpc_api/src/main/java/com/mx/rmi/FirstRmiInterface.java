package com.mx.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author: miao
 * @date 2022/3/30
 */
// 定义RMI远程服务接口， 必须是标记接口Remote的实现
public interface FirstRmiInterface extends Remote {
    // RMI 要求 所有的远程服务方法必须抛出 RemoteException
    String getRmiInfo(String name) throws RemoteException;
}
