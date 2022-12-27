/*
 *    Licensed Materials - Property of IBM
 *    5725-I43 (C) Copyright IBM Corp. 2015, 2016. All Rights Reserved.
 *    US Government Users Restricted Rights - Use, duplication or
 *    disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

package BCR.APP.Comercial.Creditos;
 


import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import com.ibm.mfp.adapter.api.ConfigurationAPI;
import io.swagger.annotations.Api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.mfp.adapter.api.OAuthSecurity;

import BCR.APP.Comercial.Modelos.ConsultarCreditos;
import BCR.APP.Comercial.Modelos.ConsultarCreditosCliente;
import BCR.APP.Comercial.Modelos.ConsultarDetalleCreditos;
import BCR.APP.Comercial.Modelos.ConsultarDetallePagoCreditos;
import BCR.APP.Comercial.Modelos.Mensaje;
import BCR.APP.Comercial.Modelos.Pagos;
import BCR.APP.Comercial.Parametros.AutorizarPagosParametros;
import BCR.APP.Comercial.Parametros.ConsultarCreditosClienteParametros;
import BCR.APP.Comercial.Parametros.ConsultarDetalleCreditoParametros;
import BCR.APP.Comercial.Parametros.ConsultarDetallePagoCreditoParametros;
import BCR.APP.Comercial.Parametros.ConsultarPagosCreditosParametros;
import BCR.APP.Comercial.Parametros.QuitarAutorizacionParametros;
import BCR.APP.Comercial.Parametros.RechazarPagosParametros;
import Common.EnumAccionHTTP;
import Common.Invoke;
import Common.LoggerType;
import Common.MasterLogger;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(value = "Controlador de la consulta de creditos")
@Path("/Creditos")
public class CreditosResource {

	static final String NOMBREAPLICACION = "AdaptadorCreditos";
	static final String CONSULTAR_CREDITOS_CLIENTE = "ConsultarCreditosCliente";
	static final String CONSULTAR_DETALLE_CREDITOS = "ConsultarDetalleCreditos";
	static final String CONSULTAR_CREDITOS = "ConsultarCreditos";
	static final String CONSULTAR_DETALLE_PAGO_CREDITOS = "ConsultarDetallePagoCreditos";

	// Inject the MFP configuration API:
	@Context
	ConfigurationAPI configApi;

	@ApiOperation(value = "Consultar creditos cliente", notes = "Consulta los creditos de un cliente")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Validacion correctamente") })
	@POST
	@Path("/ConsultarCreditosCliente")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response ConsultarCreditosCliente(ConsultarCreditosClienteParametros param) {
		Gson gson = new Gson();
		String responseString = null;
		Mensaje<ConsultarCreditosCliente> result = null;
		String numTransferencia = String.valueOf(param.CodigoUsuario);
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, numTransferencia, "Entrando",
					LoggerType.PARAMETROS, configApi);

			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse httpResponse = null;
			result = new Mensaje<ConsultarCreditosCliente>(configApi);

			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "/api/Creditos/ConsultarCreditos";

					MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, numTransferencia,
							"Invocando API", LoggerType.INTERFACES, configApi);
					httpResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);

					if (httpResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(null).build();
					} else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(httpResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, numTransferencia,
								"Resultado invocacion API: " + responseString, LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString, new TypeToken<Mensaje<ConsultarCreditosCliente>>() {
						}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operación se realizó con éxito.','Datos':{'Creditos':[{'NumeroOperacion':'01-001-02-03-2401791','SaldoActual':0.0,'FechaConstitucion':'07-07-2020','SignoMoneda':'$','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'','MontoOriginal':71000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'15-10-2024','FechaProximoPago':''},{'NumeroOperacion':'01-260-01-02-5772709','SaldoActual':4767706.9,'FechaConstitucion':'22-06-2015','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR03015206126015772709','MontoOriginal':5000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'25-06-2035','FechaProximoPago':'23-10-2023'},{'NumeroOperacion':'01-260-01-02-5772722','SaldoActual':4996594.25,'FechaConstitucion':'25-06-2015','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR40015206126015772722','MontoOriginal':5000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'25-06-2035','FechaProximoPago':'25-08-2015'},{'NumeroOperacion':'01-270-01-02-0010293','SaldoActual':3184480.7,'FechaConstitucion':'01-04-1998','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR03015206127010010293','MontoOriginal':10000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'01-04-2006','FechaProximoPago':'02-11-2004'},{'NumeroOperacion':'01-360-01-02-5772846','SaldoActual':9784502.78,'FechaConstitucion':'25-08-2015','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR25015206136015772846','MontoOriginal':14000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'04-09-2030','FechaProximoPago':'04-11-2021'},{'NumeroOperacion':'01-377-01-02-5771565','SaldoActual':15872237.27,'FechaConstitucion':'07-08-2013','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR52015206137715771565','MontoOriginal':30000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'08-08-2020','FechaProximoPago':'08-10-2013'},{'NumeroOperacion':'01-377-01-02-5771660','SaldoActual':22457805.12,'FechaConstitucion':'26-02-2014','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR09015206137715771660','MontoOriginal':22872117.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'26-02-2034','FechaProximoPago':'26-06-2015'},{'NumeroOperacion':'01-377-01-02-5771660','SaldoActual':22457805.12,'FechaConstitucion':'26-02-2014','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR09015206137715771660','MontoOriginal':22872117.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'26-02-2034','FechaProximoPago':'26-06-2015'},{'NumeroOperacion':'01-377-01-02-8001731','SaldoActual':49981796.45,'FechaConstitucion':'23-08-2012','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR66015206137718001731','MontoOriginal':50000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'28-08-2032','FechaProximoPago':'18-09-2012'},{'NumeroOperacion':'01-651-01-02-5919752','SaldoActual':70134583200.0,'FechaConstitucion':'14-03-2014','SignoMoneda':'¢','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR87015206165115919752','MontoOriginal':80000000000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'15-05-2043','FechaProximoPago':'15-11-2021'},{'NumeroOperacion':'01-919-02-02-5772860','SaldoActual':0.0,'FechaConstitucion':'16-09-2015','SignoMoneda':'$','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'','MontoOriginal':54000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'16-09-2016','FechaProximoPago':'23-09-2015'},{'NumeroOperacion':'01-921-02-02-5776453','SaldoActual':1727.54,'FechaConstitucion':'11-02-2016','SignoMoneda':'$','Deudor':'PARRILLADA LA CABANA DE CASCAJAL SOCIEDAD ANO','CuentaIban':'CR55015206192125776453','MontoOriginal':20000.0,'FormaPago':0.0,'TasaInteres':0.0,'Fluctuacion':null,'FechaVencimiento':'11-02-2020','FechaProximoPago':'11-11-2018'}],'InformacionAdicional':null,'NumeroOperacion':null,'CodigoRespuesta':0,'DetalleRespuesta':null}}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<ConsultarCreditosCliente>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, numTransferencia,
						"Error - " + ex, LoggerType.ERROR, configApi);

				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, numTransferencia, "Finalizado.",
					LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS_CLIENTE, numTransferencia, "Error - " + ex,
					LoggerType.ERROR, configApi);

			return Response.serverError().status(Status.OK).entity(result).build();
		}
	}

	/**
	 * Consulta Detalle de los de creditos de un cliente
	 * 
	 * @param param
	 *              ConsultarDetalleParam
	 * @return Http Response
	 */
	@ApiOperation(value = "Consultar detalle de un credito", notes = "Consulta los detalles de los creditos de un cliente")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Validacion correctamente") })
	@POST
	@Path("/ConsultarDetalleCreditos")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response ConsultarDetalleCreditos(ConsultarDetalleCreditoParametros param) {
		Mensaje<ConsultarDetalleCreditos> result = null;
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS,
					String.valueOf(param.NumeroOperacion), "Entrando", LoggerType.PARAMETROS, configApi);

			Gson gson = new Gson();

			String responseString = null;
			result = new Mensaje<ConsultarDetalleCreditos>(configApi);
			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse resultadoResponse = null;
 
			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "/api/Creditos/ConsultarDetalleCredito";

					MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS,
							String.valueOf(param.NumeroOperacion), "Invocando API", LoggerType.INTERFACES, configApi);
					resultadoResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);
					if (resultadoResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS, param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(result).build();
					} else if (resultadoResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(resultadoResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS,
								String.valueOf(param.NumeroOperacion), "Resultado invocacion API: " + responseString,
								LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString, new TypeToken<Mensaje<ConsultarDetalleCreditos>>() {
						}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operación se realizó con éxito.','Datos':{'Detalle':{'NumeroOperacion':'01-205-01-02-5777471','SaldoActual':7500000.0,'FechaConstitucion':'04-09-2017','SignoMoneda':'¢','Deudor':'PARRILLADA Y CABANA DE CASCAJAL SA','CuentaIban':null,'MontoOriginal':7500000.0,'FormaPago':37500.0,'TasaInteres':0.0,'Fluctuacion':'F-FIJO','FechaVencimiento':'04-11-2017','FechaProximoPago':'04-11-2017','FechaPagoContado':null},'CodigoRespuesta':0,'DetalleRespuesta':null}}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<ConsultarDetalleCreditos>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS,
						String.valueOf(param.NumeroOperacion), "Error - " + ex, LoggerType.ERROR, configApi);
				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (resultadoResponse != null) {
					resultadoResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS,
					String.valueOf(param.NumeroOperacion), "Finalizado", LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_CREDITOS,
					String.valueOf(param.NumeroOperacion), "Error - " + ex, LoggerType.ERROR, configApi);
			return Response.serverError().status(Status.OK).entity(result).build();
		}
	}

	@ApiOperation(value = "Consultar pagos de creditos", notes = "Consulta los pagos de creditos de uno o más clientes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Validacion correctamente") })
	@POST
	@Path("/ConsultarPagosCreditos")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response ConsultarPagosCreditos(ConsultarPagosCreditosParametros param) {
		Gson gson = new Gson();
		String responseString = null;
		Mensaje<ConsultarCreditos> result = null;
		String numTransferencia = String.valueOf(param.CodigoUsuario);
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, numTransferencia, "Entrando",
					LoggerType.PARAMETROS, configApi);

			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse httpResponse = null;
			result = new Mensaje<ConsultarCreditos>(configApi);

			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "api/Creditos/ConsultarPagosCreditosPorAutorizar";

					MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, numTransferencia,
							"Invocando API", LoggerType.INTERFACES, configApi);
					httpResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);

					if (httpResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(null).build();
					} else if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(httpResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, numTransferencia,
								"Resultado invocacion API: " + responseString, LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString, new TypeToken<Mensaje<ConsultarCreditos>>() {
						}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operacion se realizo con exito.','Datos':{'ListaCreditos':[{'NumeroOperacion':'01-955-01-02-5882929','MontoPagar':207256.85,'FechaConfeccion':'2018-06-26T12:02:54','TipoCredito':'Pago Crédito Terceros','TipoPago':'Pago de cuota','MonedaPago':'Colones','Firmantes':[],'CantRegistros':1,'TipoTarjeta':null,'NombreEstado':'Confeccionada','Listar':true,'Firmar':true,'Rechazar':true,'QuitarFirma':true,'VerDetalle':true,'CodigoCliente':3,'CodigoTransaccion':26928}],'CodigoRespuesta':0,'DetalleRespuesta':null}}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<ConsultarCreditos>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, numTransferencia,
						"Error - " + ex, LoggerType.ERROR, configApi);

				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, numTransferencia, "Finalizado.",
					LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_CREDITOS, numTransferencia, "Error - " + ex,
					LoggerType.ERROR, configApi);

			return Response.serverError().status(Status.OK).entity(result).build();
		}
	}

	/**
	 * Consulta Detalle de los pagos de credito
	 * 
	 * @param param
	 *              ConsultarDetalleParam
	 * @return Http Response
	 */
	@ApiOperation(value = "Consultar detalle de un pago de credito", notes = "Consulta los detalles de los pagos de creditos realizadas por un usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Validacion correctamente") })
	@POST
	@Path("/ConsultarDetallePagoCreditos")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response ConsultarDetallePagoCreditos(ConsultarDetallePagoCreditoParametros param) {
		Mensaje<ConsultarDetallePagoCreditos> result = null;
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS,
					String.valueOf(param.CodigoTransaccion), "Entrando", LoggerType.PARAMETROS, configApi);

			Gson gson = new Gson();

			String responseString = null;
			result = new Mensaje<ConsultarDetallePagoCreditos>(configApi);
			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse resultadoResponse = null;

			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "/api/Creditos/ConsultarDetallePagosCreditos";

					MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS,
							String.valueOf(param.CodigoTransaccion), "Invocando API", LoggerType.INTERFACES, configApi);
					resultadoResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);
					if (resultadoResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS, param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(result).build();
					} else if (resultadoResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(resultadoResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS,
								String.valueOf(param.CodigoTransaccion), "Resultado invocacion API: " + responseString,
								LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString, new TypeToken<Mensaje<ConsultarDetallePagoCreditos>>() {
						}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operación se realizo con exito.','Datos':{'Detalle':{'CuentaDebitar':'CC-CR91015201001025969549','DuenoCuenta':'CASTRO QUESADA ALEXANDRA                          ','Motivo':'Pago Crédito Terceros','NumeroOperacion':'01-955-01-02-5882929','TipoPago':'Pago de cuota','NumeroRecibo':'26928','NombreEstado':'Confeccionada','FechaEjecucion':'2018-06-26T12:02:54','TotalDebitar':207256.85,'Firmantes':[],'FechaConfeccion':'2018-06-26T12:02:54','MonedaPago':'Colones','CantRegistros':1,'Listar':false,'Firmar':false,'Rechazar':false,'QuitarFirma':false,'VerDetalle':false,'CodigoCliente':3,'UsuarioCreador':'DANIEL ALFREDO CASTRO QUESADA'},'CodigoRespuesta':0,'DetalleRespuesta':null}}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<ConsultarDetallePagoCreditos>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS,
						String.valueOf(param.CodigoTransaccion), "Error - " + ex, LoggerType.ERROR, configApi);
				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (resultadoResponse != null) {
					resultadoResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS,
					String.valueOf(param.CodigoTransaccion), "Finalizado", LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, CONSULTAR_DETALLE_PAGO_CREDITOS,
					String.valueOf(param.CodigoTransaccion), "Error - " + ex, LoggerType.ERROR, configApi);
			return Response.serverError().status(Status.OK).entity(result).build();
		}
	}

	/**
	 * Tarjetas - Aprobar Pagos de tarjeta
	 * 
	 * @param param
	 *              Autorizar Pagos
	 * @return Http Response
	 */
	@ApiOperation(value = "Autorizar pagos de tarjetas", notes = "Permite autorizar pagos de tarjetas")
	@POST
	@Path("/AutorizarPagos")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response AutorizarPagos(AutorizarPagosParametros param) {
		Mensaje<Pagos> result = null;
		result = new Mensaje<Pagos>(configApi);
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.Identificacion, "Entrando",
					LoggerType.PARAMETROS, configApi);

			Gson gson = new Gson();

			String responseString = null;
			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse resultadoResponse = null;

			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "/api/Creditos/PagosCreditosAprobar";

					MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.Identificacion,
							"Invocando API", LoggerType.INTERFACES, configApi);

					resultadoResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);
					if (resultadoResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(result).build();
					} else if (resultadoResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(resultadoResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.Identificacion,
								"Resultado invocacion API: " + responseString, LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString,
								new TypeToken<Mensaje<Pagos>>() {
								}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operación se realizó con éxito.','Datos':null}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<Pagos>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.Identificacion,
						"Error - " + ex, LoggerType.ERROR, configApi);
				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (resultadoResponse != null) {
					resultadoResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.Identificacion,
					"Finalizado", LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, "AutorizarPagos", param.Identificacion,
					"Error - " + ex, LoggerType.ERROR, configApi);
			return Response.serverError().status(Status.OK).entity(result).build();

		}
	}

	/**
	 * Tarjetas - Rechazar Pagos de tarjetas
	 * 
	 * @param param
	 *              Rechazar Pagos
	 * @return Http Response
	 */
	@ApiOperation(value = "Rechazar pagos de tarjetas", notes = "Permite rechazar pagos de tarjetas")
	@POST
	@Path("/RechazarPagos")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response RechazarPagos(RechazarPagosParametros param) {
		Mensaje<Pagos> result = null;
		result = new Mensaje<Pagos>(configApi);
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.Identificacion, "Entrando",
					LoggerType.PARAMETROS, configApi);

			Gson gson = new Gson();

			String responseString = null;
			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse resultadoResponse = null;

			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "/api/Creditos/PagosCreditosRechazar";

					MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.Identificacion,
							"Invocando API", LoggerType.INTERFACES, configApi);

					resultadoResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);
					if (resultadoResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(result).build();
					} else if (resultadoResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(resultadoResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.Identificacion,
								"Resultado invocacion API: " + responseString, LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString,
								new TypeToken<Mensaje<Pagos>>() {
								}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operación se realizó con éxito.','Datos':null}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<Pagos>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.Identificacion,
						"Error - " + ex, LoggerType.ERROR, configApi);
				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (resultadoResponse != null) {
					resultadoResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.Identificacion,
					"Finalizado", LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, "RechazarPagos", param.Identificacion,
					"Error - " + ex, LoggerType.ERROR, configApi);
			return Response.serverError().status(Status.OK).entity(result).build();

		}
	}

	/**
	 * Tarjetas - Quitar autorizacion Pagos de tarjeta
	 * 
	 * @param param
	 *              Quitar Autorizacion Pagos
	 * @return Http Response
	 */
	@ApiOperation(value = "Quitar autorizacion pagos de tarjetas", notes = "Permite quitar autorizacion pagos de tarjetas")
	@POST
	@Path("/QuitarAutorizacionPagos")
	@Consumes("application/json")
	@Produces(MediaType.APPLICATION_JSON)
	@OAuthSecurity(scope = "BCR_COMERCIAL_SCOPE")
	// @OAuthSecurity(enabled = false)
	public Response QuitarAutorizacionPagos(QuitarAutorizacionParametros param) {
		Mensaje<Pagos> result = null;
		result = new Mensaje<Pagos>(configApi);
		try {
			MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.Identificacion, "Entrando",
					LoggerType.PARAMETROS, configApi);

			Gson gson = new Gson();

			String responseString = null;
			CloseableHttpClient client = HttpClientBuilder.create().build();
			CloseableHttpResponse resultadoResponse = null;

			try {

				if (Boolean.valueOf(configApi.getPropertyValue("ModoDebug")) == false) {
					String host = configApi.getPropertyValue("Host");
					int timeout = Integer.parseInt(configApi.getPropertyValue("TimeOut"));
					String path = "/api/Creditos/PagosCreditosQuitarFirma";

					MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.Identificacion,
							"Invocando API", LoggerType.INTERFACES, configApi);

					resultadoResponse = Invoke.Call(client, host, timeout, path, param, EnumAccionHTTP.HTTPPOST);
					if (resultadoResponse == null) {
						MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.TokenSesion,
								"Error - httpresponse = null", LoggerType.ERROR, configApi);

						return Response.serverError().status(Status.OK).entity(result).build();
					} else if (resultadoResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						responseString = new BasicResponseHandler().handleResponse(resultadoResponse);
						MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.Identificacion,
								"Resultado invocacion API: " + responseString, LoggerType.INTERFACES, configApi);
						result = gson.fromJson(responseString,
								new TypeToken<Mensaje<Pagos>>() {
								}.getType());
					}
				} else {
					String jsonText = "{'EsExitoso':true,'Codigo':0,'Descripcion':'La operación se realizó con éxito.','Datos':null}";
					result = gson.fromJson(jsonText, new TypeToken<Mensaje<Pagos>>() {
					}.getType());
				}

			} catch (Exception ex) {
				MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.Identificacion,
						"Error - " + ex, LoggerType.ERROR, configApi);
				return Response.serverError().status(Status.OK).entity(result).build();
			} finally {
				if (resultadoResponse != null) {
					resultadoResponse.close();
				}
				if (client != null) {
					client.close();
				}
			}

			MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.Identificacion,
					"Finalizado", LoggerType.PARAMETROS, configApi);
			return Response.ok().entity(result).build();
		} catch (Exception ex) {
			MasterLogger.WriteLogger(NOMBREAPLICACION, "QuitarAutorizacionPagos", param.Identificacion,
					"Error - " + ex, LoggerType.ERROR, configApi);
			return Response.serverError().status(Status.OK).entity(result).build();

		}
	}
}
