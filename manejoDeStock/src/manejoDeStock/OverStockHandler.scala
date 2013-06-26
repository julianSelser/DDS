package manejoDeStock

class OverStockHandler {
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadSobrante: Int = componente.stockMax - Deposito.cuantos(componente)
        
        //Si nos interesa logear y sobre el maximo
        if(componente.importaLog && cantidadSobrante >= 0)
            //Genera el Log
            AuditLog.log("Se compraron " + cantidadSobrante + " " + componente.nombre +  " mas del stock maximo definido")
    }
}