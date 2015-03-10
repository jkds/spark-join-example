package com.datastax.demo

/**
 * Created by jameskavanagh on 09/03/2015.
 */
trait ArgParser {
  required : App =>

  lazy val parsedArgs = parse(Option(args))

  def parse(args : Option[scala.Array[scala.Predef.String]]) : Map[String,String] = {
    args.getOrElse(Array[String]())
      .filter(_.split("=").length > 0).map { arg =>
      val argPair = arg.split("=")
      (argPair(0), argPair(1))
    }.toMap
  }


}
