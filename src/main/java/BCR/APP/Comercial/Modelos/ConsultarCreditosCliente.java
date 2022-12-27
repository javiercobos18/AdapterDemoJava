package BCR.APP.Comercial.Modelos;

import java.util.List;

/**
 * @author ksanchoc
 *         Modelo de consulta de creditos de un cliente
 */

public class ConsultarCreditosCliente {
	public List<Credito> Creditos;
    public String InformacionAdicional;
    public String NumeroOperacion;
    public int CodigoRespuesta;
    public String DetalleRespuesta;
}

class Credito {
    public String NumeroOperacion;
    public Double SaldoActual;
    public String FechaConstitucion;
    public String SignoMoneda;
    public String Deudor;
    public String CuentaIban;
    public Double MontoOriginal;
    public Double FormaPago;
    public Double TasaInteres;
    public String Fluctuacion;
    public String FechaVencimiento;
    public String FechaProximoPago;
}

