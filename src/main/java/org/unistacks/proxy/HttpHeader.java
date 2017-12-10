package org.unistacks.proxy;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Gyges on 2017/10/11
 */
public class HttpHeader {

    private List<String> header = Lists.newArrayList();

    private String method;
    private String host;
    private String port;

    public static final int MAXLINESIZE = 4096;
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_CONNECT = "CONNECT";


    public HttpHeader() {
    }

    public static final HttpHeader readHeader(InputStream in) throws IOException {
        HttpHeader httpHeader = new HttpHeader();
        StringBuilder sb = new StringBuilder();

        char c = 0;
        while ((c = (char) in.read()) != '\n') {
            sb.append(c);
            if (sb.length() == MAXLINESIZE) {
                break;
            }
        }

        if (httpHeader.addHeaderMethod(sb.toString()) != null) {
            do {
                sb = new StringBuilder();
                while ((c = (char) in.read()) != '\n') {
                    sb.append(c);
                    if (sb.length() == MAXLINESIZE) {
                        break;
                    }
                }
                if (sb.length() > 1 && httpHeader.notTooLong()) {
                    httpHeader.addHeaderString(sb.substring(0,sb.length() - 1));
                } else {
                    break;
                }
            }while (true);
        }
        return httpHeader;
    }

    /**
     * this method
     * @param str
     */
    private void addHeaderString(String str) {
        str = str.replaceAll("\r", "");
        header.add(str);
        if (str.startsWith("Host")) {
            String[] hosts = str.split(":");
            host = hosts[1].trim();
            if (method.endsWith(METHOD_CONNECT)) {
                port = hosts.length == 3 ? hosts[2] : "433";
            } else if (method.endsWith(METHOD_GET) || method.endsWith(METHOD_POST)) {
                port = hosts.length == 3 ? hosts[2] : "80";
            }
        }
    }


    private String addHeaderMethod(String str) {
        str = str.replaceAll("\r","");
        header.add(str);
        if (str.startsWith(METHOD_CONNECT)) {
            method = METHOD_CONNECT;
        } else if (str.startsWith(METHOD_GET)) {
            method = METHOD_GET;
        } else if (str.startsWith(METHOD_POST)) {
            method = METHOD_POST;
        }
        return method;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String str : header) {
            sb.append(str).append("\r\n");
        }
        return sb.toString();
    }

    public boolean notTooLong() {
        return header.size() <= 16;
    }


    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
