# Networking app in Java

A real-time chat application built in Java using JavaFX for the user interface and UDP for networking, enabling smooth communication across multiple clients connected through a central server. This project combines efficient networking with a user-friendly GUI, providing an ideal foundation for showcasing Java development and networking skills.

## Features

- Real-Time Messaging: Exchange messages instantly across all connected clients.
- Multi-User Chat: Easily add multiple users by starting new client instances.
- User Authentication: Users enter credentials (name, IP, and port) to join the chat.
- Lightweight Server: UDP server handles message routing between clients.
- Simple UI: Clean and intuitive design built with JavaFX, offering easy usability.

## Getting Started
### Prerequisites

- Java Development Kit (JDK) 11 or above: To run and develop the application.
- JavaFX Library: Ensure JavaFX is properly set up as this handles the GUI.
- (Optional) IntelliJ

## Installation

1. Clone the repository:

```bash
https://github.com/rxxxge/JavaFX-Networking.git
cd JavaFX-Networking
```

Set up JavaFX: Ensure JavaFX is configured in your project settings or IDE.

### Build and Run:


## Usage
- Project can be run in IntelliJ where you run `ServerMain` instance first and then run `Login` instance for each client.
For `ServerMain` you need to specify port, which can be done in Configurations of `ServerMain`.

- Without IntelliJ project can be built using maven

## Technical Highlights
- UDP Communication: Achieved fast message delivery with minimal overhead, suitable for real-time chat.
- JavaFX Integration: Built an intuitive and responsive GUI for easy user interaction.
- Scalability: Each client operates independently, allowing multiple instances to join simultaneously.

## Future Enhancements
- Enhanced Security: Implement message encryption for secure communication.
- Group Chat Support: Extend to handle multiple chat rooms or groups.
- File Sharing: Add functionality for sending files between users.

## Screenshots 
![image](https://github.com/user-attachments/assets/0c8983bd-2704-4529-a21a-c2b1ade7d862)
