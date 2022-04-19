package com.neusoft.jereh.aftersales.assistant.config;

import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.neusoft.jereh.aftersales.common.constant.DataInitConfigEnum;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author: miao
 * @date 2022/3/8
 */
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class DataScopeInterceptor extends AbstractSqlParserHandler implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataScopeInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOGGER.info("intercept has been run");

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        //  获取invocation传参，执行该拦截器的全路径方法 eg. UserPermissionMapper 接口的 getById 方法， com.xx.UserPermissionMapper.getById
        //  String clzReference = mappedStatement.getId();

        // 未配置项无需拦截处理
        if (isNotContainClz(mappedStatement.getResultMaps())) {
            return invocation.proceed();
        }

        Method method = invocation.getMethod();

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        LOGGER.info("plugin has been run");

        if (target instanceof Executor) {
            return Interceptor.super.plugin(target);

        }
        return target;
     //   return Interceptor.super.plugin(target);
    }

    /**
     * 拦截器需要一些变量对象，而且这个对象是支持可配置的。
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    /**
     * 是否不需要拦截方法
     *
     * @param clzReference 方法全路径名
     * @return boolean
     */
    public boolean isNotContainClz(String clzReference) {
        boolean isNotContain = true;
        // eg  com.neusoft.jereh.aftersales.assistant.mapper.devicebasic.DeviceBasicMapper.selectById
        if (!clzReference.contains(".")) {
            return isNotContain;
        }
        String[] splitArr = clzReference.split("\\.");
        String tableName = splitArr[splitArr.length - 2];
        if (tableName.contains("Mapper")) {
            tableName = tableName.substring(0, tableName.indexOf("Mapper"));
        } else if (tableName.contains("Service")) {
            tableName = tableName.substring(0, tableName.indexOf("Service"));
        }
        Set<String> enumSet = DataInitConfigEnum.getEnumName();
        if (enumSet.contains(tableName)) {
            isNotContain = false;
        }
        return isNotContain;
    }

    private boolean isNotContainClz(List<ResultMap> resultMaps) {
        boolean isNotContain = true;
        // eg  com.neusoft.jereh.aftersales.assistant.entity.devicespec.DeviceSpec
        if (resultMaps.size() < 1 || !resultMaps.get(0).getType().getName().contains(".")) {
            return isNotContain;
        }
        String clzReference = resultMaps.get(0).getType().getName();
        String tableName = clzReference.substring(clzReference.lastIndexOf(".") + 1);
        Set<String> enumSet = DataInitConfigEnum.getEnumName();
        if (enumSet.contains(tableName)) {
            isNotContain = false;
        }
        return isNotContain;
    }

}
