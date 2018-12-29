package com.gokhanozg.hardwarels.cpu;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Gokhan Ozgozen on 29-Dec-18.
 */
@Component
public class CpuParser {

    private static final long TIME_OUT = 300;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private List<CPU> allCpu;
    private AtomicInteger atomicInteger = new AtomicInteger(0);


    public List<CPU> getAllCpu() {
        return allCpu;
    }

    private void setSingleThreadResults(List<CPU> multiThreadCPUResults, List<CPU> singleThreadCPUResults) {
        for (CPU multiThreadCPUResult : multiThreadCPUResults) {
            for (CPU singleThreadCPUResult : singleThreadCPUResults) {
                if (singleThreadCPUResult.getCpuName().equals(multiThreadCPUResult.getCpuName())) {
                    multiThreadCPUResult.setSingleThreadPerformance(singleThreadCPUResult.getMultiThreadPerformance());
                    break;
                }
            }
        }
    }

    private void fillCpuInformation(CPU cpu) {
        try {
            String cpuName = cpu.getCpuName();
            int index = cpuName.indexOf("@");
            if (index != -1) {
                cpuName = cpuName.substring(0, index);
                cpuName = cpuName.trim();
            }
            HttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("https://www.techpowerup.com/cpudb/?ajaxsrch=" + URLEncoder.encode(cpuName, "utf-8"));
            HttpResponse response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String responseString = EntityUtils.toString(response.getEntity());
                String tag = "<a href=\"";
                index = responseString.indexOf(tag);
                if (index != -1) {
                    responseString = responseString.substring(index + tag.length());
                    List<String> values = new ArrayList<>();
                    tag = "<td>";
                    index = responseString.indexOf(tag);
                    while (index != -1) {
                        responseString = responseString.substring(index + tag.length());
                        tag = "</td>";
                        index = responseString.indexOf(tag);
                        String value = responseString.substring(0, index);
                        values.add(value);
                        responseString = responseString.substring(index + tag.length());
                        tag = "<td>";
                        index = responseString.indexOf(tag);
                    }
                    if (values.size() == 8) {
                        cpu.setCodeName(values.get(0));
                        cpu.setCores(values.get(1));
                        cpu.setClock(values.get(2));
                        cpu.setSocket(values.get(3));
                        cpu.setLitrhography(values.get(4));
                        cpu.setL3Cache(values.get(5));
                        cpu.setTdp(values.get(6));
                        cpu.setReleased(values.get(7));
                        int cpuInfoCount = atomicInteger.getAndIncrement();
                        if (cpuInfoCount % 10 == 0) {
                            System.out.println("Finished filling cpu information for the " + cpuInfoCount + "th time");
                        }
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private static List<CPU> parseLowestEndCpus() {
        String url = "https://www.cpubenchmark.net/low_end_cpus.html";
        return parsePassmarkCpus(url);
    }

    private static List<CPU> parseSingleThreadCpus() {
        String url = "https://www.cpubenchmark.net/singleThread.html";
        return parsePassmarkCpus(url);
    }

    private static List<CPU> parseLowEndCpus() {
        String url = "https://www.cpubenchmark.net/midlow_range_cpus.html";
        return parsePassmarkCpus(url);
    }

    private static List<CPU> parseMidRangeCpus() {
        String url = "https://www.cpubenchmark.net/mid_range_cpus.html";
        return parsePassmarkCpus(url);
    }

    public static List<CPU> parseHighEndCpus() {
        String url = "https://www.cpubenchmark.net/high_end_cpus.html";
        return parsePassmarkCpus(url);
    }

    private static List<CPU> parsePassmarkCpus(String url) {
        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            HttpResponse httpResponse = client.execute(get);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String response = EntityUtils.toString(httpResponse.getEntity());
                return parsePassmarkResponse(response);
            } else {
                System.err.println("Status code:" + httpResponse.getStatusLine().getStatusCode() + " , for parsing high-end CPUS!!!");
                return Collections.emptyList();
            }


        } catch (Throwable t) {
            t.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static List<CPU> parsePassmarkResponse(String response) {
        List<CPU> cpus = new ArrayList<>();
        int index = response.indexOf("Price (USD)");
        response = response.substring(index + 1);
        String tag = "<tr>";
        index = response.indexOf(tag);
        while (index != -1) {
            response = response.substring(index + tag.length());
            if (response.indexOf("<td class=\"chart\">") == 0) {
                break;
            }
            tag = "<a href=\"cpu.php?cpu=";
            index = response.indexOf(tag);
            if (index == -1) {
                break;
            }
            response = response.substring(index + tag.length());
            tag = "\">";
            index = response.indexOf(tag);
            response = response.substring(index + tag.length());
            tag = "</a>";
            index = response.indexOf(tag);
            String cpuName = response.substring(0, index);
            response = response.substring(index + tag.length());
            tag = "</span>";
            index = response.indexOf(tag);
            response = response.substring(index + tag.length());
            tag = "</div>";
            index = response.indexOf(tag);
            String mark = response.substring(0, index);
            mark = mark.trim();
            tag = "#price\">";
            index = response.indexOf(tag);
            response = response.substring(index + tag.length());
            tag = "</a>";
            index = response.indexOf(tag);
            String price = response.substring(0, index);
            tag = "<tr>";
            cpus.add(createCPU(cpuName, mark, price));
            index = response.indexOf(tag);


        }
        return cpus;
    }

    public static CPU createCPU(String name, String mark, String price) {
        CPU cpu = new CPU();
        cpu.setCpuName(name);
        mark = mark.replaceAll(",", "");
        mark = mark.trim();
        cpu.setMultiThreadPerformance(Double.parseDouble(mark));
        if (price != null && !"NA".equals(price)) {
            price = price.replaceAll("\\$", "");
            price = price.replaceAll(",", "");
            price = price.replaceAll("\\*", "");
            cpu.setDollarPrice(Double.parseDouble(price));
        }
        return cpu;
    }

    @PostConstruct
    public void initializeCpuList() {
        try {
            System.out.println("Initializing cpu parser with " + Runtime.getRuntime().availableProcessors() + " number of cores...");
            Future<List<CPU>> highEndCpusFuture = executorService.submit(CpuParser::parseHighEndCpus);
            Future<List<CPU>> midRangeCpusFuture = executorService.submit(CpuParser::parseMidRangeCpus);
            Future<List<CPU>> lowEndCpusFuture = executorService.submit(CpuParser::parseLowEndCpus);
            Future<List<CPU>> lowestEndCpusFuture = executorService.submit(CpuParser::parseLowestEndCpus);
            Future<List<CPU>> singleThreadCpusFuture = executorService.submit(CpuParser::parseSingleThreadCpus);
            List<CPU> singleThreadResults = singleThreadCpusFuture.get(TIME_OUT, TIME_UNIT);
            List<CPU> lowestEndCpus = lowestEndCpusFuture.get(TIME_OUT, TIME_UNIT);
            List<CPU> lowEndCpus = lowEndCpusFuture.get(TIME_OUT, TIME_UNIT);
            List<CPU> midRangeCpus = midRangeCpusFuture.get(TIME_OUT, TIME_UNIT);
            List<CPU> highEndCpus = highEndCpusFuture.get(TIME_OUT, TIME_UNIT);
            Thread t1 = new Thread(() -> setSingleThreadResults(lowestEndCpus, singleThreadResults));
            Thread t2 = new Thread(() -> setSingleThreadResults(lowEndCpus, singleThreadResults));
            Thread t3 = new Thread(() -> setSingleThreadResults(midRangeCpus, singleThreadResults));
            Thread t4 = new Thread(() -> setSingleThreadResults(highEndCpus, singleThreadResults));
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            Set<CPU> allCpus = new HashSet<>(highEndCpus);
            allCpus.addAll(midRangeCpus);
            allCpus.addAll(lowEndCpus);
            allCpus.addAll(lowestEndCpus);
            allCpus.removeIf(next -> next.getSingleThreadPerformance() == null || next.getDollarPrice() == null);
            this.allCpu = new ArrayList<>(allCpus);
            for (CPU cpu : allCpus) {
                executorService.submit(() -> fillCpuInformation(cpu));
            }
            System.out.println("Finished initializing cpu list!");
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            System.out.println("Finished filling all additional cpu information.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
