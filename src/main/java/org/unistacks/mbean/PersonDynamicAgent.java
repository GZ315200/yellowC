package org.unistacks.mbean;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created by Gyges on 2017/10/25
 */
public class PersonDynamicAgent {
    public static void main(String[] args) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
//            give the object a name in order to JVM can find this unique bean
//            formatter: [String]:name=String
            ObjectName personName = new ObjectName("person.dynamic:name=person");

            server.registerMBean(new PersonDynamic(new Person("zean",24)), personName);
            Thread.sleep(60 * 60 * 1000);
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

