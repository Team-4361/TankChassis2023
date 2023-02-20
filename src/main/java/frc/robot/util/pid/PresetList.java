package frc.robot.util.pid;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PresetList extends ArrayList<Double> {
    private int index = 0;
    private String name = "";
    private boolean dashAdded = false;

    public PresetList(Double... elements) {
        this.addAll(Arrays.asList(elements));
    }

    public double getCurrentPreset() {
        return getPreset(index);
    }

    public double getPreset(int idx) {
        return get(MathUtil.clamp(idx, 0, size()-1));
    }

    public PresetList setCurrentPreset(int index) {
        this.index = index;
        return this;
    }

    public PresetList nextPreset() {
        if (index+1 <= size()-1) {
            index++;
        }
        return this;
    }

    public PresetList prevPreset() {
        if (index-1 >= 0) {
            index--;
        }
        return this;
    }

    private String getDashboardName(int element) {
        return name + " | Preset " + element;
    }

    public void updateDashboard(String name) {
        assert !Objects.equals(name, "");
        this.name = name;
        if (!dashAdded) {
            // Add the initial values to the SmartDashboard.
            for (int i=0; i<size(); i++) {
                SmartDashboard.putNumber(getDashboardName(i), getPreset(i));
            }
            dashAdded = true;
        } else {
            // Pull the values from the SmartDashboard.
            for (int i=0; i<size(); i++) {
                set(i, SmartDashboard.getNumber(getDashboardName(i), get(i)));
            }
        }
    }
}
