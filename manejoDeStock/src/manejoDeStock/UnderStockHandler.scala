package manejoDeStock

class UnderStockHandler {
	def handle(componente: Producto, cantidad: Int) ={
        val cantidadFaltante: Int = componente.stockMin - Deposito.cuantos(componente)
        
        //Si nos interesa logear y bajo el minimo
        if(componente.importaLog && cantidadFaltante <= 0)
            //Genera el Log
            AuditLog.log("Se compraron " + -cantidadFaltante + " " + componente.nombre +  " mas del stock minimo definido")
    }
}