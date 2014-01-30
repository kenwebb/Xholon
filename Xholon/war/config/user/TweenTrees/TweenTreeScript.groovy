/* TweenTreeScript.groovy
   This is a Java/Groovy version of the code in XhTweenTrees.java.
*/

<Rrr implName="lang:groovy:inline:"><![CDATA[
import org.primordion.xholon.script.XholonScript;
import org.primordion.xholon.base.IXholon;
import org.primordion.xholon.base.XholonWithPorts;
class Rrr extends XholonScript
{
	public void postConfigure()
	{
		IXholon bbb = getXPath().evaluate("ancestor::TheSystem/Aaa/Bbb", this);
		IXholon bbbTarget = bbb.getPort(0);
		IXholon sss = getXPath().evaluate("./Sss", this);
		IXholon ttt = getXPath().evaluate("./Ttt", this);
		IXholon uuu = getXPath().evaluate("./Uuu", this);
		IXholon vvv = getXPath().evaluate("./Vvv", this);
		((XholonWithPorts)bbb).setPort(0, sss);
		((XholonWithPorts)sss).setPort(0, ttt);
		((XholonWithPorts)sss).setPort(1, uuu);
		((XholonWithPorts)ttt).setPort(0, vvv);
		((XholonWithPorts)uuu).setPort(0, vvv);
		((XholonWithPorts)vvv).setPort(0, bbbTarget);
		super.postConfigure();
		removeChild();
	}
}
new Rrr();
]]>

<Sss implName="lang:groovy:inline:"><![CDATA[
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IMessage;
class Sss extends XholonWithPorts
{
	public void processReceivedMessage(IMessage msg)
	{
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				port[i].sendMessage(msg.getSignal(), msg.getData() + " _" + getName("^^C^^^"), this);
			}
		}
	}
}
new Sss();
]]>
</Sss>

<Ttt implName="lang:groovy:inline:"><![CDATA[
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IMessage;
class Ttt extends XholonWithPorts
{
	public void processReceivedMessage(IMessage msg)
	{
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				port[i].sendMessage(msg.getSignal(), msg.getData() + " _" + getName("^^C^^^"), this);
			}
		}
	}
}
new Ttt();
]]>
</Ttt>

<Uuu implName="lang:groovy:inline:"><![CDATA[
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IMessage;
class Uuu extends XholonWithPorts
{
	public void processReceivedMessage(IMessage msg)
	{
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				port[i].sendMessage(msg.getSignal(), msg.getData() + " _" + getName("^^C^^^"), this);
			}
		}
	}
}
new Uuu();
]]>
</Uuu>

<Vvv implName="lang:groovy:inline:"><![CDATA[
import org.primordion.xholon.base.XholonWithPorts;
import org.primordion.xholon.base.IMessage;
class Vvv extends XholonWithPorts
{
	public void processReceivedMessage(IMessage msg)
	{
		for (int i = 0; i < port.length; i++) {
			if (port[i] != null) {
				port[i].sendMessage(msg.getSignal(), msg.getData() + " _" + getName("^^C^^^"), this);
			}
		}
	}
}
new Vvv();
]]>
</Vvv>

</Rrr>
