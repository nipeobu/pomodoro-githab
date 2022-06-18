package com.nick;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
public class pomodoro {

    static boolean isTest = false;
    static String inTime[] = {"25", "5", "1", "1"};
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Ehi, pomodoro! Напиши команду.");
        String cmd1 = new Scanner(System.in).nextLine();

        if (Objects.equals(cmd1, "")) { cmd1 = "h"; }

        cmd1 = getString(cmd1);

        String[] cmd = cmd1.split(" ");

        boolean isCallHelp = false;

        // время работы
        int workMin = Integer.parseInt(inTime[0]);
        // время отдыха
        int breakMin = Integer.parseInt(inTime[1]);
        // количество итераций
        int count = Integer.parseInt(inTime[2]);
        // коэфф увеличения врем работы
        int koeff = Integer.parseInt(inTime[3]);

        boolean flag = isCallFlag(cmd);

        // длина прогресс-бара
        int sizePrint = 30;

        if (!flag) {
            for (int i = 0; i < cmd.length; i++) {
                switch (cmd[i]) {
                    case "?", "h" -> {
                        printHelpMsg();
                        isCallHelp = true;
                    }
                    case "w" -> workMin = Integer.parseInt(cmd[++i]);
                    case "b" -> breakMin = Integer.parseInt(cmd[++i]);
                    case "c" -> count = Integer.parseInt(cmd[++i]);
                    case "m" -> koeff = 2;
                    case "t" -> {
                        System.out.println("Произвольное время на работу и отдых!");
                        isTest = true;
                    }
                }
            }
        }
        if (count == 1) { koeff = 1; }
        if (flag) { System.out.println("He вepный формат!"); }

        if (!isCallHelp && !isTest && !flag) {
            System.out.printf ("Параметры: работаем %d мин, отдыхаем %d мин, " +
                    "кол-во подходов %d, коэффициент %d\n\n", workMin, breakMin, count, koeff);
            long startTime = System.currentTimeMillis();
            // timer
            for (int i = 1; i <= count; i++) {
                timer(workMin, breakMin, sizePrint);
                workMin *= koeff;
            }
            long endTime = System.currentTimeMillis();
            System.out.println("Pomodoro таймер истек " + (endTime - startTime)/(1000*60) + " мин");
        }
    }
    private static String getString(@NotNull String cmd1) {
        while (cmd1.contains("  ")) {cmd1 = cmd1.replace("  ", " ");}
        while (cmd1.contains("-")) {cmd1 = cmd1.replace("-", "");}
        return cmd1;
    }
    private static boolean isCallFlag(String @NotNull [] cmd)  {
        boolean CallHelp = true;
        try {
            for (int j = 0; j < cmd.length; j++) {
                if (Objects.equals(cmd[j].charAt(0), '0')) {
                    System.out.println(cmd[j] + " " + cmd[j-1]);
                    switch (cmd[j-1]) {
                        case "w" -> cmd[j] = inTime[0];
                        case "b" -> cmd[j] = inTime[1];
                        case "c" -> cmd[j] = inTime[2];
                    }
                }
            }
            for (int i = 0; i < cmd.length; i++) {
                if (cmd[i].equals("-h") || cmd[i].equals("h") || cmd[i].equals("-?") || cmd[i].equals("t") || cmd[i].equals("-t")) {
                    CallHelp = false;
                } else if (cmd[i].equals("-w") || cmd[i].equals("w") || cmd[i].equals("-b") || cmd[i].equals("b") || cmd[i].equals("-c") || cmd[i].equals("c")) {
                    CallHelp = false;
                    int k = Integer.parseInt(cmd[i + 1]);
                    // System.out.println(cmd[i] + " " + k);
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            CallHelp = true;
//            System.out.println("He вepный формат 2");
        } catch (NumberFormatException e) {
            CallHelp = true;
//            System.out.println("He вepный формат 1");
        }
        return CallHelp;
    }
    private static void timer(int workTime, int breakTime, int sizeProgressBar) throws InterruptedException {
        printProgress("Работаем:: ", workTime, sizeProgressBar);
        printProgress("Отдыхаем:: ", breakTime, sizeProgressBar);
    }
    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length = 60 * time / size;
        for (int i = 1; i <= size; i++) {
            String x = String.format("%.1f", i * time * 1.0 / size);
            String percent = String.format("%.1f", i * 100.0 / size);
            System.out.print(process + percent + "% " + (" ").repeat(5 - (String.valueOf(percent).length())) + "[" + ("#").repeat(i) + ("-").repeat(size - i) + "]    ( " + x + "min / " + time + "min )" + "\r");
            if (!isTest) {
                TimeUnit.SECONDS.sleep(length);
            }
        }
        System.out.println();
    }
    private static void printHelpMsg() {
        System.out.println(
                "\nPomodoro - сделай свое время более эффективным!");
        System.out.println(
                "\t-w или w <time>:\tвремя работы, сколько хочешь работать в мин.");
        System.out.println(
                "\t-b или b <time>:\tвремя отдыха, сколько хочешь отдыхать в мин.");
        System.out.println(
                "\t-c или c <count>:\tколичество подходов.");
        System.out.println(
                "\t-m или m :\t\t\tумножение времени работы на 2 с каждым подходом.");
        System.out.println(
                "\t-h или h или -? :\tменю помощи.");
        System.out.println(
                "\t-t или t  :\t\t\tпроизвольное время на работу и отдых! таймер не включается.\n");
    }
}
