package org.arpha.dto.order.enums;

public enum DeliveryType {
    PICK_UP("PickUp"), NOVA_POSHTA_DEPARTMENT("WarehouseWarehouse"),
    NOVA_POSHTA_COURIER("WarehouseDoors"), NOVA_POSHTA_POSHTMAT("WarehouseWarehouse");

    DeliveryType(String serviceType) {
        this.serviceType = serviceType;
    }

    private String serviceType;

    public String getServiceType() {
        return serviceType;
    }
}
