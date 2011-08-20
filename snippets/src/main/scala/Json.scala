import com.codahale.jerkson.Json._
import org.codehaus.jackson.JsonNode

object Json extends App{
  val list = generate(List(1, 2, 3))
  println(list)
  println(parse(list))
  println(parse[AnyRef](list).getClass)
  val map = generate(Map("one" -> 1, "two" -> "dos"))
  println(map)
  println(parse(map))
  println(parse[AnyRef](map).getClass)
  val json = """{"menu": {
   "id": "file",
   "value": "File",
   "popup": {
     "menuitem": [
       {"value": "New", "onclick": "CreateNewDoc()"},
       {"value": "Open", "onclick": "OpenDoc()"},
       {"value": "Close", "onclick": "CloseDoc()"}
     ]
   }
 }}"""
  val jsonParsed = parse[JsonNode](json)
  println(jsonParsed.getClass)
  println(jsonParsed)
}