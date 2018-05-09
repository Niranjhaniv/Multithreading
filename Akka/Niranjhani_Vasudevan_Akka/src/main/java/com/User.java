package com;

import java.util.concurrent.TimeUnit;

import com.Messages.StartGame;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * Main class for your wordcount actor system.
 * 
 * @author niran
 *
 */
public class User {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Timeout timeout = new Timeout(10000, TimeUnit.SECONDS);
		ActorSystem system = ActorSystem.create("User");
		ActorRef dealerNode = system.actorOf(Props.create(Dealer.class));
		// ask method
		final Timeout timeout = new Timeout(Duration.create(1000, TimeUnit.SECONDS));

		final Future<Object> future = Patterns.ask(dealerNode, new StartGame(), timeout);

		Result result = (Result) Await.result(future, timeout.duration());

		System.out.println(result);

		system.shutdown();

	}

}