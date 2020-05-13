package com.xingplanet.atomrpc.discover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangjin
 */
public abstract class AbstractDiscover implements DiscoverService{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String[] packageNameArray;

    public AbstractDiscover(String[] packageNameArray) {
        this.packageNameArray = packageNameArray;
    }

    @Override
    public String[] getPackageNameArray() {
        return packageNameArray;
    }
}
