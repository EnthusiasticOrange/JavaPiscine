package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class Car {
    private Boolean isLeftDriving;
    private String model;
    private Double fuel;
    private Long mileage;

    public Car() {
        this.isLeftDriving = true;
        this.model = "NewModel";
        this.fuel = 100.0;
        this.mileage = 0L;
    }

    public Car(Boolean isLeftDriving, String model, Double fuelLeft, Long mileage) {
        this.isLeftDriving = isLeftDriving;
        this.model = model;
        this.fuel = fuelLeft;
        this.mileage = mileage;
    }

    public double consumeFuel(double value) {
        this.fuel -= value;
        if (fuel < 0.0) {
            fuel = 0.0;
        }
        return fuel;
    }

    public long increaseMileage(long value) {
        this.mileage += value;
        if (mileage < 0L) {
            mileage = 0L;
        }
        return mileage;
    }

    public boolean isLeftDriving() {
        return isLeftDriving;
    }

    public void honk() {
        System.out.println("HONK");
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("isLeftDriving=" + isLeftDriving)
                .add("model='" + model + "'")
                .add("fuel=" + fuel)
                .add("mileage=" + mileage)
                .toString();
    }
}
