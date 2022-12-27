package BCR.APP.Comercial.Modelos;

import com.ibm.mfp.adapter.api.ConfigurationAPI;

/**
 * Clase Respuesta Generica
 * 
 * @author crodriguez
 *
 * @param <T>
 *            Objeto Generico
 */
public class Mensaje<T> {
	
	public Mensaje(ConfigurationAPI config) {
		this.Codigo = -1;
		this.Descripcion = config.getPropertyValue("ErrorGeneralMessage");
		this.EsExitoso = false;
		this.Datos = null;
	}
	
	

	/// Codigo de respuesta
	public int Codigo;

	/// Mensaje de respuesta
	public String Descripcion;

	/// Objeto Respuesta
	public T Datos;

	/// Estado de la solicitud
	public boolean EsExitoso;

}
