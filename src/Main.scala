object Main extends App {

  class Time {
    private[this] var h = 12
    private[this] var m = 0

    def hour: Int = h

    def hour_=(x: Int) {
      require(0 <= x && x < 24)
      h = x
    }

    def minute = m

    def minute_=(x: Int) {
      require(0 <= x && x < 60)
      m = x
    }
  }

  class Thermometer {
    var celsius: Float = _

    def fahrenheit = celsius * 9 / 5 + 32

    def fahrenheit_=(f: Float) {
      celsius = (f - 32) * 5 / 9
    }

    override def toString = fahrenheit + "F/" + celsius + "C"
  }

  class Food

  abstract class Animal {
    type SuitableFood <: Food

    def eat(food: SuitableFood)
  }

  class Grass extends Food

  class Cow extends Animal {
    type SuitableFood = Grass

    def eat(food: Grass) {
      println(food.getClass)
    }
  }

  new Cow().eat(new Grass)
}