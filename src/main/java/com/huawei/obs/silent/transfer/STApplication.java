package com.huawei.obs.silent.transfer;

import com.huawei.obs.silent.transfer.config.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class STApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(STApplication.class);

    public static void main(String[] args) {
        LOGGER.info("The Silent Transfer is starting...");
        ConfigUtil configInstance = ConfigUtil.getInstance();
        configInstance.init("conf/config.properties");
        LOGGER.info(ConfigUtil.getStringProperty("bucketName"));
    }
}
