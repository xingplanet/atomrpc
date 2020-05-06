package com.xingplanet.atomrpc.discover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjin
 */
public abstract class AbstractDiscover implements DiscoverService{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected ConcurrentHashMap<String, Set<String>> serviceMap;


}
