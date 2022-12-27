package BCR.APP.Comercial.Modelos;


/*

*/
public class ConsultarDetalleCreditos {
    
    public Detalle Detalle;
    public int CodigoRespuesta;
    public String DetalleRespuesta;
}

class Detalle {

    public String NumeroOperacion;
    public Double SaldoActual;
    public String FechaConstitucion;
    public String SignoMoneda;
    public String Deudor;
    public String CuentaIban;
    public Double MontoOriginal;
    public Double FormaPago;
    public String TasaInteresFormateado;
    public String Fluctuacion;
    public String FechaVencimiento;
    public String FechaProximoPago;
    public String SiglasTasaInteres;
}