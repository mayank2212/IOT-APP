package co.aurasphere.bluepair.model;

public class SetOrderModel {
    private String setOrderName;
    private String setOrderTimer;

    public SetOrderModel() {
    }


    public SetOrderModel(String setOrderName, String setOrderTimer) {
        this.setOrderName = setOrderName;
        this.setOrderTimer = setOrderTimer;
    }

    public String getSetOrderName() {
        return setOrderName;
    }

    public String getSetOrderTimer() {
        return setOrderTimer;
    }

    public void setSetOrderTimer(String setOrderTimer) {
        this.setOrderTimer = setOrderTimer;
    }
}
