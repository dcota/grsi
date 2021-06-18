#include <Adafruit_BME280.h>

#define intervalo 500
#define pot A0
#define ledVerde 2
#define ledAmarelo 3
#define ledVermelho 4
#define buz 5

int leituraPOT=0;
int contador = 0;
unsigned long oldTime=0;
unsigned long newTime=0;

Adafruit_BME280 bmp; //instanciar a classe Adafruit_BMP280;

void setup() {
  Serial.begin(9600);
  bmp.begin();
  //declarar os pinos 2,3 e 3 como saídas
  pinMode(ledVerde,OUTPUT);
  pinMode(ledAmarelo,OUTPUT);
  pinMode(ledVermelho,OUTPUT);
  pinMode(buz,OUTPUT);
  //desligar os pinos 2,3 e 4 ao iniciar
  digitalWrite(ledVerde,LOW);
  digitalWrite(ledAmarelo,LOW);
  digitalWrite(ledVermelho,LOW);
  digitalWrite(buz,LOW);
  teste();
}
void loop() {
  
  String input="";
  while(Serial.available()){
    char c = Serial.read();
    input += c;
  }

  //comandos LED vermelho
  if(input == "L"){
    digitalWrite(ledVermelho, HIGH);
    Serial.println("E;" + (String)digitalRead(ledVermelho));
  }
  if (input == "D") {
    digitalWrite(ledVermelho, LOW);
    Serial.println("E;" + (String)digitalRead(ledVermelho));
  }

  //comandos LED amarelo
  if(input == "A"){
    digitalWrite(ledAmarelo, HIGH);
    Serial.println("E;" + (String)digitalRead(ledAmarelo));
  }
  if (input == "B") {
    digitalWrite(ledAmarelo, LOW);
    Serial.println("E;" + (String)digitalRead(ledAmarelo));
  }

  //comandos LED verde
  if(input == "C"){
    digitalWrite(ledVerde, HIGH);
    Serial.println("E;" + (String)digitalRead(ledVerde));
  }
  if (input == "E") {
    digitalWrite(ledVerde, LOW);
    Serial.println("E;" + (String)digitalRead(ledVerde));
  }
  
  //pedido de inicialização
  if (input == "I") {
    int statusRed = digitalRead(ledVermelho);
    int statusYellow = digitalRead(ledAmarelo);
    int statusGreen = digitalRead(ledVerde);
    String init = "I;" + (String)statusRed + ";" + (String)statusYellow + ";" + (String)statusGreen;
    Serial.println(init);
  }
  
  newTime=millis();
  if(newTime - oldTime >= 200){
    oldTime=newTime;
    float temp = bmp.readTemperature();
    float pres = bmp.readPressure()/100;
    String dados = "S;" + (String)temp + ";" + (String)pres;
    Serial.println(dados);
  }

  
  
 

  

}

void teste(){
  digitalWrite(ledVerde,HIGH);
  digitalWrite(ledAmarelo,HIGH);
  digitalWrite(ledVermelho,HIGH);
  tone(buz,1500,100);
  delay(200);
  tone(buz,2000,100);
  delay(200);
  tone(buz,2500,100);
  delay(200);
  digitalWrite(ledVerde,LOW);
  digitalWrite(ledAmarelo,LOW);
  digitalWrite(ledVermelho,LOW);
  delay(200);
}
