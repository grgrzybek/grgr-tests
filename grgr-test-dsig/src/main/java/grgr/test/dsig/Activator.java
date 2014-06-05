package grgr.test.dsig;

import javax.xml.crypto.dsig.XMLSignatureFactory;

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
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        System.out.println(fac.toString());
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

}
