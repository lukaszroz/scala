object HelloWorld extends App {
  private def filesHere = (new java.io.File(".")).listFiles

  def filesMatching(matcher: (String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName))
    yield file
  }

  def filesEnding(query: String) =
    filesMatching(_.endsWith(query))

  def filesContaining(query: String) =
    filesMatching(_.contains(query))

  def filesRegex(query: String) =
    filesMatching(_.matches(query))

  println(filesEnding("iml").toList)
  println(filesContaining("de").toList)
  println(filesRegex(".*i.e.*").toList)
}