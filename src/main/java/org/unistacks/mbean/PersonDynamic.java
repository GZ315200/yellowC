package org.unistacks.mbean;

import javax.management.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gyges on 2017/10/25
 */
public class PersonDynamic implements DynamicMBean {

    /**
     * Person object
     */
    private Person person;
    /**
     * describe the attributes info
     */
    private List<MBeanAttributeInfo> attributes = new ArrayList<MBeanAttributeInfo>();
    /**
     * describe the constructor info
     */
    private List<MBeanConstructorInfo> constructorInfos = new ArrayList<>();
    /**
     * describe the methods info
     */
    private List<MBeanOperationInfo> operationInfos = new ArrayList<>();

    /**
     * describe the notification info
     */
    private List<MBeanNotificationInfo> notificationInfos = new ArrayList<>();
    /**
     * MBeanInfo is used to manage above these description info
     */
    private MBeanInfo mBeanInfo;


    public PersonDynamic(Person person) {
        this.person = person;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * init method
     */
    private void init() {
        try {
            constructorInfos.add(new MBeanConstructorInfo("PersonDynamic(String,Integer)" + "constructor:",
                    this.person.getClass().getConstructors()[0]));
            attributes.add(new MBeanAttributeInfo("name", "java.lang.String", "the word or words that a person, thing, or place is known by", true, true, false));
            attributes.add(new MBeanAttributeInfo("age", "int", "the period of time someone has been alive or something has existed", true, false, false));

            operationInfos.add(new MBeanOperationInfo("sayHello methods : ", this.person.getClass()
                    .getMethod("sayHello", new Class[]{String.class})));

            this.mBeanInfo = new MBeanInfo(this.getClass().getName(), "PersonDynamic"
                    , attributes.toArray(new MBeanAttributeInfo[attributes.size()]),
                    constructorInfos.toArray(new MBeanConstructorInfo[constructorInfos.size()])
                    , operationInfos.toArray(new MBeanOperationInfo[operationInfos.size()])
                    , notificationInfos.toArray(new MBeanNotificationInfo[notificationInfos.size()]));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
        String name = "name";
        String age = "age";
        if (attribute.equals(name)) {
            return this.person.getName();
        } else if (attribute.equals(age)) {
            return this.person.getAge();
        }
        return null;
    }

    @Override
    public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {

//        get a list of property object

        if (attributes == null || attributes.length == 0) {
            return null;
        }

        try {
            AttributeList attributeList = new AttributeList();

            for (String attrName : attributes) {
                Object obj = this.getAttribute(attrName);
                Attribute attribute = new Attribute(attrName,obj);
                attributeList.add(attribute);
            }
            return attributeList;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        return null;
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
//        invoke the methods in  Person Class

        String actionNameMock = "sayHello";

        if (actionName.equals(actionNameMock)) {
            return this.person.sayHello(params[0].toString());
        }

        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        return mBeanInfo;
    }
}
