import demo.Hello
import org.scalatest.FunSuite

/**
 * Created by eladw on 12/18/2014.
 */
class HelloTest extends FunSuite{
  test("sayHello method works correct"){
    val hello = new Hello
    assert(hello.sayHello("Scala") == "Hello, Scala!")
  }

}
