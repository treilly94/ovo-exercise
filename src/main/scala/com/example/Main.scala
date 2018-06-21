package com.example

import com.example.TariffMatcher.{calculateCost, calculateUsage, readJson}

object Main extends App {
  // Read Tariffs from file
  val tariffs = readJson("./src/main/resources/copy_of_prices.json")
  // Throw exception if no args are provided
  if (args.isEmpty) throw new IllegalArgumentException("No args were passed in")
  // Match the keyword to the appropriate function
  args.head match {
    case "cost" => calculateCost(tariffs, args(1).toDouble, args(2).toDouble).foreach(println)
    case "usage" => println(calculateUsage(tariffs, args(1), args(2), args(3).toDouble))
    case _ => println("improper command used")
  }
}
