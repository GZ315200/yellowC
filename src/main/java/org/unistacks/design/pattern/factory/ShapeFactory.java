package org.unistacks.design.pattern.factory;


import org.unistacks.design.pattern.interfaces.Shape;

/**
 * Created by Gyges on 2017/10/23
 */
public class ShapeFactory {

     public static Object getClass(Class<? extends Shape> clazz) {
         Object obj = null;
         try {
             obj = Class.forName(clazz.getName()).newInstance();
         } catch (InstantiationException e) {
             e.printStackTrace();
         } catch (IllegalAccessException e) {
             e.printStackTrace();
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         return obj;
     }
}
