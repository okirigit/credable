//package com.interview.credable.lms.config;
//
//import com.interview.credable.lms.infrastructure.cbs.kyc.KycClient;
//import com.interview.credable.lms.infrastructure.cbs.transaction.TransactionDataClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//
//@Configuration
//public class SoapConfig {
//
//    @Bean
//    public Jaxb2Marshaller kycMarshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        // This context path must match the package where JAXB classes are generated for KYC
//        marshaller.setContextPath("com.credable.lms.infrastructure.cbs.kyc");
//        return marshaller;
//    }
//
//    @Bean
//    public KycClient kycClient(Jaxb2Marshaller kycMarshaller) {
//        KycClient client = new KycClient();
//        client.setDefaultUri("https://kycapitest.credable.io/service/customerWsdl.wsdl"); // [cite: 35]
//        client.setMarshaller(kycMarshaller);
//        client.setUnmarshaller(kycMarshaller);
//        return client;
//    }
//
//    @Bean
//    public Jaxb2Marshaller transactionMarshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        // This context path must match the package where JAXB classes are generated for Transaction
//        marshaller.setContextPath("com.credable.lms.infrastructure.cbs.transaction");
//        return marshaller;
//    }
//
//    @Bean
//    public TransactionDataClient transactionDataClient(Jaxb2Marshaller transactionMarshaller) {
//        TransactionDataClient client = new TransactionDataClient();
//        client.setDefaultUri("https://trxapitest.credable.io/service/transactionWsdl.wsdl"); // [cite: 35]
//        client.setMarshaller(transactionMarshaller);
//        client.setUnmarshaller(transactionMarshaller);
//        return client;
//    }
//}