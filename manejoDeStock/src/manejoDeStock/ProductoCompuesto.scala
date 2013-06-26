package manejoDeStock

class ProductoCompuesto(val stockMin:Int, val stockMax:Int, val ptoPedido:Int, val nombre:String, val importaLog:Boolean, val unidadesCriticas:Int) extends Producto {
	  var componentes: Set[Producto] = Set()
}

