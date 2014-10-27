package logica;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CarritoMocksTest {

    private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    ICarrito carrito;
    ICarrito carrito2;

    Producto papa;
    Producto lechuga;

    //set up Mocks
    final Producto prodMock = context.mock(Producto.class, "ElMockProducto");
    final Cliente cliMock = context.mock(Cliente.class, "ElMockCliente");

    final ISistemaClientes sistCliMock = context.mock(ISistemaClientes.class, "ElMockSistCli");
    final ISistemaFacturacion sistFactMock = context.mock(ISistemaFacturacion.class, "ElMockSistFact");
        
    public CarritoMocksTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        }

    @After
    public void tearDown() {
        //carrito.vaciar();
    }

    @Test
    public void testPagarClienteRegistrado() throws NoExisteClienteException{
		//TODO    
    }


}