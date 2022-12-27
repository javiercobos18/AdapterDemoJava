package BCR.APP.Comercial.Parametros;

import java.util.List;
/*
 
{
  "TokenSesion": "tPoMqCWQZnyePILho0KG0VdknzyamnzuMmC+MVQolQjeQ3Mhf9+YiA==",
  "Canal": "80",
  "Identificacion": "303760505",
  "CodigoUsuario": 146058,
  "Clientes": [
    {
      "CodigoCliente": 3,
      "IdentificacionRUC": "565656556"
    }
  ],
  "EsHistorico": false
}
 
 */
public class ConsultarPagosCreditosParametros {
    public List<Cliente> Clientes;
    public int CodigoUsuario;
    public String TokenSesion;
    public int Canal;
    public String Identificacion;
    public boolean EsHistorico;
}
