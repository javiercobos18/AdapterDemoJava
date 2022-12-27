/*
 *    Licensed Materials - Property of IBM
 *    5725-I43 (C) Copyright IBM Corp. 2015, 2016. All Rights Reserved.
 *    US Government Users Restricted Rights - Use, duplication or
 *    disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
*/

package BCR.APP.Comercial.Creditos;

import com.ibm.mfp.adapter.api.MFPJAXRSApplication;

public class CreditosApplication extends MFPJAXRSApplication {

	protected void init() throws Exception {
		System.out.println("Adapter initialized!");
	}

	protected void destroy() throws Exception {
		System.out.println("Adapter destroyed!");
	}

	protected String getPackageToScan() {
		// The package of this class will be scanned (recursively) to find JAX-RS
		// resources.
		// It is also possible to override "getPackagesToScan" method in order to return
		// more than one package for scanning
		return getClass().getPackage().getName();
	}
}
