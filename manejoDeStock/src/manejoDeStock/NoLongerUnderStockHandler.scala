package manejoDeStock

class NoLongerUnderStockHandler {
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadFaltante: Int = componente.stockMin - Deposito.cuantos(componente)
        
        //Si nos interesa logear si estaba por dejabo, pero ya no mas
        if(componente.importaLog && cantidadFaltante >= cantidad )
            //Genera el Log
            AuditLog.log("Se compraron " + -cantidadFaltante + " " + componente.nombre +  " mas del stock minimo definido")
    }
}