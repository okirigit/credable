package com.interview.credable.lms.config;

import com.interview.credable.lms.domain.Customer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class FreeMarkerSoapProcessor {

    private final Configuration freemarkerConfig;

    public FreeMarkerSoapProcessor() {
        // Configure FreeMarker
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freemarkerConfig.setClassForTemplateLoading(FreeMarkerSoapProcessor.class, "/templates"); // Template location
        freemarkerConfig.setDefaultEncoding("UTF-8");
    }

    public Customer processKycResponse(String xmlResponse) throws XMLStreamException, IOException, TemplateException {
        // 1. Parse XML (StAX example)
        Map<String, String> data = parseXmlToMap(xmlResponse);

        // 2. Load FreeMarker Template
        Template template = freemarkerConfig.getTemplate("kyc_mapping.ftl");

        // 3. Process Template
        StringWriter writer = new StringWriter();
        template.process(data, writer);
        String output = writer.toString();

        // 4. Extract Data and Populate Java Object
        Customer customer = new Customer();
        //  This is where you'd parse the 'output' (which might be a formatted string
        //  or even a partial JSON representation) and use it to set the
        //  customer's properties.  This part is highly dependent on your template's output.
        customer.setCustomerNumber(data.get("customerNumber"));
        customer.setFirstName(data.get("firstName"));
        // ... etc.

        return customer;
    }

    private Map<String, String> parseXmlToMap(String xml) throws XMLStreamException {
        Map<String, String> data = new HashMap<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(new StringReader(xml));

        String currentElement = null;

        while (reader.hasNext()) {
            int event = reader.next();
            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    currentElement = reader.getLocalName();
                    break;
                case XMLStreamConstants.CHARACTERS:
                    if (currentElement != null) {
                        data.put(currentElement, reader.getText().trim());
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    currentElement = null;
                    break;
            }
        }
        return data;
    }

    public static void customerXml(String args) throws XMLStreamException, IOException, TemplateException {
        // Example Usage
        String xmlResponse = "<CustomerDetails><customerNumber>12345</customerNumber><firstName>John</firstName><lastName>Doe</lastName></CustomerDetails>"; // Replace with actual SOAP response

        FreeMarkerSoapProcessor processor = new FreeMarkerSoapProcessor();
        Customer customer = processor.processKycResponse(xmlResponse);
        log.info("Customer Number: {} ",customer.getCustomerNumber());
        log.info("First Name: " ,customer.getFirstName());

        // ...
    }
}
