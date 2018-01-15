package org.unistacks.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.rmi.ConnectException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JMX工具类,负责从JMX中获取数据
 *
 */
public class JMXHelper {
	private static final Logger logger = LoggerFactory.getLogger(JMXHelper.class);
	private String jmxUrl;

	private String userName;
	private String password;

	private JMXConnector connector;
	private MBeanServerConnection connection;

	private static Map<String,JMXHelper> cache = new ConcurrentHashMap<String, JMXHelper>();
	/**
	 * 获取JMX连接实例
	 * @param jmxUrl
	 * @return
	 * @throws IOException
	 */
	public static JMXHelper getInstance(String jmxUrl) throws IOException {
		if (!cache.containsKey(jmxUrl)) {
			cache.put(jmxUrl, new JMXHelper(jmxUrl));
		}
		return cache.get(jmxUrl);
	}

	public static JMXHelper getInstance(String host, String jmxPort) throws IOException {
		return getInstance(host + ":" + jmxPort);
	}

	/**
	 * 释放JMX连接
	 * @param jmxUrl
	 * @throws IOException
	 */
	synchronized private static void refreshInstance(String jmxUrl) throws IOException {
		logger.warn("jmx connection of " + jmxUrl + " is broken, create a new one.");
		JMXHelper helper = cache.remove(jmxUrl);
		if (helper != null) {
			helper.close();
		}
		getInstance(jmxUrl);
	}

	/**
	 * 
	 * @param jmxUrl
	 * @param userName
	 * @param password
	 * @throws IOException
	 */
	private JMXHelper(String jmxUrl, String userName, String password) throws IOException {
		this.jmxUrl = jmxUrl;
		this.userName = userName;
		this.password = password;
		initConnection();
	}

	private JMXHelper(String jmxUrl) throws IOException {
		this(jmxUrl, null, null);
	}

	public String getJmxUrl() {
		return jmxUrl;
	}

	/**
	 * 初始化连接
	 * @throws IOException
	 */
	private void initConnection() throws IOException {
		String jmxURL = "service:jmx:rmi:///jndi/rmi://" + jmxUrl + "/jmxrmi";
		JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] credentials = null;
		if (userName != null && password != null) {
			credentials = new String[] { userName, password };
		}
		map.put("jmx.remote.credentials", credentials);
		try {
			connector = JMXConnectorFactory.connect(serviceURL, map);
		} catch (IOException e) {
			throw new IOException("Connection refused to host: " + jmxUrl + ", please check the broker.", e);
		}
		connection = connector.getMBeanServerConnection();
	}

	public void close() {
		try {
			if (connector != null) {
				connector.close();
			}
		} catch (IOException e) {
			logger.error("Close JMXHelper fail!", e);
		}
	}
	
	/**
	 * 获取指定objectName的数据值
	 *
	 * @param objectName The object name of the MBean from which the
	 * attribute is to be retrieved.
	 * @param attr A String specifying the name of the attribute
	 * to be retrieved.
	 *
	 * @return
	 * @throws Exception
	 */
	public Object getAttribute(ObjectName objectName, String attr) throws Exception {
		try {
			return connection.getAttribute(objectName, attr);
		} catch (ConnectException e) {
			JMXHelper.refreshInstance(jmxUrl);
			throw e;
		}
	}
	/**
	 * 查询ObjectName
	 * @param objectName
	 * @param exp
	 * @return
	 * @throws IOException
	 */
	public Set<ObjectName> queryNames(ObjectName objectName, QueryExp exp) throws IOException {
		try {
			return connection.queryNames(objectName, exp);
		} catch (ConnectException e) {
			JMXHelper.refreshInstance(jmxUrl);
			throw e;
		}
	}


	public static void main(String[] args) {

		try {

			ObjectName objectName = new ObjectName("kafka.server:type=BrokerTopicMetrics,name=*,*");
			JMXHelper jmxHelper = JMXHelper.getInstance("192.168.1.217","9999");
			Iterator<ObjectName> o = jmxHelper.queryNames(objectName,null).iterator();
			while (o.hasNext()) {
				System.out.println("topic:" +o.next().getKeyProperty("topic")+ " " +o.next().getKeyProperty("name") + " "+jmxHelper.getAttribute(o.next(),"Count"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
