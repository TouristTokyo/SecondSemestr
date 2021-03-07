package ru.vsu.cs.Shemenev;

public class KreditCallculator {
    private double sum;
    private int n;
    private double r;
    private String name;
    private int moth;

    public KreditCallculator(double sum, int n, double r, String name){
        this.sum = sum;
        this.n = n;
        this.r = r;
        this.name = name;
    }
    public double getSumMoth(){
        if(name.equals("ДИФ")){
            return sum/n + sum*(n-(moth-1))*r/(n*100);
        }
        else {
            double q = 1 + r/100;
            return (sum*Math.pow(q,n)*(q-1))/(Math.pow(q,n)-1);
        }
    }
    public double getSum(){
        if(name.equals("ДИФ")){
            return sum + sum*r*(1+n)/200;
        }
        else {
            double q = 1 + r/100;
            return (sum*Math.pow(q,n)*(q-1)*n)/(Math.pow(q,n)-1);
        }
    }
    public void setMoth(int moth){
        this.moth = moth;
    }
}
