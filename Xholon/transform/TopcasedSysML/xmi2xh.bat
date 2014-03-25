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

:: Transform TOPCASED Eclipse SysML files to Xholon format.
:: Ken Webb  www.primordion.com/Xholon
:: xmi2xh.bat
:: September 26, 2007

@echo OFF
java -classpath ..\..\bin;..\..\lib\Xholon.jar;..\..\lib\ecj.jar org.primordion.xholon.io.Xmi2Xholon %1 %2 %3 %4
