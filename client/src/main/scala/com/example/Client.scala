package com.example

import actors.threadpool.helpers.Utils
import actors.threadpool.TimeUnit

object Client extends App {

  import org.eclipse.jetty.client.ContentExchange
  import org.eclipse.jetty.client.HttpClient
  import org.eclipse.jetty.util.thread.QueuedThreadPool

  val client = new HttpClient
  client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL)
  client.setMaxConnectionsPerAddress(200); // max 200 concurrent connections to every address
  client.setThreadPool(new QueuedThreadPool(250)); // max 250 threads
  client.setTimeout(30000); // 30 seconds timeout; if no server reply, the request expires
  client.start();

  val exchanges: Seq[ContentExchange] = for {
    i <- 1 to 10000
  } yield new ContentExchange

  (exchanges zipWithIndex) foreach {
    case (e, i) => e.setURL("http://localhost:8080/scala/hello?World%s!".format(i))
  }
  
  val count = exchanges.size
  var line = ""
  while (line != "OK") {

    exchanges foreach (_.reset())
    val start = Utils.nanoTime
    exchanges foreach (client.send(_))
    exchanges foreach (_.waitForDone())
    val duration = TimeUnit.NANOSECONDS.toMillis(Utils.nanoTime - start)
    println("Duration: %d, count: %d, throughput: %.2f rq/s".format(duration, count, 1000.0 * count / duration))
//    exchanges foreach (e => println(e.getResponseContent))

    line = readLine("Type in OK to end...")
  }
  client.stop()
}