package com.albusxing.dobby.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author liguoqing
 */
@Slf4j
public class LogUtil {

    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static void  report(String url, String method, String desc, String params, String returnInfo, String exceptionMsg) {
        StringBuilder sb = new StringBuilder("\n------- Controller log report ----- ").append(sdf.get().format(new Date()));
        sb.append(" --------------------------\n");
        sb.append("请求Url         	: ").append(url).append("\n");
        sb.append("请求方法  	  	: ").append(method).append("\n");
        sb.append("方法描述        	: ").append(desc).append("\n");
        sb.append("请求参数      	: ").append(params).append("\n");
        sb.append("返回结果      	: ").append(returnInfo).append("\n");
        if (!StringUtils.isEmpty(exceptionMsg)) {
            sb.append("异常信息      	: ").append(exceptionMsg).append("\n");
        }
        sb.append("-------------------------------------------------------------------------------------\n");
        log.info(sb.toString());
    }

}
