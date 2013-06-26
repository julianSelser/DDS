//Productos
abstract class Producto
{
  val stockMin  : Int
  val stockMax  : Int
  val ptoPedido : Int
  val nombre    : String
  
  val importaLog: Boolean
  val unidadesCriticas: Int
}
 
(val stockMin:Int, val stockMax:Int, val ptoPedido:Int, val nombre:String, val importaLog:Boolean, val unidadesCriticas:Int) extends Producto
{  
  var componentes: Set[Producto] = Set()
}

class MateriaPrima(val stockMin:Int, val stockMax:Int, val ptoPedido:Int, val nombre:String, val importaLog:Boolean, val unidadesCriticas:Int) extends Producto
 
//singleton Deposito 
object Deposito
{
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
 
abstract class Handler
{
    //Solo es un contrato
    def handle(componente: Producto, cantidad: Int)
}
 
 
//Debe generarse un pedido de compra si el producto bajó por sobre su stock mínimo. 
class UnderStockHandlerCompra extends Handler
{
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadFaltante: Int = componente.stockMin - Deposito.cuantos(componente)
        
        //Si faltan componentes
        if(cantidadFaltante <= 0 )
            //Informa al Departamento de Compras con cuantas faltan
            DepartamentoCompras.armarNotaCompras(componente, cantidadFaltante)
    }
}
 
 
//Algunos productos al bajar el stock mínimo generan un log de auditoría 
class UnderStockHandler extends Handler
{    
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadFaltante: Int = componente.stockMin - Deposito.cuantos(componente)
        
        //Si nos interesa logear y bajo el minimo
        if(componente.importaLog && cantidadFaltante <= 0)
            //Genera el Log
            AuditLog.log("Se compraron " + -cantidadFaltante + " " + componente.nombre +  " mas del stock minimo definido")
    }
}
 
 
//Para algunos productos nos interesa enviar un mail si un determinado producto tuvo una salida de más de x unidad
class CantidadHandler extends Handler
{    
    def handle(componente: Producto, cantidad: Int) ={        
        //Si nos interesa logear y por sobre la cantidad critica
        if(componente.importaLog && componente.unidadesCriticas <= cantidad)
            //Manda el mail
            AuditLog.mail("Se compraron " + cantidad + " " + componente.nombre +  ". Estas avisado!")
    }
}
 
 
//Algunos productos registran en el log de auditoría cuando dejan de estar por debajo del stoc
class noLongerUnderStockHandler extends Handler
{    
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadFaltante: Int = componente.stockMin - Deposito.cuantos(componente)
        
        //Si nos interesa logear si estaba por dejabo, pero ya no mas
        if(componente.importaLog && cantidadFaltante >= cantidad )
            //Genera el Log
            AuditLog.log("Se compraron " + -cantidadFaltante + " " + componente.nombre +  " mas del stock minimo definido")
    }
}
 
 
//Otros productos avisan cuando al ingresar mercadería exceden el stock máximo posible.
class OverStockHandler extends Handler
{    
    def handle(componente: Producto, cantidad: Int) ={
        val cantidadSobrante: Int = componente.stockMax - Deposito.cuantos(componente)
        
        //Si nos interesa logear y sobre el maximo
        if(componente.importaLog && cantidadSobrante >= 0)
            //Genera el Log
            AuditLog.log("Se compraron " + cantidadSobrante + " " + componente.nombre +  " mas del stock maximo definido")
    }
}
 
 
//Mocks
object AuditLog
{
    def log(s: String) = println(s)
    def mail(s: String) = println(s)
}
 
object DepartamentoCompras
{
    def armarNotaCompras(c: Producto, i: Int) = println("Comprar " + i + " " + c.nombre)
}