package com.lyg.innerstatic.test;

public class MyThread extends Thread{  
    
    @Override  
    public void run() {   
        System.out.println(MySingleton.getInstance().hashCode());  
    }  
      
    public static void main(String[] args) {   
          
        MyThread[] mts = new MyThread[20];  
        for(int i = 0 ; i < mts.length ; i++){  
            mts[i] = new MyThread();  
        }  
          
        for (int j = 0; j < mts.length; j++) {  
            mts[j].start();  
        }  
    }  
}  