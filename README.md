# UltrasonicMapper
Cheap alternative to short range LIDAR :)

v-- Scanning Directory --v
Micro-controller software for running an adafruit small reduction stepper motor, driving a gear with ratio 1:4, with the larger gear connecting to a HC-SR04 ultrasonic scanner.

v-- Mapping Directory --v
Java based software for reading from serial port (micro-controller output) and mapping it onto a 2D cartesian axis, maxY being the "front" of the device. 

v--(quick build/run instructions for a jar package)--v

mvn clean package; java -jar target/ultra-mapper-0.01-SNAPSHOT.jar
