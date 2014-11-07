package logica;

import java.util.Iterator;
import java.util.LinkedList;
import javax.naming.CommunicationException;

class Carrito implements ICarrito {

    private LinkedList<Item> items;
    // el carrito se compone por una lista de <Producto, cantidad>

    Cliente cliente;
    String nombreCarrito;
    private ISistemaClientes sistCli;
    private ISistemaFacturacion sistFact;

    public Carrito(Cliente c) {
        cliente = c;
        sistCli = null;
        sistFact = null;
        try
        {
            nombreCarrito = c.getNombre();
        }
        catch(CommunicationException ce)
        {
            nombreCarrito = "General";
        }

		this.items = new LinkedList<Item> ();
	}

	public void agregarProducto(Producto p, int cant) {
            //después del testAgregarProductoExistente arreglo de esta forma
            Item i = this.obtenerItem(p.getNombre());
            if(i != null){
                i.setCantidad(cant + i.getCantidad());
            }
            else {
                items.add(new Item(p, cant));
            }
	}

        public void disminuirProducto(Producto p, int cant) {
            LinkedList<Item> itemsRetorno = new LinkedList<Item>();
            for(Item item : items){
                if (!item.getProducto().getNombre().equals(p.getNombre())) {
                    itemsRetorno.add(item);
                } else {
                    if (item.getCantidad() < cant) {
                        item.setCantidad(0);
                    } else {
                        int cantidadItem = item.getCantidad();
                        item.setCantidad(cantidadItem-cant);
                        itemsRetorno.add(item);
                    }
                }
            }
            items = itemsRetorno;
        }

        public void eliminarProductos(Producto p) {
            LinkedList<Item> itemsRetorno = new LinkedList<Item>();
            for(Item item : items){
                if (!item.getProducto().getNombre().equals(p.getNombre())) {
                    itemsRetorno.add(item);
                }
            }
            items = itemsRetorno;	
        }


        public double obtenerPrecioTotal() {
            int precioTotal = 0;
            for (Item item : items)
                precioTotal += item.getProducto().getPrecio() * item.getCantidad();

            return precioTotal;
        }

        public double obtenerSubtotal(String s) {
            double retorno = 0;
            for (Item item : items ) {
                if (item.getProducto().getNombre().equals(s)){
                    retorno = retorno + item.getProducto().getPrecio() * item.getCantidad();
                    //break; //se agrega para el caso de que solo hay un item de cada producto
                }
            }
            return retorno;
        }

        public int obtenerCantidad(String s) {
            Item i = obtenerItem(s);
            if (i != null){
                return i.getCantidad();
            }
            else {
                return 0;
            }
        }

        public void vaciar() {
            items.clear();
        }

        private Item obtenerItem(String s){
            Iterator<Item> iter = items.iterator();
            while(iter.hasNext()){
                Item actual = iter.next();
                if (actual.getProducto().getNombre().equals(s)){
                    return actual;
                }
            }
            return null;
        }

        public void pagar() {
            double total = (double) obtenerPrecioTotal();
            double descuento;

            try {
                descuento = sistCli.descuentoCliente(cliente);
            } catch (NoExisteClienteException e) {
                descuento = 0;
            }
            total = total * (1 - descuento);
            sistFact.facturar(total);
        }

        public void configurarSistemaClientes(ISistemaClientes s) {
            this.sistCli = s;
        }

        public void configurarSistemaFacturacion(ISistemaFacturacion s) {
            this.sistFact = s;
        }
	
}
