package com.example

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class HelloScalaServlet extends HttpServlet {

  import com.codahale.jerkson.Json._
  import Counter._

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    in.incrementAndGet
    start
    generate(Map("pathInfo" -> req.getPathInfo, "queryString" -> req.getQueryString, "hello" -> counter.incrementAndGet), resp.getOutputStream)
    val max = in.get - out.get
    var maxInC = maxIn.get
    while (maxInC < max){
      maxIn.compareAndSet(maxInC, max)
      maxInC = maxIn.get
    }
    out.incrementAndGet
  }
}

object Counter {

  import java.util.concurrent.atomic.AtomicLong
  import actors.threadpool.helpers.Utils
  import actors.threadpool.TimeUnit.NANOSECONDS
  import java.util.concurrent.atomic.AtomicBoolean
  import java.util.Locale

  val counter = new AtomicLong;
  val in = new AtomicLong;
  val out = new AtomicLong;
  val maxIn = new AtomicLong;
  val started = new AtomicBoolean(false);

  def start {
    if (started.compareAndSet(false, true)) {
      //        println("Started counter...")
      Locale.setDefault(Locale.US)
      val c = counter.get
      scala.concurrent.ops.spawn {
        val start = Utils.nanoTime
        Thread.sleep(2000)
        val count = counter.get - c
        val duration = NANOSECONDS.toMillis(Utils.nanoTime - start)
        println("Duration: %d, count: %d, throughput: %.2f rq/s, max: %d".format(duration, count, 1000.0 * count / duration, maxIn.get))
        maxIn.set(0)
        started.set(false)
      }
    }
  }

}