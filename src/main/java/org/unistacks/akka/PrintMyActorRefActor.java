package org.unistacks.akka;

import akka.actor.*;

/**
 * @author Gyges Zean
 * @date 2017/12/9
 */
public class PrintMyActorRefActor extends AbstractActor{


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals("printit", p -> {
                    ActorRef secondRef = getContext().actorOf(Props.empty(), "second-actor");
                    System.out.println("Second: " + secondRef);
                }).build();
    }


}
