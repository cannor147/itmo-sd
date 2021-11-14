package com.github.cannor147.itmo.software.lab07;

import com.github.cannor147.itmo.software.lab07.temp.Temp;
import com.github.cannor147.itmo.software.lab07.profiler.Profiler;

public class Main {
    public static final String PACKAGE = "com.github.cannor147.itmo.software.lab07.temp";

    public static void main(String[] args) {
        final Profiler profiler = Profiler.getInstance();
        profiler.setPackagePrefix(PACKAGE);
        final Temp temp = new Temp();
        System.out.println(temp.calc());
        System.out.println(temp.calc());
        System.out.println(temp.calc());
        profiler.printStats(System.out);
    }
}
