package com.xingplanet.atomrpc.config;

/**
 * @author wangjin
 */
public class RegisterConfig extends AbstractConfig {

    /**
     * 地址
     */
    private String address;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 超时时长
     */
    private Integer timeout;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "RegisterConfig{" +
                "address='" + address + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", port=" + port +
                ", timeout=" + timeout +
                '}';
    }
}
