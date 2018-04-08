/*
 알찬 아두이노 키트(입문자용) 의 
 RFID-RC522로 받은 데이터를 화면에 출력하는 예제

 알찬 아두이노 키트(입문자용): http://kit128.com/goods/view?no=133

 개별 부품
 . WAT-Arduino128 : http://kit128.com/goods/view?no=64
 . RFID-RC522 : http://kit128.com/goods/view?no=83

 연결
 Arduino UNO RFID-RC522
 -------------------------------------------------
 10 SPI SS SDA
 13 SPI SCK SCK
 11 SPI MOSI MOSI
 12 SPI MISO MISO
 9 SPI RESET RST 
*/

#include <SPI.h>
#include <MFRC522.h>
#include <SoftwareSerial.h>

SoftwareSerial BTSerial(2, 3); // SoftwareSerial(RX, TX), 블루투스
byte buffer[1024]; // 데이터를 수신 받을 버퍼
int bufferPosition; // 버퍼에 데이타를 저장할 때 기록할 위치
const int raser=6;
const int magnetic=7;
#define RST_PIN 9 // 
#define SS_PIN 10 //

MFRC522 mfrc522(SS_PIN, RST_PIN); // Create MFRC522 instance

void setup() {
  BTSerial.begin(9600); 
  bufferPosition = 0; // 버퍼 위치 초기화
  pinMode(raser,INPUT);
  pinMode(magnetic,INPUT);
  Serial.begin(115200); 
  SPI.begin(); // Init SPI bus
  mfrc522.PCD_Init(); // Init MFRC522
  ShowReaderDetails(); // Show details of PCD - MFRC522 Card Reader details
  Serial.println(F("Scan PICC to see UID, type, and data blocks..."));
}

void loop() {
  if(digitalRead(raser))
  {
    BTSerial.write("1R.");  
  }
  else
  {
    BTSerial.write("0R.");  
  }
  delay(500);
  if(digitalRead(magnetic))
  {
    BTSerial.write("1M.");
  }
  else
  {
    BTSerial.write("0M.");
  }
  delay(500);  
  
//  /* Bluetooth 송수신 확인 - 생략 가능 */
//  if (BTSerial.available()){ // 블루투스로 데이터 수신
//    byte data = BTSerial.read(); // 수신 받은 데이터 저장
//    Serial.write(data); // 수신된 데이터 시리얼 모니터로 출력
//    buffer[bufferPosition++] = data; // 수신 받은 데이터를 버퍼에 저장
//  
//      buffer[bufferPosition] = '\0';
// 
//      // 스마트폰으로 문자열 전송
//      BTSerial.write(buffer, bufferPosition);
//      bufferPosition = 0;
//      
//  }
//  delay(500);

  /* RFID */
  // Look for new cards
  if (!mfrc522.PICC_IsNewCardPresent()) {
    return;
  }

  // Select one of the cards
  if (!mfrc522.PICC_ReadCardSerial()) {
    return;
  }

  // Dump debug info about the card; PICC_HaltA() is automatically called
  // mfrc522.PICC_DumpToSerial(&(mfrc522.uid));
  bufferPosition=0;
  for (byte i = 0; i < 4; i++) {               // 태그의 ID출력하는 반복문.태그의 ID사이즈(4)까지
    buffer[bufferPosition++]=mfrc522.uid.uidByte[i];
    
    Serial.print(mfrc522.uid.uidByte[i], HEX);        // mfrc.uid.uidByte[0] ~ mfrc.uid.uidByte[3]까지 출력
    Serial.print(" ");                        // id 사이의 간격 출력
  }
  Serial.println();
  buffer[bufferPosition++]='D';
  buffer[bufferPosition++]='.';
  buffer[bufferPosition] = '\0';
  BTSerial.write(buffer, bufferPosition);
  bufferPosition=0;
  delay(500);
}

void ShowReaderDetails() {
 // Get the MFRC522 software version
 byte v = mfrc522.PCD_ReadRegister(mfrc522.VersionReg);
 Serial.print(F("MFRC522 Software Version: 0x"));
 Serial.print(v, HEX);
 if (v == 0x91)
 Serial.print(F(" = v1.0"));
 else if (v == 0x92)
 Serial.print(F(" = v2.0"));
 else
 Serial.print(F(" (unknown)"));
 Serial.println("");
//  When 0x00 or 0xFF is returned, communication probably failed
 if ((v == 0x00) || (v == 0xFF)) {
 Serial.println(F("WARNING: Communication failure, is the MFRC522 properly connected?"));
 }
}
