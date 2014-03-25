:: Xholon Runtime Framework - executes event-driven & dynamic applications
:: Copyright (C) 2007, 2008 Ken Webb
::
:: This library is free software; you can redistribute it and/or modify
:: it under the terms of the GNU Lesser General Public License
:: as published by the Free Software Foundation; either version 2.1
:: of the License, or (at your option) any later version.
::
:: This library is distributed in the hope that it will be useful, but
:: WITHOUT ANY WARRANTY; without even the implied warranty of
:: MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
:: Lesser General Public License for more details.
::
:: You should have received a copy of the GNU Lesser General Public
:: License along with this library; if not, write to the Free Software
:: Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA

:: Transform all UML/SysML XMI files to Xholon format.
:: This is a convenience utility that can be used to regenerate the sample applications.
:: Ken Webb  www.primordion.com/Xholon
:: xmi2xh.bat
:: September 27, 2007

@echo OFF

:: xmi2xh transformType xmiFileName [rootXholon] [shouldCompile]

:: ArgoUML models
cd ArgoUML
call xmi2xh all HelloWorldTutorial.zargo HelloWorldSystem true

:: MagicDraw models
cd ..\MagicDraw
call xmi2xh all __XholonTemplate__.mdzip TheSystem true
call xmi2xh all Elevator.mdzip AnElevatorSystem true
call xmi2xh all Fsm06ex1_Fsm.mdzip Membrane1 true
call xmi2xh all HelloWorldTutorial_multiWorld.mdzip HelloWorldSystem true
call xmi2xh all HelloWorldTutorial_plus.mdzip HelloWorldSystem true
call xmi2xh all HelloWorldTutorial_universe.mdzip HelloWorldSystem true
call xmi2xh all HelloWorldTutorial.mdzip HelloWorldSystem true
call xmi2xh all ProvidedRequiredTest.mdzip ClassA true
call xmi2xh all Rcs_GP_FSM_Grid.mdzip GPaseSystemWithGrid true
::call xmi2xh jg Rcs_GP_FSM_Grid.mdzip GPaseSystemWithGrid true
call xmi2xh all Rcs_GP_FSM.mdzip GPaseSystem true
call xmi2xh all Rcs_GP_MM_NoSymbols.mdzip GPaseSystem true
call xmi2xh all Rcs_GP_MM.mdzip GPaseSystem true
call xmi2xh all StopWatch.mdzip StopWatchSystem true
call xmi2xh all TestFsm.mdzip TestFsmSystem true
call xmi2xh all TestFsmForkJoin.mdzip TestFsmSystem true
call xmi2xh all TestFsmHistory.mdzip TestFsmSystem true
call xmi2xh all TestFsmJunction.mdzip TestFsmSystem true
call xmi2xh all TestFsmOrthogonal.mdzip TestFsmSystem true

:: Poseidon models
cd ..\Poseidon
call xmi2xh all Watch.zuml WatchUserSystem true

:: Topcased UML models
cd ..\Topcased
call xmi2xh all Elevator.uml AnElevatorSystem true
call xmi2xh all HelloWorldTutorial_multiWorld.uml HelloWorldSystem true
call xmi2xh all HelloWorldTutorial.uml HelloWorldSystem true
call xmi2xh all TestFsm.uml TestFsmSystem true

:: Topcased SysML models
cd ..\TopcasedSysML
call xmi2xh all HelloWorldTutorialSysML.sysml HelloWorldSystem true

cd ..
