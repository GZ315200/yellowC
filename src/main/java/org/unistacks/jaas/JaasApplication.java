package org.unistacks.jaas;

import org.apache.kafka.common.security.JaasUtils;
import java.io.IOException;

/**
 * Created by Gyges on 2017/10/19
 */
public class JaasApplication {

    private static final String USERNAME_CONFIG = "username";
    private static final String PASSWORD_CONFIG = "password";
//
//    public static void main(String[] args) throws IOException {
//        JaasUtils.jaasConfig(LoginType.CLIENT.contextName(),USERNAME_CONFIG);
//        JaasUtils.jaasConfig(LoginType.CLIENT.contextName(),PASSWORD_CONFIG);
//
//    }
}
