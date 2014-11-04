package logica;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CarritoTest {
    
    ICarrito carrito;
    
    Producto papa;
    Producto lechuga;
    Producto tomate;
    Producto limon;
    Producto naranja;
    Producto frutilla;
    Producto manzana;

    public CarritoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        carrito = FactoryCarrito.getCarrito();
        
        //id vacio
        limon = new Producto(40, "");     
        //precio gratis
        tomate = new Producto(0, "tomate");
        //precio negativo
        lechuga = new Producto(-10, "lechuga");
        //casos "normales"
        papa = new Producto(25, "papa");
        naranja = new Producto(25, "naranja");
        manzana = new Producto(60, "manzana");
        frutilla = new Producto(65, "frutilla");
       
    }

    @After
    public void tearDown() {
        carrito.vaciar();
    }

   

/** 3 CASOS QUE PRUEBAN LAS 3 OPERS CADA UNO:
 * CASO DE PRODUCTO CON VALORES ESPECIALES, 
 * CASO DE CANTIDAD CON VALORES ESPECIALES 
 * CASO NORMAL **/
    
    /**
     * Prueba caso de que el producto tenga:
     * nombre ""
     * precio 0 
     * precio negativo
     */
    @Test
    public void testCasosEspecialesProducto(){
        
        carrito.agregarProducto(limon, 50);
        carrito.agregarProducto(tomate, 20);
        carrito.agregarProducto(lechuga, 15);

        assertEquals(50, carrito.obtenerCantidad(""));
        assertEquals(20, carrito.obtenerCantidad("tomate"));
        assertEquals(15, carrito.obtenerCantidad("lechuga"));
        
        int total = 50*40+0-10*15;
        assertEquals(total, (long)carrito.obtenerPrecioTotal());
    }

     /**
     * Prueba caso de que la cantidad sea:
     * 0 o negativa
     */
    @Test
    public void testCasosCantidadesEspeciales(){
        
        carrito.agregarProducto(limon, 0);
        carrito.agregarProducto(tomate, 0);
        carrito.agregarProducto(lechuga, 0);
        carrito.agregarProducto(papa, 0);

        assertEquals(0, carrito.obtenerCantidad(""));
        assertEquals(0, carrito.obtenerCantidad("tomate"));
        assertEquals(0, carrito.obtenerCantidad("lechuga"));
        assertEquals(0, carrito.obtenerCantidad("papa"));
        
        int total = 0;
        assertEquals(total, (long)carrito.obtenerPrecioTotal());
        
        carrito.vaciar();
        
        carrito.agregarProducto(limon, -1);
        carrito.agregarProducto(tomate, -1);
        carrito.agregarProducto(lechuga, -1);
        carrito.agregarProducto(papa, -1);
        
        assertEquals(-1, carrito.obtenerCantidad(""));
        assertEquals(-1, carrito.obtenerCantidad("tomate"));
        assertEquals(-1, carrito.obtenerCantidad("lechuga"));
        assertEquals(-1, carrito.obtenerCantidad("papa"));
        
      
        total = -40+10-25;
        assertEquals(total, (long)carrito.obtenerPrecioTotal());
        
    }
    
    /**
     * Prueba caso de que el producto tenga:
     * nombre distinto de ""
     * cantidades y precios positivos
     * que hayan varios items de un mismo producto
     */
    @Test
    public void testCasoNormal(){
        
        carrito.agregarProducto(papa, 2);
        carrito.agregarProducto(naranja, 8);
        carrito.agregarProducto(manzana, 17);
        carrito.agregarProducto(frutilla, 80);

        assertEquals(2, carrito.obtenerCantidad("papa"));
        assertEquals(8, carrito.obtenerCantidad("naranja"));
        assertEquals(17, carrito.obtenerCantidad("manzana"));
        assertEquals(80, carrito.obtenerCantidad("frutilla"));
        
        int total = 2*25+8*25+17*60+80*65;
        assertEquals(total, (long)carrito.obtenerPrecioTotal());
        
        carrito.agregarProducto(papa, 3);
        carrito.agregarProducto(naranja, 9);
        carrito.agregarProducto(manzana, 18);
        carrito.agregarProducto(frutilla, 81);
        
        assertEquals(2+3, carrito.obtenerCantidad("papa"));
        assertEquals(8+9, carrito.obtenerCantidad("naranja"));
        assertEquals(17+18, carrito.obtenerCantidad("manzana"));
        assertEquals(80+81, carrito.obtenerCantidad("frutilla"));
        
        total = (2+3)*25+(8+9)*25+(17+18)*60+(80+81)*65;
        assertEquals(total, (long)carrito.obtenerPrecioTotal());
     
    }
}