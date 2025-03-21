package com.interview.credable.lms.domain;



import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionData {
    private String accountNumber;
    private BigDecimal alternativechanneltrnscrAmount;
    private Integer alternativechanneltrnscrNumber;
    private BigDecimal alternativechanneltrnsdebitAmount;
    private Integer alternativechanneltrnsdebitNumber;
    private Integer atmTransactionsNumber;
    private BigDecimal atmtransactionsAmount;
    private Integer bouncedChequesDebitNumber;
    private Integer bouncedchequescreditNumber;
    private BigDecimal bouncedchequetransactionscrAmount;
    private BigDecimal bouncedchequetransactionsdrAmount;
    private BigDecimal chequeDebitTransactionsAmount;
    private Integer chequeDebitTransactionsNumber;
    private Long createdAt;
    private Long createdDate;
    private BigDecimal credittransactionsAmount;
    private BigDecimal debitcardpostransactionsAmount;
    private Integer debitcardpostransactionsNumber;
    private Double fincominglocaltransactioncrAmount;
    private Long id;
    private Double incominginternationaltrncrAmount;
    private Integer incominginternationaltrncrNumber;
    private Integer incominglocaltransactioncrNumber;
    private Integer intrestAmount;
    private Long lastTransactionDate;
    private Object lastTransactionType;
    private Integer lastTransactionValue;
    private Double maxAtmTransactions;
    private Double maxMonthlyBebitTransactions;
    private Double maxalternativechanneltrnscr;
    private Double maxalternativechanneltrnsdebit;
    private Double maxbouncedchequetransactionscr;
    private Double maxchequedebittransactions;
    private Double maxdebitcardpostransactions;
    private Double maxincominginternationaltrncr;
    private Double maxincominglocaltransactioncr;
    private Double maxmobilemoneycredittrn;
    private Double maxmobilemoneydebittransaction;
    private Double maxmonthlycredittransactions;
    private Double maxoutgoinginttrndebit;
    private Double maxoutgoinglocaltrndebit;
    private Double maxoverthecounterwithdrawals;
    private Double minAtmTransactions;
    private Double minMonthlyDebitTransactions;
    private Double minalternativechanneltrnscr;
    private Double minalternativechanneltrnsdebit;
    private Double minbouncedchequetransactionscr;
    private Double minchequedebittransactions;
    private Double mindebitcardpostransactions;
    private Double minincominginternationaltrncr;
    private Double minincominglocaltransactioncr;
    private Double minmobilemoneycredittrn;
    private Double minmobilemoneydebittransaction;
    private Double minmonthlycredittransactions;
    private Double minoutgoinginttrndebit;
    private Double minoutgoinglocaltrndebit;
    private Double minoverthecounterwithdrawals;
    private BigDecimal mobilemoneycredittransactionAmount;
    private Integer mobilemoneycredittransactionNumber;
    private BigDecimal mobilemoneydebittransactionAmount;
    private Integer mobilemoneydebittransactionNumber;
    private BigDecimal monthlyBalance;
    private BigDecimal monthlydebittransactionsAmount;
    private BigDecimal outgoinginttransactiondebitAmount;
    private Integer outgoinginttrndebitNumber;
    private BigDecimal outgoinglocaltransactiondebitAmount;
    private Integer outgoinglocaltransactiondebitNumber;
    private BigDecimal overdraftLimit;
    private BigDecimal overthecounterwithdrawalsAmount;
    private Integer overthecounterwithdrawalsNumber;
    private Double transactionValue;
    private Long updatedAt;
}
