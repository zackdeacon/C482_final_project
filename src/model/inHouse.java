package model;

public class inHouse extends Part{

    private int machineId;

    public inHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    public int getMachineID(){
        return machineId;
    }

    public void setMachineID(int machineId){
        this.machineId = machineId;
    }

}
