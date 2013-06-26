package manejoDeStock

class UnderStockHandlerCompra {
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadFaltante: Int = componente.stockMin - Deposito.cuantos(componente)
        
        //Si faltan componentes
        if(cantidadFaltante <= 0 )
            //Informa al Departamento de Compras con cuantas faltan
            DepartamentoCompras.armarNotaCompras(componente, cantidadFaltante)
    }
}