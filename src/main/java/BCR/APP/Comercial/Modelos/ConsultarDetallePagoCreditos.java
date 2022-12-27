package BCR.APP.Comercial.Modelos;

import java.util.List;

public 


class ConsultarDetallePagoCreditos  {
    
	public DetallePagoCreditos Detalle;
    public int CodigoRespuesta;
    public String DetalleRespuesta;
}

class DetallePagoCreditos  {
    public String CuentaDebitar;
    public String DuenoCuenta;
    public String Motivo;
	public String NumeroOperacion;
    public String TipoPago;
	public String NumeroRecibo;
    public String NombreEstado;
    public String FechaEjecucion;
    public double TotalDebitar;
    public List<String> Firmantes;
    public String FechaConfeccion;
    public String MonedaPago;
    public int CantRegistros;
    public int CodigoCliente;
    public String UsuarioCreador;
	public String TipoCredito;

}
