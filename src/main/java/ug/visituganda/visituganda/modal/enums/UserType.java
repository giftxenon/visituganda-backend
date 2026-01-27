package ug.visituganda.visituganda.modal.enums;



public enum UserType {


    CUSTOMER("Customer logs in to explore visit Uganda use case" ),
    BUSINESS("Business logs in to market their businesses on visit");


    private final String description;

    UserType(String description) {
        this.description = description;
    }

    }
