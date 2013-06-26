package manejoDeStock

abstract class Producto {
  val stockMin  : Int
  val stockMax  : Int
  val ptoPedido : Int
  val nombre    : String
  
  val importaLog: Boolean
  val unidadesCriticas: Int
}