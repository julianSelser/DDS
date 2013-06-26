package manejoDeStock

class CantidadHandler {
	def handle(componente: Producto, cantidad: Int) ={        
        //Si nos interesa logear y por sobre la cantidad critica
        if(componente.importaLog && componente.unidadesCriticas <= cantidad)
            //Manda el mail
            AuditLog.mail("Se compraron " + cantidad + " " + componente.nombre +  ". Estas avisado!")
    }
}