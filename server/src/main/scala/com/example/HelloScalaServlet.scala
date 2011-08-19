package com.example

import javax.servlet.http.{HttpServletResponse, HttpServletRequest, HttpServlet}

class HelloScalaServlet extends HttpServlet{
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    println("-----------------")
    println(req.getPathInfo)
    println(req.getQueryString)
    println("-----------------")
    resp.getWriter.write(<html><body>
      <p>Path info: { req.getPathInfo }</p>
      <p>Query string: { req.getQueryString }</p>
    </body></html>.text)
  }
}