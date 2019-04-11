import java.io.*;
import java.util.*;
class cluster
{
public static void main (String args[])throws IOException
{
DataInputStream in= new DataInputStream(System.in);
float m1,m2,q=0.0f,w=0.0f;
int k_means;
int z=0,i=0;
int z1=0;
float avg1,avg2;
System.out.println("Enter no of elements in cluster");
k_means=Integer.parseInt(in.readLine());
int a[]=new int[k_means];
int c1[]=new int[10];
int c2[]=new int[10];
System.out.println("Enter elements in cluster");
for(i=0;i<k_means;i++)
{
a[i]=Integer.parseInt(in.readLine());
}
System.out.println("Enter value of m1 and m2");
m1=Integer.parseInt(in.readLine());
m2=Integer.parseInt(in.readLine());
operations op=new operations();
while(q!=m2&&w!=m2)
{
for(i=0;i<k_means;i++)
{
if(Math.abs(a[i]-m1)<Math.abs(a[i]-m2))
{
c1[z]=a[i];
z++;
}
else
{
c2[z1]=a[i];
z1++;
}
}
z=0;z1=0;
System.out.print("Cluster 1\t");
op.display(c1,k_means);
System.out.print("Cluster 2\t");
op.display(c2,k_means);
q=m1;
w=m2;
m1=op.average(c1,k_means);
System.out.print("average of cluster1 "+m1);
System.out.println();
m2=op.average(c2,k_means);
System.out.print("average of cluster2 "+m2);
System.out.println();
}
}}
class operations
{
void display(int c[],int k_means)
{
for(int i=0;i<k_means;i++)
{
if(c[i]!=0)
{
System.out.print(c[i]+"  ");
}}
System.out.println();
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
