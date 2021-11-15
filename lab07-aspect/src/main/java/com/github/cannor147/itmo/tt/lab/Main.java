package com.github.cannor147.itmo.tt.lab;

import com.github.cannor147.itmo.software.lab07.profiler.Profiler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final Profiler profiler = Profiler.getInstance();
        profiler.setPackagePrefix("com.github.cannor147.itmo.tt.lab");
        try {
            Task3.main(args);
        } finally {
            profiler.printStats(System.out);
        }
    }
}
