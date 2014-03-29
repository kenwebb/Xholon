To use MathML in an app where it's not yet included, such as Chameleon
Nov 28 2011
----------------------------------------------------------------------
Note:
This works on the desktop, but the config and .class files are not included in XholonJnlp.jar

(1) Open a Xholon Console on IH > XholonMechanism, and paste in:
<_-.forest xmlns:xi="http://www.w3.org/2001/XInclude">
  <xi:include href="./config/mech/mathml/MathMLEntity.xml"/>
  <xi:include href="./config/mech/mathml/MathML_ClassDetails.xml"/>
</_-.forest>

(2) Open a Xholon Console on Chameleon, and paste in something like:
<math xmlns="http://www.w3.org/1998/Math/MathML">
  <apply>
    <plus/>
    <cn>5.1</cn>
    <cn>2.0f</cn>
    <cn>3</cn>
    <apply>
      <times/>
      <cn>8</cn>
      <cn>9</cn>
      <apply>
        <abs/>
        <cn>-2</cn>
      </apply>
    </apply>
  </apply>
</math>