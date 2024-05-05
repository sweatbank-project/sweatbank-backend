package com.sweaterbank.leasing.car.model.enums;

public enum BusinessAreaType {
    NONE("none"),
    AGRICULTURE_FORESTRY_FISHERY("agricultureForestryFishery"),
    CONSTRUCTION("construction"),
    INDUSTRY("industry"),
    TRADE_PERSONAL_SERVICE("tradePersonalService"),
    TRANSPORT_HAULAGE_COMMUNICATIONS("transportHaulageCommunications"),
    FINANCIAL_INTERMEDIATION("financialIntermediation"),
    EDUCATION_CULTURE("educationCulture"),
    REAL_ESTATE_ACTIVITIES("realEstateActivities"),
    INFORMATION_TECHNOLOGIES("informationTechnologies"),
    LEGAL_ASSISTANCE_AUDITING("legalAssistanceAuditing"),
    HEALTH_SOCIAL_CARE("healthSocialCare"),
    PUBLIC_ADMINISTRATION_NATIONAL_DEFENCE("publicAdministrationNationalDefence");

    private final String businessAreaType;

    BusinessAreaType (String businessAreaType) { this.businessAreaType = businessAreaType; }

    @Override
    public String toString() { return businessAreaType; }

    public static BusinessAreaType fromString(String text) throws IllegalArgumentException {
        for (BusinessAreaType b : BusinessAreaType.values()) {
            if (b.businessAreaType.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
