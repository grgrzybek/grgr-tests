package grgr.test.dsig;

import java.security.Security;
import javax.xml.crypto.dsig.XMLSignatureFactory;

import org.apache.xml.security.Init;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p></p>
 *
 * @author Grzegorz Grzybek
 */
public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        Init.init();
        Security.insertProviderAt(new org.apache.jcp.xml.dsig.internal.dom.XMLDSigRI(), 0);
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", "ApacheXMLDSig");
        System.out.println(fac.toString());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
