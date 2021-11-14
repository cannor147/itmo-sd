package com.github.cannor147.itmo.software.lab07.profiler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import one.util.streamex.EntryStream;
import org.aspectj.lang.Signature;

import java.io.PrintStream;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LongSummaryStatistics;
import java.util.Map;

@SuppressWarnings("unused")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Profiler {
    private static final Profiler INSTANCE = new Profiler();

    private String packagePrefix = null;

    public static Profiler getInstance() {
        return INSTANCE;
    }

    private final Map<String, DoubleSummaryStatistics> pathToStatisticsMap = new HashMap<>();

    synchronized void register(Signature signature, long executionTime) {
        final String path = signature.getDeclaringTypeName() + "." + signature.getName();
        pathToStatisticsMap.computeIfAbsent(path, x -> new DoubleSummaryStatistics())
                .accept(0.000_001 * executionTime);
    }

    public synchronized void clear() {
        pathToStatisticsMap.clear();
    }

    public void stop() {
        packagePrefix = null;
    }

    public synchronized void printStats(PrintStream printStream) {
        EntryStream.of(pathToStatisticsMap)
                .mapValues(DoubleSummaryStatistics::toString)
                .mapKeyValue((name, stats) -> stats.replace(DoubleSummaryStatistics.class.getSimpleName(), name))
                .forEach(printStream::println);
    }

    public String getPackagePrefix() {
        return packagePrefix;
    }

    public void setPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix;
    }
}
