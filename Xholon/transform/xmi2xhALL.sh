#!/bin/bash
# Xholon Runtime Framework - executes event-driven & dynamic applications
# Copyright (C) 2007 Ken Webb
#
# This library is free software; you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License
# as published by the Free Software Foundation; either version 2.1
# of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA

# Transform all UML/SysML XMI files to Xholon format.
# This is a convenience utility that can be used to regenerate the sample applications.
# Ken Webb  www.primordion.com/Xholon
# xmi2xh
# September 27, 2007

# xmi2xh transformType xmiFileName [rootXholon] [shouldCompile]

# ArgoUML models
cd ArgoUML
./xmi2xh all HelloWorldTutorial.zargo HelloWorldSystem false

# MagicDraw models
cd ../MagicDraw
./xmi2xh all __XholonTemplate__.mdzip TheSystem true
./xmi2xh all Elevator.mdzip AnElevatorSystem true
./xmi2xh all Fsm06ex1_Fsm.mdzip Membrane1 true
./xmi2xh all HelloWorldTutorial_multiWorld.mdzip HelloWorldSystem true
./xmi2xh all HelloWorldTutorial_plus.mdzip HelloWorldSystem true
./xmi2xh all HelloWorldTutorial_universe.mdzip HelloWorldSystem true
./xmi2xh all HelloWorldTutorial.mdzip HelloWorldSystem true
./xmi2xh all ProvidedRequiredTest.mdzip ClassA true
./xmi2xh all Rcs_GP_FSM_Grid.mdzip GPaseSystemWithGrid true
#./xmi2xh jg Rcs_GP_FSM_Grid.mdzip GPaseSystemWithGrid true
./xmi2xh all Rcs_GP_FSM.mdzip GPaseSystem true
./xmi2xh all Rcs_GP_MM_NoSymbols.mdzip GPaseSystem true
./xmi2xh all Rcs_GP_MM.mdzip GPaseSystem true
./xmi2xh all StopWatch.mdzip StopWatchSystem true
./xmi2xh all TestFsm.mdzip TestFsmSystem true
./xmi2xh all TestFsmForkJoin.mdzip TestFsmSystem true
./xmi2xh all TestFsmHistory.mdzip TestFsmSystem true
./xmi2xh all TestFsmJunction.mdzip TestFsmSystem true
./xmi2xh all TestFsmOrthogonal.mdzip TestFsmSystem true

# Poseidon models
cd ../Poseidon
./xmi2xh all Watch.zuml WatchUserSystem true

# Topcased UML models
cd ../Topcased
./xmi2xh all Elevator.uml AnElevatorSystem true
./xmi2xh all HelloWorldTutorial_multiWorld.uml HelloWorldSystem true
./xmi2xh all HelloWorldTutorial.uml HelloWorldSystem true
./xmi2xh all TestFsm.uml TestFsmSystem true

# Topcased SysML models
cd ../TopcasedSysML
./xmi2xh all HelloWorldTutorialSysML.sysml HelloWorldSystem true

cd ..
