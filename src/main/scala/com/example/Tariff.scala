package com.example

case class Tariff(Name: String,
                  RatePower: Option[Double],
                  RateGas: Option[Double],
                  StandingCharge: Option[Double])
