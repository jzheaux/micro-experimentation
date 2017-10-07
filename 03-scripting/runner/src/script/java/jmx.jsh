import javax.management.remote.*;
import javax.management.*;
import javax.management.openmbean.*;

JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:18080/jmxrmi");
JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

MBeanServerConnection mbsc =  jmxc.getMBeanServerConnection();

Object getValue(String mBeanName, String attributeName, String subAttributeName) {
  try {
    ObjectName o = new ObjectName(mBeanName);
    Object response = mbsc.getAttribute(o, attributeName);
    if ( response instanceof CompositeDataSupport ) {
      return ((CompositeDataSupport)response).get(subAttributeName);
    } else if ( response instanceof SimpleType ) {
      return ((SimpleType)response).readResolve();
    }
    return response;
  } catch ( Exception e ) {
    throw new RuntimeException(e);
  }
}
