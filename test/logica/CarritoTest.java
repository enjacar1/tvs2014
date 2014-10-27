package logica;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CarritoTest {

    ICarrito carrito;
    ICarrito carrito2;
    Producto papa;
    Producto lechuga;

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
        papa = new Producto(25, "papa");
        lechuga = new Producto(10, "lechuga");
    }

    @After
    public void tearDown() {
        carrito.vaciar();
    }

    @Test
    public void testAgregarProductoExistente(){

		carrito.agregarProducto(papa, 20);
		assertEquals(20, carrito.obtenerCantidad("papa"));

		carrito.agregarProducto(papa, 2);
		//deberían haber 22 papas
		assertEquals(22, carrito.obtenerCantidad("papa"));
		
	}

    @Test
    public void testProbarTotal(){

		carrito.agregarProducto(papa, 1);
		carrito.agregarProducto(lechuga, 1);
        
		assertEquals(25+10, (long)carrito.obtenerPrecioTotal());
	}
}