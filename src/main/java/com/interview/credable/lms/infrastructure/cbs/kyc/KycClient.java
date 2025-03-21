//package com.interview.credable.lms.infrastructure.cbs.kyc;
//
//import io.credable.cbs.customer.ObjectFactory;
//
//public class KycClient extends WebServiceGatewaySupport {
//
//    public CustomerServiceResponse getCustomerDetails(String customerNumber) {
//        CustomerService request = new CustomerService();
//        request.setCustomerNumber(customerNumber);
//
//        JAXBElement<CustomerService> factory = (new ObjectFactory()).createCustomer(request);
//
//        JAXBElement<CustomerServiceResponse> response = (JAXBElement<CustomerServiceResponse>) getWebServiceTemplate()
//                .marshalSendAndReceive("https://kycapitest.credable.io/service/customerWsdl.wsdl",
//                        factory,
//                        new SoapActionCallback("http://service.customer.credit.partner.com/CustomerService")); // You might need to adjust the SoapActionCallback
//
//
//        return response.getValue();
//    }
//}