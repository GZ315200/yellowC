package org.unistacks.main;

import org.inferred.freebuilder.FreeBuilder;

/**
 * Created by Gyges on 2017/10/18
 */

@FreeBuilder
public interface Person {

    String name();

    int age();

    class Builder extends org.unistacks.main.Person_Builder { }

}

