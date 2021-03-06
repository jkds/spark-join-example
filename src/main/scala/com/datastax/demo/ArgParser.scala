package com.datastax.demo

/**
 * Very simple trait that parses optional arguments from the command line
 * and creates a map of them which can be used in your application.
 * In your application pass arguments like foo=bar and then parsedArgs will contain
 * that argument with that value. Obviously in your application you can easily provide
 * defaults by using Maps' built-in Option capability e.g.
 *
 * val numberOfWorkers = parsedArgs.getOrElse("numberOfWorkers","2").toInt
 *
 * This is a very rudimentary implementation and all arguments are treated
 * as strings. There is scope to provide a more complex mechanism but this
 * does the job for now.
 *
 */
trait ArgParser {
  //Must be mixed with the App trait so that args are provided
  required : App =>
  //lazily evaluated because args won't be available immediately
  //uses an Option class to ensure things don't break if no arguments
  //are supplied
  lazy val parsedArgs = parse(Option(args))

  def parse(args : Option[scala.Array[String]]) : Map[String,String] = {
    args.getOrElse(Array[String]())
      .filter(_.split("=").length > 0).map { arg =>
      val argPair = arg.split("=")
      (argPair(0), argPair(1))
    }.toMap
  }

}
