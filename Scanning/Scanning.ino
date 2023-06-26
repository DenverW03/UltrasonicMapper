#include <TinyStepper.h>
#define trigPin 5
#define echoPin 4
#define HALFSTEPS 4096  // Number of half-steps for a full rotation
TinyStepper stepper(HALFSTEPS, 6, 7, 8, 9); // 6 IN1, 7 IN2, 8 IN3, 9 IN4
float currentAngle = 0.00;

void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  stepper.Enable();
  while(true){
    if (Serial.available() > 0) {
      String data = Serial.readString();
      data.trim();
      if(data.equals("start")){
        break;
      }
    }
  }
}

void loop() {
  int currentDistance = getDistance();
  Serial.print(currentDistance);
  Serial.print("+");
  Serial.println(currentAngle);
  incrementAngle();
  delay(10);
}

void incrementAngle(){
  if(currentAngle == 360.00){ 
    stepper.Move(-1 * currentAngle);
    currentAngle = 0.00;
    return;
  }
  stepper.Move(1);
  currentAngle+=1;
  return;
}

// this method gets the distance of the nearest object in CM
long getDistance(){
  long time = (ping() * 0.034) / 2; //calculating the distance using simple physics
  return time;
}

// this method returns the result of a ping using the ultrasonic sensor
int ping(){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  return pulseIn(echoPin, HIGH);
}