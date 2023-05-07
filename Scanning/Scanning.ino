#include <TinyStepper.h>
#define trigPin 7
#define echoPin 6
#define HALFSTEPS 4096  // Number of half-steps for a full rotation
TinyStepper stepper(HALFSTEPS, 8, 9, 10, 11); // 8 IN1, 9 IN2, 10 IN3, 11 IN4
int currentAngle = 0;

void setup() {
  Serial.begin(9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  stepper.Enable();
  delay(1000);
}

void loop() {
  int currentDistance = getDistance();
  Serial.print(currentDistance);
  Serial.print("+");
  Serial.println(((double)currentAngle)/4);
  incrementAngle();
  delay(10);
}

void incrementAngle(){
  if(currentAngle == ((360 * 4) - 1)){ 
    stepper.Move(currentAngle * -1);
    currentAngle = 0;
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