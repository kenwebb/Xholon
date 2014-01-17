package org.primordion.user.xmiapps.Elevator;

import org.primordion.xholon.app.Application;
import org.primordion.xholon.base.IMessage;
import org.primordion.xholon.base.StateMachineEntity;
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.IXholonTime;
import org.primordion.xholon.base.XholonTime;
import org.primordion.xholon.service.svg.SvgClient;

/**
	Elevator application - Xholon Java
	<p>Author: KenWebb</p>
	<p>Date:   23/05/2007</p>
	<p>File:   Elevator.mdzip</p>
	<p>Target: Xholon 0.5 http://www.primordion.com/Xholon</p>
	<p>UML: MagicDraw UML 11.5</p>
	<p>XMI: 2.1, Wed May 23 12:19:43 EDT 2007</p>
	<p>XSLT: 1, Apache Software Foundation, http://xml.apache.org/xalan-j</p>
*/
public class XhElevator extends XholonWithPorts implements CeElevator {

// references to other Xholon instances; indices into the port array
public static final int DoorControl3 = 3;
public static final int DoorHoistControl4 = 4;
public static final int ElevatorDispatch2 = 2;
public static final int GuiComm = 0;
public static final int PushbuttonMessages0 = 0;
public static final int Ticks0 = 0;
public static final int LocationInfo0 = 0;
public static final int PushbuttonMessages1 = 1;
public static final int PushbuttonMessages2 = 2;
public static final int Ticks5 = 5;
public static final int ElevatorDispatch3 = 3;
public static final int PushbuttonMessages3 = 3;
public static final int PushbuttonMessages4 = 4;
public static final int PushbuttonMessages5 = 5;
public static final int PushbuttonMessages6 = 6;
public static final int PushbuttonMessages7 = 7;
public static final int LocationInfo1 = 1;

// Signals, Events
public static final int SOUT_ACK = 501;
public static final int SIN_REQUESTED = 500;
public static final int SOUT_REQUEST_SATISFIED = 502;
public static final int SOUT_CLOSE_DOOR = 102;
public static final int SOUT_OPEN_DOOR = 103;
public static final int SIN_DOOR_CLOSED = 100;
public static final int SIN_DOOR_OPENED = 101;
public static final int SIN_E_N_IS_AT = 409;
public static final int SIN_B_UP_PUSHED = 407;
public static final int SOUT_B_UP_REQUEST_SATISFIED = 413;
public static final int SIN_B_DOOR_CLOSE = 401;
public static final int SOUT_MOVE_TO_FL_N = 303;
public static final int SIN_B_DOOR_OBSTRUCTED = 402;
public static final int SIN_IS_AT = 300;
public static final int SIN_B_DOWN_PUSHED = 406;
public static final int SOUT_B_FLOOR_REQUEST_SATISFIED = 417;
public static final int SOUT_B_FLOOR_ACK = 418;
public static final int SOUT_UNLOCK_DOOR = 203;
public static final int SOUT_B_UP_ACK = 414;
public static final int SIN_B_DOOR_OPEN = 400;
public static final int SOUT_LOCK_DOOR = 202;
public static final int SIN_READY = 405;
public static final int SOUT_B_DOWN_REQUEST_SATISFIED = 415;
public static final int SIN_IS_READY_NO_DEST = 301;
public static final int SIN_DOOR_LOCKED = 200;
public static final int SOUT_TICK = 600;
public static final int SOUT_B_DOWN_ACK = 416;
public static final int SOUT_MOVE_E_M_TO_FL_N = 419;
public static final int SIN_B_FLOOR_PUSHED = 408;
public static final int SIN_IS_READY_DEST = 302;
public static final int SIGNAL_ANY = -4;

// Variables
public String roleName = null;
protected boolean lockRequested; // Door
private int currentDirection; // Hoist
private int destinationFloor; // Hoist
private int arrivalFloor; // Hoist
private int cycleNoDest; // Hoist
private byte hoistDestination[] = new byte[8]; // Hoist
private byte dispatchDestination[] = new byte[16]; // Dispatcher
private IXholonTime xhTime; // TickGenerator
protected static final int NUM_TIMESTEPS_PER_TICK = 10; // TickGenerator
public static final int MaxFloors = 8; // AnElevatorSystem
public static final int MaxElevators = 2; // AnElevatorSystem
public static final int NumUpDown = 2; // AnElevatorSystem
public static final int NoDestinationFound = -1; // AnElevatorSystem
public static final int udd_UP = 1; // AnElevatorSystem
public static final int udd_DOWN = 2; // AnElevatorSystem
public static final int des_AVAILABLE = 1; // AnElevatorSystem
public static final int des_REQUESTED = 2; // AnElevatorSystem
public static final int des_BEING_SERVICED = 3; // AnElevatorSystem
protected static volatile int selectedInjectButton = -1; // UserJTree
private static int testScenario = 101; // UserJTree

// for use with the SVG gui when sending a button-click msg to self
private static IXholon userJTree = null;

// Constructor
public XhElevator() {super();}

// Setters and Getters
public void setRoleName(String roleName) {this.roleName = roleName;}
public String getRoleName() {return roleName;}

public void setCurrentDirection(int currentDirection) {this.currentDirection = currentDirection;}
public int getCurrentDirection() {return currentDirection;}

public void setDestinationFloor(int destinationFloor) {this.destinationFloor = destinationFloor;}
public int getDestinationFloor() {return destinationFloor;}

public void setArrivalFloor(int arrivalFloor) {this.arrivalFloor = arrivalFloor;}
public int getArrivalFloor() {return arrivalFloor;}

public void setCycleNoDest(int cycleNoDest) {this.cycleNoDest = cycleNoDest;}
public int getCycleNoDest() {return cycleNoDest;}

public static void setTestScenario(int testScenarioArg) {testScenario = testScenarioArg;}
public static int getTestScenario() {return testScenario;}

public void initialize()
{
	super.initialize();
}

// Hoist
protected void insertDestination(int floor)
{
hoistDestination[floor] = des_REQUESTED;
}

// Hoist
protected void deleteDestination(int floor)
{
hoistDestination[floor] = des_AVAILABLE;
}

// Hoist
protected int selectHoistDestination(int currentFloor, int currentDirection, int numFloors)
{
int floor = NoDestinationFound;
switch(currentDirection) {
case udd_UP:
  for (floor = currentFloor; floor < numFloors; floor++) {
    if (hoistDestination[floor] == des_REQUESTED) {
      return floor;
    }
  }
  break;
case udd_DOWN:
  for (floor = currentFloor; floor >= 0; floor--) {
    if (hoistDestination[floor] == des_REQUESTED) {
      return floor;
    }
  }
  break;
default:
  break;
}
return NoDestinationFound;
}

// Hoist
protected boolean isDestination(int floor)
{
if (hoistDestination[floor] == des_REQUESTED) {
  return true;
}
else {
  return false;
}
}

// Dispatcher
protected void insertDestination(int floor, int direction)
{
dispatchDestination[floor + ((direction - 1) * MaxFloors)] = des_REQUESTED;
}

// Dispatcher
protected void deleteDestination(int floor, int direction)
{
dispatchDestination[floor + ((direction - 1) * MaxFloors)] = des_AVAILABLE;
}

// Dispatcher
protected int selectDispatchDestination(int floor1, int floor2, int direction)
{
int floor = NoDestinationFound;
switch(direction) {
case udd_UP:
  for (floor = floor1; floor < floor2; floor++) {
    if (dispatchDestination[floor + ((udd_UP - 1) * MaxFloors)] == des_REQUESTED) {
      return floor;
    }
  }
  break;
case udd_DOWN:
  for (floor = floor1; floor > floor2; floor--) {
    if (dispatchDestination[floor + ((udd_DOWN - 1) * MaxFloors)] == des_REQUESTED) {
      return floor;
    }
  }
  break;
default:
  break;
}
return NoDestinationFound;
}

// Dispatcher
protected boolean isDestination(int floor, int direction)
{
return dispatchDestination[floor + ((direction - 1) * MaxFloors)] == des_BEING_SERVICED;
}

// Dispatcher
protected void updateDestination(int floor, int direction)
{
dispatchDestination[floor + ((direction - 1) * MaxFloors)] = des_BEING_SERVICED;
}

public void act()
{
	switch(xhc.getId()) {
	case AnElevatorSystemCE:
		processMessageQ();
		break;
	case UserJTreeCE:
	{
		if (selectedInjectButton != -1) {
		  switch (selectedInjectButton) {

		  // PushbuttonMessages0 bFlUp
		  case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:
		    port[PushbuttonMessages0].sendMessage(SIN_REQUESTED, null, this, selectedInjectButton); break;

		  // PushbuttonMessages4 bFlDown
		  case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15:
		    port[PushbuttonMessages4].sendMessage(SIN_REQUESTED, null, this, selectedInjectButton-8); break;

		  // PushbuttonMessages5 bFloor
		  case 16: case 17: case 18: case 19: case 20: case 21: case 22: case 23:
		  case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31:
		    port[PushbuttonMessages5].sendMessage(SIN_REQUESTED, null, this, selectedInjectButton-16); break;

		  // LocationInfo iFloor
		  case 32: case 33:
		    // do nothing; no signals defined yet for LocationInfo
		    break;

		  // PushbuttonMessages6 bOpen
		  case 34: case 35:
		    port[PushbuttonMessages6].sendMessage(SIN_REQUESTED, null, this, selectedInjectButton-34); break;

		  // PushbuttonMessages7 bClose
		  case 36: case 37:
		    port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, selectedInjectButton-36); break;

		  // DoorControl3 doorControl
		  case 38:
		    port[DoorControl3].sendMessage(SIN_DOOR_OPENED, null, this, 0); break;
		  case 39:
		    port[DoorControl3].sendMessage(SIN_DOOR_CLOSED, null, this, 0); break;
		   case 40:
		    port[DoorControl3].sendMessage(SIN_DOOR_OPENED, null, this, 1); break;
		   case 41:
		    port[DoorControl3].sendMessage(SIN_DOOR_CLOSED, null, this, 1); break;

		  // ElevatorDispatch2 elevatorDispatch
		  case 42: case 43: case 44: case 45:
		    // these messages all require data along with the signal
		    break;

		  default: break;
		  }
		  selectedInjectButton = -1; // reset
		}
	}
		break;
	case XholonClassCE:
	{

	}
		break;
	default:
		break;
	}
	super.act();
}

public void processReceivedMessage(IMessage msg)
{
	switch(xhc.getId()) {
	default:
		IXholon node = getFirstChild();
		while (node != null) {
			if (node.getClass() == StateMachineEntity.class) {
				((StateMachineEntity)node).doStateMachine(msg); // StateMachine
				break;
			}
			else {
				node = node.getNextSibling();
			}
		}
		if (node == null) {
			System.out.println("XhElevator: message with no receiver " + msg);
		}
		break;
	}
}

public void performActivity(int activityId, IMessage msg)
{
	switch (activityId) {
		case 1181833: // Door Unlocked SIN_DOOR_CLOSED
port[PushbuttonMessages1].sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 9521824: // Door Unlocked SIN_DOOR_OPENED
// Set timer to go off in 500 ms.
//println("Door will automatically close ...");
xhTime.timeoutRelative(this, ((Application)getApp()).getTimeStepInterval() * 50);

port[PushbuttonMessages0].sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 3771899: // Door Unlocked SIN_REQUESTED
// also need to cancel timer
//println("Door Open-->Closing received SIN_REQUESTED " + getName());
port[DoorControl3].sendMessage(SOUT_CLOSE_DOOR, null, this);
			break;
		case 9941743: // Door Unlocked 
port[DoorControl3].sendMessage(SOUT_CLOSE_DOOR, null, this);
			break;
		case 5451839: // Door Unlocked SOUT_LOCK_DOOR
port[DoorHoistControl4].sendMessage(SIN_DOOR_LOCKED, null, this);
			break;
		case 1911836: // Door Unlocked SIN_REQUESTED
port[DoorControl3].sendMessage(SOUT_OPEN_DOOR, null, this);
			break;
		case 5241827: // Door Unlocked SIN_REQUESTED
// Set timer to go off after 500 ms.
port[DoorControl3].sendMessage(SOUT_CLOSE_DOOR, null, this);
			break;
		case 5811831: // Door Unlocked SIN_REQUESTED
port[DoorControl3].sendMessage(SOUT_OPEN_DOOR, null, this);
			break;
		case 25991997: // Door StateMachine_Door 
lockRequested = false;
xhTime = new XholonTime();
//print("Door " + getName() + " is initialized.");
			break;
		case 5591895: // Door StateMachine_Door SOUT_UNLOCK_DOOR
lockRequested = false;
port[DoorControl3].sendMessage(SOUT_OPEN_DOOR, null, this);
			break;
		case 52641933: // Door StateMachine_Door SIN_DOOR_OPENED
// Reset the button.
port[PushbuttonMessages0].sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 53931938: // Door StateMachine_Door SIN_DOOR_CLOSED
// Reset the button.
port[PushbuttonMessages1].sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 41911943: // Door StateMachine_Door SOUT_LOCK_DOOR
lockRequested = true;
			break;
		case 94951948: // Door StateMachine_Door SOUT_UNLOCK_DOOR
// Ignore an initial unlock.
			break;
		case 99621957: // Door StateMachine_Door SIN_REQUESTED
// received on bOpen port
// Something is wrong, but tell sender that everything is OK.
msg.getSender().sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 19991962: // Door StateMachine_Door SIN_REQUESTED
// received on bClose port
// Something is wrong, but tell sender that everything is OK.
msg.getSender().sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 55461997: // Hoist StateMachine_Hoist 
setDestinationFloor(0);
setArrivalFloor(0);
setCurrentDirection(udd_UP);
insertDestination(getDestinationFloor());
setCycleNoDest(1);
			break;
		case 8771862: // Hoist StateMachine_Hoist SIN_DOOR_LOCKED
if (getCurrentDirection() == udd_UP) {
  port[ElevatorDispatch3].sendMessage(SOUT_MOVE_TO_FL_N, new Integer(getArrivalFloor() + 2), this);
}
else {
  port[ElevatorDispatch3].sendMessage(SOUT_MOVE_TO_FL_N, new Integer(getArrivalFloor()), this);
}
			break;
		case 9511859: // Hoist StateMachine_Hoist SOUT_TICK
setDestinationFloor(selectHoistDestination( getArrivalFloor(), getCurrentDirection(), MaxFloors ));
			break;
		case 59911921: // Hoist StateMachine_Hoist 
int combo = 0;
switch( getCycleNoDest() ) {
case 1:
  combo = (getArrivalFloor() << 8) + (MaxFloors - 1);
  setCurrentDirection(udd_UP);
  setCycleNoDest(2);
  break;
case 2:
  combo = (getArrivalFloor() << 8) + 0;
  setCurrentDirection(udd_DOWN);
  setCycleNoDest(3);
  break;
case 3:
  combo = ((MaxFloors - 1) << 8) + getArrivalFloor();
  setCurrentDirection(udd_DOWN);
  setCycleNoDest(4);
  break;
case 4:
  combo = (0 << 8) + getArrivalFloor();
  setCurrentDirection(udd_UP);
  setCycleNoDest(1);
  break;
default:
  setCycleNoDest(1);
  break;
}
port[ElevatorDispatch2].sendMessage(SIN_IS_READY_NO_DEST, new Integer(combo), this);
			break;
		case 23991924: // Hoist StateMachine_Hoist 
deleteDestination( getArrivalFloor() );
port[ElevatorDispatch2].sendMessage(SIN_IS_AT, new Integer(getArrivalFloor()), this);
port[DoorHoistControl4].sendMessage(SOUT_UNLOCK_DOOR, null, this);
if ( getCurrentDirection() == udd_UP ) {
  setCycleNoDest(1);
}
else {
  setCycleNoDest(2);
}
			break;
		case 85341922: // Hoist StateMachine_Hoist 
port[ElevatorDispatch2].sendMessage(SIN_IS_READY_DEST, new Integer((getArrivalFloor() << 8) + getDestinationFloor()), this);
port[DoorHoistControl4].sendMessage(SOUT_LOCK_DOOR, null, this);
			break;
		case 9611868: // Hoist StateMachine_Hoist SIN_IS_AT
setArrivalFloor(((Integer)msg.getData()).intValue() - 1);

// GWT SVG
String elevNum = "2";
String dirStr = "inc";
if (this.getParentNode().getNextSibling().getXhcId() == ElevatorCE) {
  // this is the first of 2 Elevator nodes
  elevNum = "1";
}
if ( getCurrentDirection() == udd_UP ) {
  dirStr = "dec";
}
((SvgClient)getApp().getView().getFirstChild()).xmlAttr("AnElevatorSystem/Elevator[" + elevNum
    + "]/Hoist,y," + dirStr + "(32)");

port[ElevatorDispatch2].sendMessage(SIN_IS_READY_DEST, new Integer((getArrivalFloor() << 8) + getDestinationFloor()), this);
			break;
		case 91991929: // Hoist StateMachine_Hoist 
if (getCurrentDirection() == udd_UP) {
  port[ElevatorDispatch3].sendMessage(SOUT_MOVE_TO_FL_N, new Integer(getArrivalFloor() + 2), this);
}
else {
  port[ElevatorDispatch3].sendMessage(SOUT_MOVE_TO_FL_N, new Integer(getArrivalFloor()), this);
}
			break;
		case 28451939: // Hoist StateMachine_Hoist 
deleteDestination( getArrivalFloor() );
port[PushbuttonMessages0].sendMessage(SOUT_REQUEST_SATISFIED, null, this, getArrivalFloor());
port[ElevatorDispatch2].sendMessage(SIN_IS_AT, new Integer(getArrivalFloor()), this);
port[DoorHoistControl4].sendMessage(SOUT_UNLOCK_DOOR, null, this);
if ( getCurrentDirection() == udd_UP ) {
  setCycleNoDest(1);
}
else {
  setCycleNoDest(2);
}
			break;
		case 9841865: // Hoist StateMachine_Hoist SIN_REQUESTED
println("SIN_REQUESTED received at " + getName() + " on port " + msg.getSender().getName() + " state: Moving");
insertDestination( msg.getIndex() ); // floor
			break;
		case 48421973: // Hoist StateMachine_Hoist SIN_REQUESTED
//println("SIN_REQUESTED received at " + getName() + " on port " + msg.getSender().getName());
int floor1 = msg.getIndex();
if (floor1 == getArrivalFloor()) {
  port[PushbuttonMessages0].sendMessage(SOUT_REQUEST_SATISFIED, null, this, getArrivalFloor());
}
else {
  insertDestination( floor1 );
}
			break;
		case 51191978: // Hoist StateMachine_Hoist SOUT_MOVE_TO_FL_N
insertDestination( ((Integer)msg.getData()).intValue() ); // floor
			break;
		case 83791983: // Hoist StateMachine_Hoist 

			break;
		case 9481597: // Button StateMachine_Button SIN_REQUESTED
//println("Button received SIN_REQUESTED " + getName());
msg.getSender().sendMessage(SOUT_ACK, null, this);
port[PushbuttonMessages1].sendMessage(SIN_REQUESTED, null, this);
			break;
		case 9791612: // Button StateMachine_Button SOUT_REQUEST_SATISFIED
port[PushbuttonMessages0].sendMessage(SOUT_REQUEST_SATISFIED, null, this);
			break;
		case 676215: // Button StateMachine_Button SIN_REQUESTED
// ignore. This is a duplicate button press.
			break;
		case 6385199: // Button StateMachine_Button SOUT_REQUEST_SATISFIED
// ignore
			break;
		case 89211499: // Dispatcher StateMachine_Dispatcher 
// initial dispatchDestination array
for (int iDisp = 0; iDisp < dispatchDestination.length; iDisp++) {
  dispatchDestination[iDisp] = des_AVAILABLE;
}
			break;
		case 791894: // Dispatcher StateMachine_Dispatcher SIN_IS_READY_DEST
//println("SIN_IS_READY_DEST received by Dispatcher from " + msg.getSender().getName() + " elevatorDispatchD[" + msg.getIndex() + "]");
int currentDirection;
int e = msg.getIndex();
int combo3 = ((Integer)msg.getData()).intValue();
int currentFloor = combo3 >> 8;
int destinationFloor = combo3 & 0xFF;
if (destinationFloor > currentFloor) {
  currentDirection = udd_UP;
}
else {
  currentDirection = udd_DOWN;
}
int newDest = selectDispatchDestination( currentFloor, destinationFloor, currentDirection );
if (newDest != NoDestinationFound) {
  updateDestination( newDest, currentDirection );
  port[ElevatorDispatch2].sendMessage(SOUT_MOVE_TO_FL_N, new Integer(newDest), this, e);
}
			break;
		case 8971897: // Dispatcher StateMachine_Dispatcher SIN_IS_READY_NO_DEST
//println("SIN_IS_READY_NO_DEST received by Dispatcher from " + msg.getSender().getName() + " elevatorDispatchD[" + msg.getIndex() + "]");
int currentDirection4;
int newDest4 = NoDestinationFound;
int e4 = msg.getIndex();
int combo4 = ((Integer)msg.getData()).intValue();
int currentFloor4 = combo4 >> 8;
int destinationFloor4 = combo4 & 0xFF;
if (destinationFloor4 > currentFloor4) {
  currentDirection4 = udd_UP;
}
else {
  currentDirection4 = udd_DOWN;
}
if (currentFloor4 == destinationFloor4) {
  if (isDestination(currentFloor4, udd_UP)) {
    currentDirection4 = udd_UP;
    newDest4 = currentFloor4;
  }
  else if (isDestination(currentFloor4, udd_DOWN)) {
    currentDirection4 = udd_DOWN;
    newDest4 = currentFloor4;
  }
}
else {
  newDest4 = selectDispatchDestination( currentFloor4, destinationFloor4, currentDirection4 );
}
if (newDest4 != NoDestinationFound) {
  updateDestination( newDest4, currentDirection4 );
  port[ElevatorDispatch2].sendMessage(SOUT_MOVE_TO_FL_N, new Integer(newDest4), this, e4);
}
			break;
		case 8471911: // Dispatcher StateMachine_Dispatcher SIN_IS_AT
//println("SIN_IS_AT received by Dispatcher from " + msg.getSender().getName() + " elevatorDispatchD[" + msg.getIndex() + "]");
int floor5 = ((Integer)msg.getData()).intValue();
if (isDestination(floor5, udd_UP)) {
  deleteDestination(floor5, udd_UP);
  port[PushbuttonMessages0].sendMessage(SOUT_REQUEST_SATISFIED, null, this, floor5);
}
else if (isDestination(floor5, udd_DOWN)) {
  deleteDestination(floor5, udd_DOWN);
  port[PushbuttonMessages1].sendMessage(SOUT_REQUEST_SATISFIED, null, this, floor5);
}
			break;
		case 6681888: // Dispatcher StateMachine_Dispatcher SIN_REQUESTED
//println("SIN_REQUESTED received by Dispatcher from " + msg.getSender().getName() + " callUp[" + msg.getIndex() + "]");
insertDestination( msg.getIndex(), udd_UP ); // floor
			break;
		case 1731899: // Dispatcher StateMachine_Dispatcher SIN_REQUESTED
//println("SIN_REQUESTED received by Dispatcher from " + msg.getSender().getName() + " callDown[" + msg.getIndex() + "]");
insertDestination( msg.getIndex(), udd_DOWN );
			break;
		case 7111951: // TickGenerator StateMachine_TickGenerator 
// Set timer to go off after 50 ms.
println("TickGenerator is ticking ...");
xhTime = new XholonTime();
xhTime.timeoutRepeat(this, null, 0, ((Application)getApp()).getTimeStepInterval() * NUM_TIMESTEPS_PER_TICK);
			break;
		case 7431962: // TickGenerator StateMachine_TickGenerator 
port[Ticks0].sendMessage(SOUT_TICK, null, this);

			break;
		case 87781429: // UserJTree OtherScenario 
switch(testScenario) {
case 1:
  // press bUp:CallButton on floor 1
  port[PushbuttonMessages0].sendMessage(SIN_REQUESTED, null, this, 0);
  break;
case 2:
  // press bDown:CallButton on floor 8
  port[PushbuttonMessages4].sendMessage(SIN_REQUESTED, null, this, 7);
  break;
case 3:
  // press bFloor:FloorSelectionButton 2 in ElevatorPanel of Elevator 1
  port[PushbuttonMessages5].sendMessage(SIN_REQUESTED, null, this, 1);
  break;
case 4:
  // press iFloor:Indicator in ElevatorPanel of Elevator 1
  // signals not yet defined for this port
  port[LocationInfo1].sendMessage(org.primordion.xholon.base.ISignal.SIGNAL_DUMMY, null, this, 1);
  break;
case 5:
case 6:
case 7:
  // manipulate the door in Elevator 1
  port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, 0);
  port[DoorControl3].sendMessage(SIN_DOOR_CLOSED, null, this, 0);
  port[PushbuttonMessages6].sendMessage(SIN_REQUESTED, null, this, 0);
  port[DoorControl3].sendMessage(SIN_DOOR_OPENED, null, this, 0);
  break;
case 8:
  // tell hoist that the elevator is at floor 5
  port[ElevatorDispatch2].sendMessage(SIN_IS_AT, new Integer(5), this, 0);
  break;
default:
  break;
} // end switch
			break;
		case 465139: // UserJTree Submachine_Scenario102 SOUT_CLOSE_DOOR
println("<--- close door");
msg.getSender().sendMessage(SIN_DOOR_CLOSED, null, this, msg.getIndex());
			break;
		case 853142: // UserJTree Submachine_Scenario102 SOUT_OPEN_DOOR
println("<--- open door");
msg.getSender().sendMessage(SIN_DOOR_OPENED, null, this, msg.getIndex());
			break;
		case 9971145: // UserJTree Submachine_Scenario102 SOUT_MOVE_TO_FL_N
println("<--- move to floor " + (Integer)msg.getData());
msg.getSender().sendMessage(SIN_IS_AT, (Integer)msg.getData(), this, msg.getIndex());
println("---> elevator " + (msg.getIndex()+1) + " has moved to floor " + msg.getData());
			break;
		case 99441966: // UserJTree Scenario102 
// for this scenario to work, all elevator doors should be closed
port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, 0);
port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, 1);
			break;
		case 17981369: // UserJTree Submachine_Scenario101 SOUT_ACK
println("<--- ack");
			break;
		case 74591463: // UserJTree Submachine_Scenario101 SOUT_MOVE_TO_FL_N
println("<--- move to floor " + (Integer)msg.getData());
msg.getSender().sendMessage(SIN_IS_AT, (Integer)msg.getData(), this, msg.getIndex());
println("---> elevator " + (msg.getIndex()+1) + " has moved to floor " + msg.getData());
			break;
		case 89151499: // UserJTree Submachine_Scenario101 SOUT_MOVE_TO_FL_N
println("<--- move to floor " + (Integer)msg.getData());
msg.getSender().sendMessage(SIN_IS_AT, (Integer)msg.getData(), this, msg.getIndex());
println("---> elevator " + (msg.getIndex()+1) + " has moved to floor " + msg.getData());
			break;
		case 71661529: // UserJTree Submachine_Scenario101 SOUT_REQUEST_SATISFIED
println("<--- floor request satisfied " + msg.getIndex());
// press button to go to floor 7
port[PushbuttonMessages5].sendMessage(SIN_REQUESTED, null, this, 6);
println("---> I want to go to floor 7");
// request that door be closed
port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, 0);
println("---> I am pressing the door close button");
			break;
		case 75991936: // UserJTree Submachine_Scenario101 SOUT_MOVE_TO_FL_N
println("<--- move to floor " + (Integer)msg.getData());
msg.getSender().sendMessage(SIN_IS_AT, (Integer)msg.getData(), this, msg.getIndex());
println("---> elevator " + (msg.getIndex()+1) + " has moved to floor " + msg.getData());
			break;
		case 99111443: // UserJTree Submachine_Scenario101 SOUT_CLOSE_DOOR
println("<--- close door");
msg.getSender().sendMessage(SIN_DOOR_CLOSED, null, this, msg.getIndex());
			break;
		case 19711459: // UserJTree Submachine_Scenario101 SOUT_OPEN_DOOR
println("<--- open door");
msg.getSender().sendMessage(SIN_DOOR_OPENED, null, this, msg.getIndex());
			break;
		case 9961345: // UserJTree Scenario101 
// for this scenario to work, all elevator doors should be closed
port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, 0);
port[PushbuttonMessages7].sendMessage(SIN_REQUESTED, null, this, 1);
// a person is on floor 5, requesting to go up
port[PushbuttonMessages0].sendMessage(SIN_REQUESTED, null, this, 4);
println("---> I am on floor 5, and would like to go up");

			break;
		case 21171377: // UserJTree Scenario101 SIGNAL_ANY
println("<-ANY- " + msg);
			break;
		case 189418: // UserJTree StateMachine_UserJTree 
// set roleName for each UserInject button
IXholon node = getFirstChild();
int buttonCount = 0;
while (node != null) {
  if (node.getXhcId() == UserInjectCE) {
    switch(buttonCount) {
    // PushbuttonMessages0 bFlUp
    case 0: node.setRoleName("f1_Up"); break;
    case 1: node.setRoleName("f2_Up"); break;
    case 2: node.setRoleName("f3_Up"); break;
    case 3: node.setRoleName("f4_Up"); break;
    case 4: node.setRoleName("f5_Up"); break;
    case 5: node.setRoleName("f6_Up"); break;
    case 6: node.setRoleName("f7_Up"); break;
    case 7: node.setRoleName("f8_Up"); break;
    // PushbuttonMessages4 bFlDown
    case 8: node.setRoleName("fl1_Down"); break;
    case 9: node.setRoleName("fl2_Down"); break;
    case 10: node.setRoleName("fl3_Down"); break;
    case 11: node.setRoleName("fl4_Down"); break;
    case 12: node.setRoleName("fl5_Down"); break;
    case 13: node.setRoleName("fl6_Down"); break;
    case 14: node.setRoleName("fl7_Down"); break;
    case 15: node.setRoleName("fl8_Down"); break;
    // PushbuttonMessages5 bFloor
    case 16: node.setRoleName("e1_f1_Select"); break;
    case 17: node.setRoleName("e1_f2_Select"); break;
    case 18: node.setRoleName("e1_f3_Select"); break;
    case 19: node.setRoleName("e1_f4_Select"); break;
    case 20: node.setRoleName("e1_f5_Select"); break;
    case 21: node.setRoleName("e1_f6_Select"); break;
    case 22: node.setRoleName("e1_f7_Select"); break;
    case 23: node.setRoleName("e1_f8_Select"); break;
    case 24: node.setRoleName("e2_f1_Select"); break;
    case 25: node.setRoleName("e2_f2_Select"); break;
    case 26: node.setRoleName("e2_f3_Select"); break;
    case 27: node.setRoleName("e2_f4_Select"); break;
    case 28: node.setRoleName("e2_f5_Select"); break;
    case 29: node.setRoleName("e2_f6_Select"); break;
    case 30: node.setRoleName("e2_f7_Select"); break;
    case 31: node.setRoleName("e2_f8_Select"); break;
    // LocationInfo1 iFloor
    case 32: node.setRoleName("e1_Location"); break;
    case 33: node.setRoleName("e2_Location"); break;
    // PushbuttonMessages6 bOpen
    case 34: node.setRoleName("e1_door_Open"); break;
    case 35: node.setRoleName("e2_door_Open"); break;
    // PushbuttonMessages7 bClose
    case 36: node.setRoleName("e1_door_Close"); break;
    case 37: node.setRoleName("e2_door_Close"); break;
    // DoorControl3 doorControl
    case 38: node.setRoleName("e1_door_Opened"); break;
    case 39: node.setRoleName("e1_door_Closed"); break;
    case 40: node.setRoleName("e2_door_Opened"); break;
    case 41: node.setRoleName("e2_door_Closed"); break;
    // ElevatorDispatch2 elevatorDispatch
    case 42: node.setRoleName("e1_hoist_IsAt_5"); break;
    case 43: node.setRoleName("e1_hoist_IsAt_6"); break;
    case 44: node.setRoleName("e2_hoist_IsReadyNoDest"); break;
    case 45: node.setRoleName("e2_hoist_IsReadyDest"); break;
    default: node.setRoleName("unnamed"); break;
    }
    buttonCount++;
  }
  node = node.getNextSibling();
}

			break;
		case 19541938: // entry Open
println("+--+ Door entry:Open " + getName());
			break;
		case 39431949: // entry Closing
println("+--+ Door entry:Closing " + getName());
			break;
		case 51991945: // entry Opening
println("+--+ Door entry:Opening " + getName());
			break;
		case 87991943: // entry Closed
println("+--+ Door entry:Closed " + getName());
			break;
		case 2881951: // entry Locked
println("+--+ Door entry:Locked " + getName());
			break;
		case 84691431: // entry Idle
println("+--+ Hoist entry:Idle " + getName());
			break;
		case 79791428: // entry Moving
println("+--+ Hoist entry:Moving " + getName());
			break;
		case 5661426: // entry MoveRequested
println("+--+ Hoist entry:MoveRequested " + getName());
			break;
	default:
		System.out.println("XhElevator: performActivity() unknown Activity " + activityId);
		break;
	}
}

public boolean performGuard(int activityId, IMessage msg)
{
	switch (activityId) {
		case 6731592: // Choice1
			return lockRequested == true;
		case 2141594: // Choice2
			return lockRequested == false;
		case 85971929: // CNPT_OUTGOING1
			return msg.getSender() == port[PushbuttonMessages1];
		case 89781927: // CNPT_OUTGOING1
			return msg.getSender() == port[PushbuttonMessages0];
		case 58431923: // CNPT_OUTGOING1
			return msg.getSender() == port[PushbuttonMessages1];
		case 96391925: // CNPT_OUTGOING2
			return msg.getSender() == port[PushbuttonMessages0];
		case 69591963: // CNPT_OUTGOING5
			return msg.getSender() == port[PushbuttonMessages0];
		case 7971965: // CNPT_OUTGOING6
			return msg.getSender() == port[PushbuttonMessages1];
		case 61461429: // Choice2
			return true; // else
		case 1971869: // Choice1
			return destinationFloor != NoDestinationFound;
		case 5571873: // Choice1
			return destinationFloor == arrivalFloor;
		case 5641879: // Choice2
			return true; // else
		case 381875: // Choice2
			return true; // else
		case 6611877: // Choice1
			return isDestination(arrivalFloor);
		case 423419: // CNPT_OUTGOING1
			return msg.getSender() == port[PushbuttonMessages0];
		case 868113: // CNPT_OUTGOING2
			return msg.getSender() == port[PushbuttonMessages1];
		case 6391414: // CNPT_OUTGOING1
			return msg.getSender() == port[PushbuttonMessages0];
		case 58631522: // CNPT_OUTGOING2
			return msg.getSender() == port[PushbuttonMessages0];
		case 93821329: // sc1
			return getTestScenario() == 101;
		case 97821373: // sc2
			return getTestScenario() == 102;
		case 1251396: // sc3
			return getTestScenario() == 103;
		case 87941499: // sc4
			return true; // else
	default:
		System.out.println("XhElevator: performGuard() unknown Activity " + activityId);
		return false;
	}
}

public Object handleNodeSelection()
{
	switch(xhc.getId()) {
	
	case FloorSelectionButtonCE:
	case DoorControlButtonCE:
	case CallButtonCE:
	{
	  if (userJTree == null) {
	    userJTree = getApp().getRoot().getFirstChild();
	  }
	  if (userJTree != null) {
	    this.sendMessage(SIN_REQUESTED, null, userJTree);
	  }
	}
	
	case UserInjectCE:
	{
// get index of this instance of UserInject
IXholon node = getParentNode().getFirstChild();
int buttonCount = 0;
while (node != null) {
  if (node.getXhcId() == UserInjectCE) {
    if (node == this) {
      selectedInjectButton = buttonCount;
      break;
    }
    buttonCount++;
  }
  node = node.getNextSibling();
}
return "The " + roleName + " button has been selected.";
	}
	default:
		return toString();
	}
}

public String toString() {
	String outStr = getName();
	if ((port != null) && (port.length > 0)) {
		outStr += " [";
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				outStr += " port:" + port[i].getName();
			}
		}
		outStr += "]";
	}
	if (getVal() != 0.0) {
		outStr += " val:" + getVal();
	}
	return outStr;
}

}
