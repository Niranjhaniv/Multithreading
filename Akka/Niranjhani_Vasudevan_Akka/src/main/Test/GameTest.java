package com;

import akka.actor.ActorRef;
import akka.actor.Props;
import junit.framework.TestCase;

public class GameTest extends TestCase {
	
 static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create("User");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        ActorRef dealerNode = system.actorOf(Props.create(Dealer.class));
        helloGreeter.tell(new WhoToGreet("Akka"), ActorRef.noSender());
        helloGreeter.tell(new Greet(), ActorRef.noSender());
        Greeting greeting = testProbe.expectMsgClass(Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }

}
