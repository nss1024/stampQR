#include<SoftwareSerial.h>

#define CNC_UART Serial1
#define PC_USB Serial

int txPin = 47;//this is the output pin of arduino and the rx pin of the bluetooth
int rxPin = 51;// this is the input pin of arduino and the tx pin of the bluetooth

SoftwareSerial BTSerial(rxPin, txPin);
String qrDataToProcess= "";
String message = "";
float datastoreArray[1000];
int pointer = 0;
int readPointer=0;

void setup() {
  // put your setup code here, to run once:
  pinMode(txPin,OUTPUT);
  pinMode(rxPin,INPUT);
  BTSerial.begin(9600);//Start Bluetooth serian with baud rate 9600 
  CNC_UART.begin(115200);
  PC_USB.begin(115200);
}

void loop() {
  // put your main code here, to run repeatedly:

  String incominData = "";

  //read and buffer chars until terminator character of ";" is received. 
  while(BTSerial.available()>0){
    char charData = (char) BTSerial.read();
    incominData += charData;
    //Serial.println(incominData);
    delay(1);
    if(charData == ';'){
        qrDataToProcess = incominData;
        incominData = "";
    }
  }
  //collect data NumGCode in array 
  if(qrDataToProcess.length()>1){
    if(qrDataToProcess!="Done;"){
       message = "P";       
       datastoreArray[pointer] = qrDataToProcess.toFloat();
       pointer++;
       delay(2);
      BTSerial.print(message);
    }
    if(qrDataToProcess=="Done;"){      
      while(readPointer<1000){
        PC_USB.print(datastoreArray[readPointer]);
        PC_USB.println();
        readPointer++;
      }

    }   
    qrDataToProcess="";
  }

  //if there is anything in the array, send data to CNC
  if(datastoreArray[0]>0){   
    readPointer=0;
     while(readPointer<1000){
        if(datastoreArray[readPointer]==0){break;}        
            if(datastoreArray[readPointer]>3000){ 
                CNC_UART.print("?");//get CNC status
                delay(5);
               if(getCNCData()=="ok"){
                Serial.print("$J=G90 Z");
                Serial.print (getMovenemt(datastoreArray[readPointer]));
                Serial.print ("F1000");                
               }              
            } // move Z axis datastoreArray[readPointer]-3000 mms            
            if(datastoreArray[readPointer]>2000){
              CNC_UART.print("?");//get CNC status
                delay(5);
               if(getCNCData()=="ok"){
                Serial.print("$J=G90 Y");
                Serial.print (getMovenemt(datastoreArray[readPointer]));
                Serial.print ("F1000");                
               }       
            } // move Y axis datastoreArray[readPointer]-2000 mms            
            if(datastoreArray[readPointer]>1000){
              CNC_UART.print("?");//get CNC status
                delay(5);
               if(getCNCData()=="ok"){
                Serial.print("$J=G90 X");
                Serial.print (getMovenemt(datastoreArray[readPointer]));
                Serial.print ("F1000");                
               }       
            } // move X axis datastoreArray[readPointer]-1000 mms            
            if(datastoreArray[readPointer]<1000){} // either G21 or G990 commands        
        readPointer++;
      }
  }
  
  //Serial.println("Running program...");

}

float getMovenemt(float input){  
    if(input>3000){return input-3000;}      
    if(input>2000){return input-2000;}      
    if(input>1000){return input-1000;}
} 

String getCNCData(){
    String cncInMessage ="";
    while (CNC_UART.available() > 0) {
    char CNCCharData = (char) CNC_UART.read();
    cncInMessage+=CNCCharData;
    PC_USB.print(cncInMessage);
    return cncInMessage;
  }
}
