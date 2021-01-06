package com.example.lessonschedule;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class ClassInfo {
    static enum ColorSupported {
        Red, Pink, Purple, Deep_Purple, Indigo, Blue, Light_Blue, Cyan, Teal, Green, Light_Green, Lime, Amber, Orange
    }

    static final Map<ColorSupported, Integer> colorEnum2Color = new HashMap<ColorSupported, Integer>() {{
        put(ColorSupported.Red, Color.rgb(244, 67, 54));
        put(ColorSupported.Pink, Color.rgb(233, 30, 99));
        put(ColorSupported.Purple, Color.rgb(156, 39, 176));
        put(ColorSupported.Deep_Purple, Color.rgb(103, 58, 183));
        put(ColorSupported.Indigo, Color.rgb(63, 81, 181));
        put(ColorSupported.Blue, Color.rgb(33, 150, 243));
        put(ColorSupported.Light_Blue, Color.rgb(3, 169, 244));
        put(ColorSupported.Cyan, Color.rgb(0, 188, 212));
        put(ColorSupported.Teal, Color.rgb(0, 150, 136));
        put(ColorSupported.Green, Color.rgb(76, 175, 80));
        put(ColorSupported.Light_Green, Color.rgb(139, 195, 74));
        put(ColorSupported.Lime, Color.rgb(205, 220, 57));
        put(ColorSupported.Amber, Color.rgb(255, 193, 7));
        put(ColorSupported.Orange, Color.rgb(255, 152, 0));
    }};

    private String name;
    private String location;
    private int weekdayIndex;
    private int startWeek, endWeek;
    private int classStartIndex, classPeriod;
    private Integer bgColor;

    ClassInfo(String name, String location, int startWeek, int endWeek, int weekdayIndex, int classStartIndex, int classPeriod) {
        this.name = name;
        this.location = location;
        this.startWeek = startWeek;
        this.endWeek = endWeek;
        this.classStartIndex = classStartIndex;
        this.classPeriod = classPeriod;
        this.weekdayIndex = weekdayIndex;
        this.bgColor = colorEnum2Color.get(ColorSupported.values()[(int) (Math.random() * ColorSupported.values().length)]);
    }

    ClassInfo(String name, String location, int startWeek, int endWeek, int weekdayIndex, int classStartIndex, int classPeriod, ColorSupported bgColor) {
        this(name, location, startWeek, endWeek, weekdayIndex, classStartIndex, classPeriod);
        this.bgColor = colorEnum2Color.get(bgColor);
    }

    String toClassInfoString() {
        return name + "\n" + location + "\n" + startWeek + "~" + endWeek + "周";
    }

    Integer getBgColor() {
        return bgColor;
    }

    public void setBgColor(Integer bgColor) {
        this.bgColor = bgColor;
    }

    int getWeekdayIndex() {
        return weekdayIndex;
    }


    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    int getClassStartIndex() {
        return classStartIndex;
    }

    int getClassPeriod() {
        return classPeriod;
    }
}
