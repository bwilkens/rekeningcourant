package nl.quintor.rc.web.soap.klant;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class KlantWebServicePublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:9999/ws/klant", new KlantWebServiceImpl());
    }

}