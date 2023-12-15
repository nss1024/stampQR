/*
  SD card read/write

  This example shows how to read and write data to and from an SD card file
  The circuit:
   SD card attached to SPI bus for Arduino MEGA 2560 is as follows:
 ** MOSI - pin 51
 ** MISO - pin 50
 ** SCK - pin 52
 ** CS - pin 53 

  created   Nov 2010
  by David A. Mellis
  modified 04 Dec 2023
  by N.Simon

  This example code is in the public domain.

*/

/*
 Note:
 Not all pins on the Mega and Mega 2560 support change interrupts,
 so only the following can be used for RX:
 10, 11, 12, 13, 50, 51, 52, 53, 62, 63, 64, 65, 66, 67, 68, 69
*/

/*
Note:
Arduino Mega 2560 support serial ports:
Serial - used for USB to communicate with PC
Serial 1 - pins 19 (RX) and 18 (TX) => Serial1.begin(speed);
Serial 2 - pins 17 (RX) and 16 (TX) => Serial2.begin(speed);
Serial 3 - pins 15 (RX) and 14 (TX) => Serial3.begin(speed);

*/

#include<SoftwareSerial.h>
#include <SPI.h>
#include <SD.h>
#define CNC_UART Serial1

int txPin = 10;//this is the output pin of arduino and the rx pin of the bluetooth
int rxPin = 11;// this is the input pin of arduino and the tx pin of the bluetooth
SoftwareSerial BTSerial(rxPin, txPin);
String qrDataToProcess= "";
String message = "";
bool fileOpen=0;
bool dataTransferComplete = 0;
bool testMode = 1; // used of testing so program does not get stuck in infinite waiting loop
int attemptCounter = 0;

File myFile;

void setup() {
   BTSerial.begin(9600);//Start Bluetooth serian with baud rate 9600 
  // Open serial communications and wait for port to open:
  Serial.begin(9600); //Start USN serial for PC output
  CNC_UART.begin(115200); // Start CNC serial for I/O with CNC
  while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  pinMode(53,OUTPUT);

  Serial.print("Initializing SD card...");

  if (!SD.begin(53)) {
    Serial.println("initialization failed!");
    while (1);
  }
  Serial.println("initialization done.");

}

void loop() {  
    String incominData = "";
  //read and buffer chars until terminator character of ";" is received. 
  while(BTSerial.available()>0){
    
    if(!fileOpen){
      myFile = SD.open("test2.txt", FILE_WRITE);
      fileOpen=1;
      Serial.println("File open! ");
    }

    char charData = (char) BTSerial.read();
    incominData += charData;
    //Serial.println(incominData);
    delay(1);
    if(charData == ';'){
        qrDataToProcess = incominData;
        Serial.println(qrDataToProcess);
        if(qrDataToProcess.indexOf("Done")<0){//if incoming data not "Done write it to file"
          myFile.println(qrDataToProcess);
        }
        incominData = "";
    }
  }
    //if not done, send "P" back to phone (P=processed)
   if(qrDataToProcess.length()>1){
    if(qrDataToProcess!="Done;"){
       message = "P";       
       delay(2);      
      BTSerial.print(message);
    }
    //if "Done" received from phone , there will be no more data, close the file , set datatransfer complete flag.
    if(qrDataToProcess=="Done;"){      
     myFile.close();
     Serial.println("File closed!");
     dataTransferComplete = 1;
    }   
    
    qrDataToProcess="";
  }
  //Once data has been received from mobile start processing it
  if(dataTransferComplete){
    String fileData = "";
     myFile = SD.open("test2.txt"); // Open file for reading
     while (myFile.available()) { // while there is data in the file, main while loop
      char fileChar = myFile.read(); // read char by char
      fileData +=fileChar; // concatenate chars to a String      
      if(fileChar==';'){ // end of a CNC instruction reached
        int charIndex = fileData.lastIndexOf(';');//get index of ";"
          fileData.remove(charIndex);//remove ";"             
          Serial.println("Data sent to CNC:");
          Serial.println(fileData);  //log what has been sent to CNC
          CNC_UART.println(fileData); // send data to CNC
          delay(500);//wait for CNC to respond
          while(CNC_UART.available()==0){if(testMode){break;}}//wait for CNC to reply
          while(CNC_UART.available()>0){  //process reply
            if(testMode){break;}
            incominData = "";
            char charData = (char) CNC_UART.read();
            incominData += charData;
            //Serial.println(incominData);
            delay(1);            
              if(incominData.indexOf("OK")>=0||incominData.indexOf("ok")>=0){//if CNC reply "OK" move on
                  incominData = "";
                  attemptCounter = 0;
                  break;
                }
               if(incominData.indexOf("OK")<0||incominData.indexOf("ok")<0){//if CNC reply NOT "OK"
                  delay(500);//give it more time;
                  break;
                }    
                
          }

          fileData="";
      }
      
    }
    dataTransferComplete=0; // once data sent to CNC, reset dataTransferComplete
  }

}


