package com.albusxing.dobby.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Albusxing
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DemoJob {

    private static final String jobName = "DEMO";

    @XxlJob("demoJob")
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobHelper.log(" >>>>>> 开始推送{}, prams:{}", jobName, param);
        try {
            //
        } catch (Exception e) {
            XxlJobHelper.log("推送{}失败", jobName, e);
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }

}
