package com.example.rashedalam.callpredictor;

public class Operations
{
    void display(int c[],int k_means)
    {
        for(int i=0;i<k_means;i++)
        {
            if(c[i]!=0)
            {
//                System.out.print(c[i]+"  ");
            }}
//        System.out.println();
    }
    float average(int c[],int k_means)
    {
        int count=0,sum=0;
        float avg;
        for(int i=0;i<k_means;i++)
        {
            if(c[i]!=0)
            {
                sum+=c[i];
                count++;
            }}
        avg=(float)sum/count;
        return(avg);
    }}
