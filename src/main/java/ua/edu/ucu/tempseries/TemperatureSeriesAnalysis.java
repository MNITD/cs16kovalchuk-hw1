package main.java.ua.edu.ucu.tempseries;

import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {

    private double[] tempSeries = {};
    private int freePosNum = 0;

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        tempSeries = temperatureSeries;
    }

    public double average() {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }

        double sum = 0;
        for (double d : tempSeries) {
            sum += d;
        }
        return sum / tempSeries.length;
    }

    public double deviation() {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double ave = average();
        double sum = 0;
        for (double d : tempSeries) {
            sum += ((d - ave) * (d - ave));
        }

        return sum / tempSeries.length;
    }

    public double min() {

        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double min = tempSeries[0];
        for (double d : tempSeries) {
            if (d < min) {
                min = d;
            }
        }
        return min;
    }

    public double max() {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double max = tempSeries[0];
        for (double d : tempSeries) {
            if (d > max) {
                max = d;
            }
        }
        return max;
    }

    public double findTempClosestToZero() {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double minMod = Double.MAX_VALUE;
        for (double d : tempSeries) {
            if (Math.abs(d) < minMod) {
                minMod = d;
            } else if (Math.abs(d) == Math.abs(minMod)) {
                minMod = Math.abs(d);
            }
        }
        return minMod;
    }

    public double findTempClosestToValue(double tempValue) {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double temp = Double.MAX_VALUE;
        for (double d : tempSeries) {
            if (Math.abs(d - tempValue) < Math.abs(temp - tempValue)) {
                temp = d;
            }
        }
        return temp;
    }

    public double[] findTempsLessThan(double tempValue) {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double[] tempArray = new double[tempSeries.length];
        double[] newArray;
        int n = 0;
        for (int i = 0; i < tempSeries.length; i++) {
            if (tempSeries[i] < tempValue) {
                tempArray[i] = tempSeries[i];
                n++;
            } else {
                tempArray[i] = tempValue;
            }
        }
        newArray = new double[n];
        n = 0;
        for (int i = 0; i < tempSeries.length; i++) {
            if (tempArray[i] != tempValue) {
                newArray[n] = tempArray[i];
                n++;
            }
        }
        return newArray;
    }

    public double[] findTempsGreaterThan(double tempValue) {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        double[] tempArray = new double[tempSeries.length];
        double[] newArray;
        int n = 0;
        for (int i = 0; i < tempSeries.length; i++) {
            if (tempSeries[i] >= tempValue) {
                tempArray[i] = tempSeries[i];
                n++;
            } else {
                tempArray[i] = tempValue - 1;
            }
        }
        newArray = new double[n];
        for (int i = 0; i < tempSeries.length; i++) {
            if (tempArray[i] != (tempValue - 1)) {
                newArray[n] = tempArray[i];
                n++;
            }
        }
        return newArray;
    }

    public TempSummaryStatistics summaryStatistics() {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        return new TempSummaryStatistics(average(), deviation(), min(), max());
    }

    public int addTemps(double... temps) {
        if (tempSeries.length < 1) {
            throw new IllegalArgumentException();
        }
        for (double d : temps) {
            if (d < -273.0) {
                throw new InputMismatchException();
            }
        }
        if (freePosNum < temps.length) {
            double[] newArray;
            int len = tempSeries.length * 2;
            int posNum = len - tempSeries.length + freePosNum - temps.length;

            while (posNum < 0) {
                len = len * 2;
                posNum = len - tempSeries.length + freePosNum - temps.length;
            }
            freePosNum = posNum;
            newArray = new double[len];

            len = tempSeries.length + temps.length;
            int j = 0;
            for (int i = 0; i < len; i++) {
                if (i < tempSeries.length) {
                    newArray[i] = tempSeries[i];
                } else {
                    newArray[i] = temps[j];
                    j++;
                }
            }
            tempSeries = newArray;
        } else {
            int j = 0;
            freePosNum = freePosNum - temps.length;

            for (int i = tempSeries.length - freePosNum; i < temps.length; i++) {
                tempSeries[i] = temps[j];
                j++;
            }
        }
        return (tempSeries.length - freePosNum);
    }
}
