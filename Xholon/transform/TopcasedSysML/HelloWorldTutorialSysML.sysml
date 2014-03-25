<?xml version="1.0" encoding="UTF-8"?>
<sysML:ModelSYSML xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore" xmlns:sysML="http://www.topcased.org/1.0/sysML" xmlns:uml="http://www.eclipse.org/uml2/2.1.0/UML" xmi:id="idModel" name="HelloWorldTutorialSysML">
  <eAnnotations xmi:type="ecore:EAnnotation" xmi:id="_EJU3QExjEdyddLXXppivPg" source="The model includes an EAnnotation."/>
  <ownedComment xmi:type="uml:Comment" xmi:id="_7h1dAExiEdyddLXXppivPg">
    <body>The HelloWorldTutorialSysML is a simple tutorial, modeled using Topcased SysML. (comment)</body>
  </ownedComment>
  <packagedElement xmi:type="sysML:PackageSYSML" xmi:id="idPackage" name="HelloWorldTutorialSysML">
    <packagedElement xmi:type="sysML:Block" xmi:id="_Z7yvkEwXEdyDdISDSUDL6g" name="HelloWorldSystem">
      <generalization xmi:type="uml:Generalization" xmi:id="_kAtxIEwXEdyDdISDSUDL6g" general="_Rn8fgEwWEdyDdISDSUDL6g"/>
      <ownedAttribute xmi:type="sysML:BlockProperty" xmi:id="_pK8BAEwcEdyDdISDSUDL6g" name="hello" type="_b1YucEwXEdyDdISDSUDL6g" aggregation="composite"/>
      <ownedAttribute xmi:type="sysML:BlockProperty" xmi:id="_xDyMkEwcEdyDdISDSUDL6g" name="world" type="_dsTMwEwXEdyDdISDSUDL6g" aggregation="composite"/>
    </packagedElement>
    <packagedElement xmi:type="sysML:Block" xmi:id="_b1YucEwXEdyDdISDSUDL6g" name="Hello">
      <ownedComment xmi:type="uml:Comment" xmi:id="_hLn-QExiEdyddLXXppivPg">
        <body>The Hello block includes a state machine, which it uses to enter into a dialog with World.</body>
      </ownedComment>
      <generalization xmi:type="uml:Generalization" xmi:id="_lqz7cEwXEdyDdISDSUDL6g" general="_Rn8fgEwWEdyDdISDSUDL6g"/>
      <ownedAttribute xmi:type="uml:Port" xmi:id="_OdWuoEwdEdyDdISDSUDL6g" name="ph" type="_x3bh8EwaEdyDdISDSUDL6g" aggregation="composite" isBehavior="true" isService="false"/>
      <ownedConnector xmi:type="uml:Connector" xmi:id="_QEhssExdEdyddLXXppivPg" name="">
        <end xmi:type="uml:ConnectorEnd" xmi:id="_QEhssUxdEdyddLXXppivPg" role="_OdWuoEwdEdyDdISDSUDL6g"/>
        <end xmi:type="uml:ConnectorEnd" xmi:id="_QEhsskxdEdyddLXXppivPg" role="_eqBxkEwdEdyDdISDSUDL6g"/>
      </ownedConnector>
      <ownedBehavior xmi:type="uml:StateMachine" xmi:id="_pgmdEEweEdyDdISDSUDL6g" name="StateMachine_Hello">
        <region xmi:type="uml:Region" xmi:id="_zRtKwEweEdyDdISDSUDL6g" name="Region1">
          <ownedComment xmi:type="uml:Comment" xmi:id="_1F8NEExhEdyddLXXppivPg">
            <body>Hello starts the dialog between Hello and World.</body>
          </ownedComment>
          <subvertex xmi:type="uml:State" xmi:id="_06wzQEweEdyDdISDSUDL6g" name="Top">
            <region xmi:type="uml:Region" xmi:id="_06wzQUweEdyDdISDSUDL6g" name="Region1">
              <subvertex xmi:type="uml:Pseudostate" xmi:id="_561a4EweEdyDdISDSUDL6g" name="Pseudostate1"/>
              <subvertex xmi:type="uml:State" xmi:id="_8PGjkEweEdyDdISDSUDL6g" name="SayingHello"/>
              <transition xmi:type="uml:Transition" xmi:id="_DazVQEwfEdyDdISDSUDL6g" name="" target="_8PGjkEweEdyDdISDSUDL6g" source="_561a4EweEdyDdISDSUDL6g">
                <effect xmi:type="uml:OpaqueBehavior" xmi:id="_kL2JcEwfEdyDdISDSUDL6g">
                  <body>print(&quot;{Hello &quot;);&#xA;port[Partner].sendMessage(SIGNAL_ONE, null, this);</body>
                </effect>
              </transition>
              <transition xmi:type="uml:Transition" xmi:id="_F6PzMEwfEdyDdISDSUDL6g" name="CNPT_OUTGOING1" target="_8PGjkEweEdyDdISDSUDL6g" source="_8PGjkEweEdyDdISDSUDL6g">
                <effect xmi:type="uml:OpaqueBehavior" xmi:id="_M2L1IEwgEdyDdISDSUDL6g">
                  <body>print(&quot;{Hello &quot;);&#xA;port[Partner].sendMessage(SIGNAL_ONE, null, this);</body>
                </effect>
                <trigger xmi:type="uml:Trigger" xmi:id="_98W6IEwfEdyDdISDSUDL6g" event="_YmvLsEwYEdyDdISDSUDL6g" port="_OdWuoEwdEdyDdISDSUDL6g"/>
              </transition>
            </region>
          </subvertex>
        </region>
      </ownedBehavior>
    </packagedElement>
    <packagedElement xmi:type="sysML:Block" xmi:id="_dsTMwEwXEdyDdISDSUDL6g" name="World">
      <generalization xmi:type="uml:Generalization" xmi:id="_l-LjYEwXEdyDdISDSUDL6g" general="_Rn8fgEwWEdyDdISDSUDL6g"/>
      <ownedAttribute xmi:type="uml:Port" xmi:id="_eqBxkEwdEdyDdISDSUDL6g" name="pw" type="_x3bh8EwaEdyDdISDSUDL6g" aggregation="composite" isBehavior="true"/>
      <ownedBehavior xmi:type="uml:StateMachine" xmi:id="_HW2CIEwhEdyDdISDSUDL6g" name="StateMachine_World">
        <region xmi:type="uml:Region" xmi:id="_OVIp8EwhEdyDdISDSUDL6g" name="Region1">
          <subvertex xmi:type="uml:State" xmi:id="_O8Lb0EwhEdyDdISDSUDL6g" name="Top">
            <region xmi:type="uml:Region" xmi:id="_O8Lb0UwhEdyDdISDSUDL6g" name="Region1">
              <subvertex xmi:type="uml:Pseudostate" xmi:id="_UlYgAEwhEdyDdISDSUDL6g" name="Pseudostate1"/>
              <subvertex xmi:type="uml:State" xmi:id="_VV9BUEwhEdyDdISDSUDL6g" name="SayingWorld"/>
              <transition xmi:type="uml:Transition" xmi:id="_ZD7u8EwhEdyDdISDSUDL6g" name="" target="_VV9BUEwhEdyDdISDSUDL6g" source="_UlYgAEwhEdyDdISDSUDL6g">
                <effect xmi:type="uml:OpaqueBehavior" xmi:id="_7Mq3kExdEdyddLXXppivPg" body=""/>
              </transition>
              <transition xmi:type="uml:Transition" xmi:id="_aPCQIEwhEdyDdISDSUDL6g" name="CNPT_OUTGOING1" target="_VV9BUEwhEdyDdISDSUDL6g" source="_VV9BUEwhEdyDdISDSUDL6g">
                <effect xmi:type="uml:OpaqueBehavior" xmi:id="_PoZU4ExeEdyddLXXppivPg">
                  <body>println(&quot;World}&quot;);&#xD;&#xA;port[Partner].sendMessage(SIGNAL_ONE, null, this);</body>
                </effect>
                <trigger xmi:type="uml:Trigger" xmi:id="_hos4oExeEdyddLXXppivPg" event="_YmvLsEwYEdyDdISDSUDL6g"/>
              </transition>
            </region>
          </subvertex>
        </region>
      </ownedBehavior>
    </packagedElement>
    <packagedElement xmi:type="uml:Signal" xmi:id="_yozb4EwXEdyDdISDSUDL6g" name="SIGNAL_ONE">
      <ownedAttribute xmi:type="uml:Property" xmi:id="_4WohYEwXEdyDdISDSUDL6g" isReadOnly="true">
        <defaultValue xmi:type="uml:LiteralString" xmi:id="_8vhNMEwXEdyDdISDSUDL6g" value="100"/>
      </ownedAttribute>
    </packagedElement>
    <packagedElement xmi:type="uml:SignalEvent" xmi:id="_YmvLsEwYEdyDdISDSUDL6g" name="SIGNAL_ONEse" signal="_yozb4EwXEdyDdISDSUDL6g"/>
    <packagedElement xmi:type="uml:Interface" xmi:id="_ev3QoEwZEdyDdISDSUDL6g" name="IHwIn">
      <ownedAttribute xmi:type="uml:Property" xmi:id="_legS4EwZEdyDdISDSUDL6g" name="" isStatic="true" type="_yozb4EwXEdyDdISDSUDL6g" isReadOnly="true"/>
    </packagedElement>
    <packagedElement xmi:type="uml:Interface" xmi:id="_BqJCMEwaEdyDdISDSUDL6g" name="IHwOut">
      <ownedAttribute xmi:type="uml:Property" xmi:id="_F6PlcEwaEdyDdISDSUDL6g" name="" isStatic="true" type="_yozb4EwXEdyDdISDSUDL6g" isReadOnly="true"/>
    </packagedElement>
    <packagedElement xmi:type="uml:Class" xmi:id="_x3bh8EwaEdyDdISDSUDL6g" name="Partner" clientDependency="_SveWAEwbEdyDdISDSUDL6g _eKOiEEwbEdyDdISDSUDL6g">
      <ownedAttribute xmi:type="uml:Property" xmi:id="_DjBZ8EwbEdyDdISDSUDL6g" isStatic="true" isReadOnly="true">
        <defaultValue xmi:type="uml:LiteralString" xmi:id="_FaOzMEwbEdyDdISDSUDL6g" value="0"/>
      </ownedAttribute>
      <interfaceRealization xmi:type="uml:InterfaceRealization" xmi:id="_SveWAEwbEdyDdISDSUDL6g" name="InterfaceRealization1" supplier="_ev3QoEwZEdyDdISDSUDL6g" client="_x3bh8EwaEdyDdISDSUDL6g" contract="_ev3QoEwZEdyDdISDSUDL6g"/>
    </packagedElement>
    <ownedRelationships xmi:type="uml:Usage" xmi:id="_eKOiEEwbEdyDdISDSUDL6g" name="Usage1" supplier="_BqJCMEwaEdyDdISDSUDL6g" client="_x3bh8EwaEdyDdISDSUDL6g"/>
  </packagedElement>
  <packagedElement xmi:type="sysML:Block" xmi:id="_Rn8fgEwWEdyDdISDSUDL6g" name="XholonClass" isAbstract="true"/>
</sysML:ModelSYSML>
