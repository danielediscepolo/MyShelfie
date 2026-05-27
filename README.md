# Board Game: MyShelfie - Software Engineering Project

This game was developed as a computer game for the final examination of the Software engineering course at Politecnico di Milano (A.Y. 2022/2023)
![alt text](src/main/resources/Graphics/Publisher_material/Display_1.jpg)
<br>
## Group members
- *Daniele Discepolo*
- *Jacopo Taccucci*
- *Gabriele Greco*
- *Alice Ferrante*
  <br>

## How to play
  In order to start the game, you must first start the ServerApp class, which acts as a server for both socket connections and for RMI connections. To start the clients, on the other hand, it is necessary to start the ClientApp class (as many times as there are players).
  The game starts in the CLI via the interactive menu, which offers the player the possibility of starting a new game or continuing one in the case of persistence and disconnections.
  To switch to the GUI, it is necessary, as also indicated in the menu, to select '3' as an option.

### Socket

To start a client Socket: 
```
java -jar ClientApp.jar --server-ip localhost --server-port 2308 --interface cli --network socket
```

### RMI

To start a client Rmi:
```
java -jar ClientApp.jar --server-ip localhost --server-port 2308 --interface cli --network rmi
```

### Server 

To start a Server:

```
java -jar Server.jar --ip-address localhost

```


## Functionalities Implemented

| Functionality                | State |
|:-----------------------------|:-----:|
| Simple rules                 |  🟩   |
| Complete rules               |  🟩   |
| Socket                       |  🟩   |
| RMI                          |  🟩   |
| CLI                          |  🟩   |
| GUI                          |  🟩   |
| Multiple games               |  🟩   |
| Persistence                  |  🟩   |
| Resilience to disconnections |  🟩   |
| Chat                         |  🟥   |


# Develop
## Network
### ServerApp

 Main Process for MyShelfie Server.

 The following structure is fundamental to implement in an elegant way Resilience, Persistence and Multi-match features.
 ServerApp is primarily based on the use of 3 data structures:<br/>
* 3 lists of players representing the 3 waiting rooms (2,3 and 4 players) enclosed in the WaitingRoom object;<br/>
* 1 BlockingQueue representing the shared resource in a Consumer Producer Pattern.<br/>

 ServerApp plays the role of a Consumer of Login requests. These are processed one at a time to maintain robustness in
 execution.<br/>

 Elements from the queue are taken with the take method, blocking to ensure correct execution if the queue is empty.

The login name is the only discriminator to distinguish different players, they must therefore be unique.<br/>
Requests are processed in the following way:

   If a player with the same name exists and is in the game:
*               The incoming request is discarded with notification.
If a player with the same name exists and is in the game but is logged out:
*               Since the login name is the only authenticating element, the incoming request is assumed to be an
                attempt to reconnect. The connection object of the disconnected player is then updated.
If a player with the same name exists and is in waitingRoom:
*               The incoming request is discarded with notification.
 If there are no players with the same name:
*               At this point an attempt is made to assign the newly connected player to a random waiting room that has
                at least one player (to speed up the start). If this is not possible, it means that all 3 are empty.
                Then the waiting room chosen by the player in the preferences is used.
                If the waiting room is full the game is initialised in a new thread, players are added to the list of
                players in the game and the waiting room is emptied.
ServerApp also maintain a HashMap of Playing players. This Data Structure is a centralized store for valid Connection
objects.<br/>

Whenever VirtualView needs to send a message to a player, it refers to the connections contained here. Whenever
ServerApp needs to update a connection, the corresponding player in the map is updated.<br/>

Note that ServerApp skims unconnected players each time it checks waiting rooms.<br/>

ServerApp, as like as Patrick class, implements a Persistence functionality. At the start of a match, it passes to
Patrick constructor a unique filename. The uniqueness of the filename is entirely based on timestamp,
given that the thread generating the filename is unique in the context of a Server process,
and assuming there are no other Servers with which exchange save-files, we can firmly state that for the scope of
this project to generate filenames have 1-to-1 relationship with matches.<br/>

When the server is restarted after a shutdown, it checks how many save files are in directory specified in the arguments.
For each one try to deserialize the content in a Game obj. Then it checks for consistency in players and start the Game.

### Client Interface

Interface for Strategy pattern. It acts as a separator for the network layer.<br/>
Implementing classes such as RMI Client and Socket Client should provide the following capabilities: <br/>
1.  A first part provides a way to listen for incoming Login messages and update the ServerApp's queue of incoming login requests.<br/>
- Start a thread listening for incoming connections. <br/>
- For each successful connection a new Thread should listen for incoming login messages and update the central queue. <br/>
- The update process should use a static method in the ServerApp.<br/>

2.  A second part should provide a blocking way for Controller and Client to wait for messages (wait for message method) and to send messages.<br/>

### Server Interface

Interface for Strategy pattern. It acts as a separator for the network layer.<br/>
Implementing classes such as RMI Server and Socket Server should provide the following capabilities: <br/>
1. A first part provides a way to listen for incoming Login messages and update the ServerApp's queue of incoming login requests.<br/>
- Start a thread listening for incoming connections. <br/>
- For each successful connection a new Thread should listen for incoming login messages and update the central queue. <br/>
- The update process should use a static method in the ServerApp.<br/>

2. A second part should provide a blocking way for Controller and Client to wait for messages (wait for message method) and to send messages.<br/>

## Message

Abstract Serializable class representing the Message of the Communication Protocol.<br />
Each message is a wrapper for specific information, and is conveyed by the network sublayer (RMI or Socket)<br />
The messages available in the communication protocol are as follows:<ul>
LoginMessage:
*               Client -> Server:
                 Message to make a login request.
                Client side: Insert name and numOfPlayers.
                Server side: Unpack the Message, create a New Player obj and 
                add a connection object in it.
NameAvailabilityMessage: 
*               Server -> Client:
                Message to inform the client whether the chosen name is available or not.
                Client side: In case of availability, wait for the game to start, 
                otherwise asks the player for a new name.
                Server side: see ServerApp 
UpdateGameMessage: 
*               Server -> Client:
                Message to update the client-side model of the game.
                Client side: The Clients can interpret it as the start of game, but not as the start of a turn 
                (for this it must wait for MoveRequestMessage).
                Server side: Sent to everybody at STARTGAME, STARTTURN, ENDTURN.
MoveRequestMessage: 
*               Server -> Client:
                Message used whenever the player is asked to make a move, including the start of the turn.
                It has attributes to specify the type of move ( PICKING,SORTING, COLUMNCHOICE, CONFIRMATION ).
                Client side: Wait for this message to start the turn and/or a particular move.
                Server side: Send it to client at start of turn (multiple times if at the endturn the client does 
                not confirm the turn).
PickMoveMessage: 
*               Client -> Server:
                Message to inform the server for the picked Tiles. It can contain the passedTurn flag.
                Client side: Write into it the List of positions in the Board or the passedTurn flag. 
                Server side: Check if the turn is passed, in this case skip the turn, otherwise take the List of 
                positions in the Board from the message.
SortMoveMessage: 
*               Client -> Server:
                Message to inform the server for the sorted tiles. It can contain the passedTurn flag.
                Client side: Write into it the sorted List of tiles or the passedTurn flag.
                Server side: Check if the turn is passed, in this case skip the turn, otherwise take the sorted 
                List of tiles from the message.
ColumnMoveMessage: 
*               Client -> Server:
                Message to inform the server for the Column chosen. It can contain the passedTurn flag. 
                Client side: Write into it the column number or the passedTurn flag.
                Server side: Check if the turn is passed, in this case skip the turn, otherwise take the column from 
                the message.
TurnConfirmationMessage: 
*               Client -> Server:
                Message used by the client to confirm a Turn.
                Client side: Sent after confirmation prompt (upon reception of MoveRequestMessage of Reception type).
                Server side: Upon reception, server can restart the turn or move to the next player. 
ResponseMessage: 
*               Server -> Client:
                General response message that the server sends after a move made by the player.
                It may contain a confirmation of the move or a relative error.
                Client side: If there is an error repeat the move, otherwise wait for next MoveMessage.
                Server side: Sent after a move.
EndGameMessage: 
*               Server -> Client
                Message to inform the Client that the game is concluded and it is time to announce the winner.
                Client side: Take Winner's name from the message and update it.
                Server side: Sent at the end og the game. This is the last message sent by a controller thread.
PingMessage: 
*               Server <-> Client
                Simple Ping message to test connection.
                Both  sides: Sent for test network connection. Discarded on reception.

## MVC 

### Patrick

Controller of the implemented MVC pattern.<br/>
Its task is to interface View and Model.<br/>

Through the View (VirtualView class), this class communicates with a specific user, receiving input,
communicating results and updates to the Model in a completely transparent manner. <br/>

Thanks to the VirtualView, in fact, there is a clear separation between the management of the game and the
management of communication with the user, through which it is also possible to initialise a game locally
(where all the player can play through CLI in the server, especially useful in debug phases).<br/>

In the context of the game, this class takes care of the management of the turn and consequently performs checks
to manage every event connected with the user, Such as disconnections, and choosing to pass the turn.<br/>

Each instance of this class identifies a specific MyShelfie match.<br/>

It is created by ServerApp, which starts the game as soon as a waiting room is complete.<br/>

In order to implement the possibility of managing several matches simultaneously, each instance of this class
is managed in a separate Thread.<br/>

Considering that ServerApp, for simplicity and clarity of implementation, does not maintain a status of the
launched Threads, as the last action of the run() method, this class takes care of deleting players from
the Map of Players in the ServerApp.<br/>

The Controller implements a Persistence functionality. The controller use as save-point a unique filename
generated by ServerApp. At the end of each turn (excluding the last for usability issues), in a point where
we have a consistent state, it serializes and saves the state of Game obj in the file indicated by the filename.
The restoration of a savepoint do not affect the flow of the controller, thanks to the run() structure.
Ensuring a 1-to-1 relationship between files and matches we can avoid synchronization issues.
Also, using different files, increase usability of the server, giving an operator the possibility to manually
change them.

We can briefly schematise the match-making flow in this way: ServerApp --> Patrick --> Game --> All the rest of the model

### Virtual view

VirtualView is a server-side class intended to dispatch messages from client to server.<br />
So it acts as a network handler, ensuring player connections are active.
It acts like a Virtual View in respect to the server-side Controller and Model.<br />
It has an Observer role in respect to GameView, and it sends update messages to the clients.<br />
View uses UserInterface class to realize the user interface in a CLI or GUI fashion.<br />
<br />
N.B. Every method in VirtualView can handle directly a match played in Local mode on the Server.
In this mode, Virtual View acts as an actual View.
This feature is implemented to debug and stress test the MVC portion on the project.
For this reason, and to keep the arch simple, this feature is implemented in VirtualView and not in a dedicated class.<br />

## View

### User Interface

Interface used to abstract the concept of User Interface. Useful for the implementation of distinct user interface modes and for the capability to switch between them.

### CLI
CLI is Intended to handle the Input/output of the project in the context of a Command Line Interface.

### Board
![alt text](/README_RES/Board_Cli.png)


### Bookshelf
![alt text](/README_RES/Bookshelf_Cli.png)


### Cli interface
![alt text](/README_RES/CliUpdate.png)


### GUI
The gui is implemented, again to maintain the strategy pattern, in a manner similar to the cli with a few differences.
Each method that is invoked loads a different scene, which depending on whether it expects client input,
returns a value or not, encapsulating the logic and controls on the player's action within each individual scene,
making each scene atomic and independent of each other, being connected only through individual invocations of
clientApps.

To do this, we implemented a CallbackRunner class in order not to conflict with the JavaFx thread owner
by scheduling through lamba functions each scene and function of the game.

### Welcome scene
![alt text](/README_RES/Welcome.png)

### Waiting Room scene
![alt text](/README_RES/waitingRoom.png)

### UpdateModel scene
![alt text](/README_RES/UpdateBoard.png)

### Pick scene
![alt text](/README_RES/pick.png)

### Sort scene
![alt text](/README_RES/Sort.png)

### Insert scene
![alt text](/README_RES/Insert.png)

### Retry scene
![alt text](/README_RES/retry.png)

### Winner scene
![alt text](/README_RES/Winner.png)

# Requirements
Requires Java 19 or a more recent version.
