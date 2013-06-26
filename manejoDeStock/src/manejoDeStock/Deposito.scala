package manejoDeStock

object Deposito {
    var componentes:  Set[Producto] = Set()
    var handlersIn:   Set[Handler] = Set()
    var handlersOut:  Set[Handler] = Set()
    
    def removerUno(componente: Producto) = this.componentes -= componente
    def agregarUno(componente: Producto) = this.componentes += componente
    
    //Metodos auxiliares
    def cuantos(componente: Producto ): Int = this.componentes.count(_ == componente)
    
    
    //Metodos diseñados
    def sacar(componente: Producto, cantidad: Int) = {
        //Saca de la lista `cantidad` de veces
        (1 to cantidad).foreach( _ => this.removerUno(componente) )
        //Informa a los handelers corespondientes
        (this.handlersOut).foreach( (h) => h.handle(componente, cantidad) )
    }
    
    def meter(componente: Producto, cantidad: Int) = {
        //Saca de la lista `cantidad` de veces
        (1 to cantidad).foreach( _ => this.agregarUno(componente) )
        //Informa a los handelers corespondientes
        (this.handlersOut).foreach( (h) => h.handle(componente, cantidad) )
    }
}
