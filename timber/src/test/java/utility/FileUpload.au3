#cs ----------------------------------------------------------------------------

 AutoIt Version: 3.3.14.5
 Author:         myName

 Script Function:
	Template AutoIt script.

#ce ----------------------------------------------------------------------------

; Script Start - Add your code below here

ControlFocus("Open","","Edit1")
ControlSetText("Open","", "Edit1", $CmdLine[1]) 
ControlClick("Open","","Button1")
