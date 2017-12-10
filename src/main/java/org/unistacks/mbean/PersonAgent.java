package org.unistacks.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Gyges on 2017/10/18
 */
public class PersonAgent {

    public static void main(String[] args) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            ObjectName personName = new ObjectName("jmxBean:name=xiaoming");
            server.registerMBean(new Person("xiaoming",27),personName);
            Thread.sleep(60*60*1000);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
