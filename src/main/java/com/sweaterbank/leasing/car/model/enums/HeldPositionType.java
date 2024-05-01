package com.sweaterbank.leasing.car.model.enums;

public enum HeldPositionType {
    WORKER("worker"),
    SUPERIOR_SPECIALIST("supervisorSpecialist"),
    MIDDLE_MANAGER("middleManager"),
    EXECUTIVE("executive"),
    HIGHER("higher"),
    OWNER("owner"),
    STUDENT("student"),
    PENSIONER("pensioner"),
    AT_HOME("atHome"),
    UNEMPLOYED("unemployed"),
    PRIVATE_ENTREPRENEUR("privateEntrepreneur");

    private final String heldPositionType;

    HeldPositionType (String heldPositionType) { this.heldPositionType = heldPositionType; }

    @Override
    public String toString() { return heldPositionType; }

    public static HeldPositionType fromString(String text) throws IllegalArgumentException {
        for (HeldPositionType b : HeldPositionType.values()) {
            if (b.heldPositionType.equalsIgnoreCase(text)) {
                return b;
            }
        }

        throw new IllegalArgumentException("No enum with text " + text + " was found.");
    }
}
