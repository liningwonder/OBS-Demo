package com.huawei.obs.silent.transfer.config;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    private static final ConfigUtil instance = new ConfigUtil();

    private static Properties properties = null;

    private ConfigUtil() {}

    public static ConfigUtil getInstance() {
        return instance;
    }

    /**
     * init
     * @param filename config.properties
     */
    public void init(String filename) {
        InputStream inputStream = null;
        try {
            String defaultConfig = SystemUtils.USER_DIR + File.separator + filename;
            File file = new File(defaultConfig);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            }
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("load config file failure");
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure");
                }
            }
        }
    }

    public static String getStringProperty(String key) {
        String value = "";
        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }
}
