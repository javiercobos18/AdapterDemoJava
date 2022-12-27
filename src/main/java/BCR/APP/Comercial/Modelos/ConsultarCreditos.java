package BCR.APP.Comercial.Modelos;

import java.util.List;

public class ConsultarCreditos {
    public List<ListaCredito> ListaCreditos;
	public int CodigoRespuesta;
	public String DetalleRespuesta;
}

class ListaCredito {

	public String NumeroOperacion;
    public double MontoPagar;
    public String FechaConfeccion;
	public String TipoCredito;
    public String TipoPago;
    public String MonedaPago;
    public List<String> Firmantes;
    public int CantRegistros;
    public String NombreEstado;
	public boolean Listar;
	public boolean Firmar;
	public boolean Rechazar;
	public boolean QuitarFirma;
	public boolean VerDetalle;
	public int CodigoCliente;
	public int CodigoTransaccion;
	public String FechaEjecucion;
	public String NumeroRecibo;

}