package manejoDeStock

abstract class Handler
{
    //Solo es un contrato
    def handle(componente: Producto, cantidad: Int)
}