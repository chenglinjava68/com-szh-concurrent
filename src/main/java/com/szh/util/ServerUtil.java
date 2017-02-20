package com.szh.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerUtil.class);

    public ServerUtil() {
    }

    public static String getDefaultHostName() {
        InetAddress ia = null;

        try {
            ia = InetAddress.getLocalHost();
        } catch (UnknownHostException var2) {
            LOGGER.error("failed to get host, error is {}.", var2);
        }

        return ia == null?"some error..":ia.getHostName();
    }
}