#include <ros.h>
#include <std_msgs/Int32.h>
#include <Servo.h>

// Define el pin de señal del servo
#define SERVO_PIN 9

// Crea un objeto de tipo Servo
Servo servo;

// Crea un objeto de tipo ros::NodeHandle
ros::NodeHandle nh;

// Crea un objeto de tipo std_msgs::Int32 para almacenar el mensaje del servo
std_msgs::Int32 servo_msg;

// Crea un objeto de tipo ros::Publisher para publicar mensajes en el topic "servo_position"
ros::Publisher servo_pub("servo_position", &servo_msg);

// Callback para manejar los mensajes recibidos en el topic "servo_position"
void servoCallback(const std_msgs::Int32& msg)
{
  // Lee el valor del mensaje y lo asigna como posición del servo
  int position = msg.data;
  servo.write(position);
}

// Crea un objeto de tipo ros::Subscriber para suscribirse al topic "servo_position"
ros::Subscriber<std_msgs::Int32> sub("servo_position", &servoCallback);

void setup()
{
  // Inicializa el servo en el pin especificado
  servo.attach(SERVO_PIN);

  // Inicializa la comunicación con ROS
  nh.initNode();
  
  // Se suscribe al topic "servo_position"
  nh.subscribe(sub);

  // Anuncia la existencia del publicador para permitir la comunicación con otros nodos
  nh.advertise(servo_pub);
}

void loop()
{
  // Se mantiene en espera de mensajes de ROS
  nh.spinOnce();
}