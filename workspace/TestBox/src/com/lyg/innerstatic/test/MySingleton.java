package com.lyg.innerstatic.test;

public class MySingleton {  
    
    //ʹ��volatile�ؼ��ֱ���ɼ���  
    private volatile static MySingleton instance = null;  
      
    private MySingleton(){}  
       
    public static MySingleton getInstance() {  
        try {    
            if(instance != null){//����ʽ   
                  
            }else{  
                //����ʵ��֮ǰ���ܻ���һЩ׼���Եĺ�ʱ����  
            	//Thread.sleep(300);  
                synchronized (MySingleton.class) {  
                	
                    if(instance == null){//���μ��  
                        instance = new MySingleton();  
                    }  
                }  
            }   
        } catch (Exception e) {   
            e.printStackTrace();  
        }  
        return instance;  
    }  
}  