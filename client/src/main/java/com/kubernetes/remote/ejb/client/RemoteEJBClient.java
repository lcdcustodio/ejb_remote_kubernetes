package com.kubernetes.remote.ejb.client;

import com.kubernetes.remote.ejb.server.stateless.RemoteHello;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Hashtable;

public class RemoteEJBClient {

    private static final String HTTP = "http";

    public static void main(String[] args) throws Exception {
        // Invoke a stateless bean
         invokeBean();
    }


    private static void invokeBean() throws NamingException {
        // Let's lookup the remote stateful counter
        final RemoteHello statefulRemoteHello = lookupRemoteStatefulCounter();
        System.out.println("Obtained a remote stateful counter for invocation");
        // invoke on the remote counter bean
        System.out.println(statefulRemoteHello.sayHello("EJB Remote"));
    }


    private static RemoteHello lookupRemoteStatefulCounter() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<>();
        //jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        if(Boolean.getBoolean(HTTP)) {
            //use HTTP based invocation. Each invocation will be a HTTP request
            //jndiProperties.put(Context.PROVIDER_URL,"http://localhost:8080/wildfly-services");
            //jndiProperties.put(Context.PROVIDER_URL,"http://172.17.0.2:8080/wildfly-services");
            jndiProperties.put(Context.PROVIDER_URL,"remote+http://svc-ejb-demo-k8s:8080/wildfly-services");
        } else {
            //use HTTP upgrade, an initial upgrade requests is sent to upgrade to the remoting protocol
            //jndiProperties.put(Context.PROVIDER_URL,"remote+http://localhost:8080");
            //jndiProperties.put(Context.PROVIDER_URL,"remote+http://172.17.0.2:8080");
            jndiProperties.put(Context.PROVIDER_URL,"remote+http://svc-ejb-demo-k8s:8080");
        }
        final Context context = new InitialContext(jndiProperties);


        // let's do the lookup
        return (RemoteHello) context.lookup("ejb:/ejb-remote-server-side-1//HelloBean!"
            + RemoteHello.class.getName());

    }
}
