/*
Jens's Most Simple Model (JMSM)
Plant
see email: March 29, 2021

There is an environment, and a plant is located at a particular place in this environment.
The plant has a height that changes over time.
The plant grows (increases in height) at a particular rate - 1 cm per day.

Ken - March 30 2021
I will try out various Xholon/Math/FP-like implementations, using modern JavaScript.
*/

// my JS template
((title) => {console.log(title)})("TODO")
// or
((title) => {
  console.log(title)
})("TODO")

// --------------------------------------------------------------------------------------------------
// Ken's version 1 (KV1), from Jen's II.
// TODO maybe use arrays of Pair to specify mappings
//  - use Pair from Joy of JS book  Pair(indexCSH, indexIH)  gla01.js
//    - and as in Bigraph notation
//  - and use simple map from IH to IH_LABELS
((title) => {
  console.log(title)
  
  // labels are optional for the computer, but useful for us humans
  // these line up with the corresponding items (indexes) in the IH array
  // these are type labels/names
  const IH_LABELS = [
    "Inheritance_Tree",
    "Observer",
    "PhysicalSystem",
    "Environment",
    "Field",
    "Entity",
    "Plant"
  ]
  
  // Inheritance_Tree, types
  const IH = [
    0, // Inheritance_Tree
    1, // Observer
    2, // PhysicalSystem
    3, // Environment,
    4, // Field
    5, // Entity
    6  // Plant
  ]
  
  const OP = [
    0, // Object_Properties
    1, // PhysicalSystem
    2, // time
    3, // Field
    4, // shape
    5, // Plant
    6, // location
    7  // height
  ]
  
  // Composition_Tree
  const CSH = [
    0, // Composition_Tree  note: this is not in the IH
    1, // Observer
    2, // Environment,
    3, // Field
    4  // Plant
  ]
  
  
})("JMSM KV1")


// --------------------------------------------------------------------------------------------------
// test of Xholon module from existing ~/gwtspace/Xholon/Xholon/module/moduleTest01.xml
// this works
((title) => {
  console.log(title)
  
  const obj = `
<?xml version="1.0" encoding="UTF-8"?>
<!--
Test of XholonModule.
Copy and paste, or drag, this entire text into any running Xholon app.
-->
<XholonModule>
  <XholonMap>
  
    <Attribute_String roleName="ih"><![CDATA[
<_-.XholonClass>
  <Ten/>
  <Eleven/>
  <Twelve/>
</_-.XholonClass>
    ]]></Attribute_String>
    
    <Attribute_String roleName="cd"><![CDATA[
<xholonClassDetails>
  <Ten><Color>red</Color></Ten>
  <Eleven><Color>orange</Color></Eleven>
  <Twelve><Color>yellow</Color></Twelve>
</xholonClassDetails>
    ]]></Attribute_String>
    
    <Attribute_String roleName="csh"><![CDATA[
<_-.csh>
  <Ten roleName="Десять" energy="10"/>
  <Eleven roleName="Одиннадцать" energy="11"/>
  <Twelve roleName="Двенадцать" energy="12"/>
</_-.csh>
    ]]></Attribute_String>
    
  </XholonMap>
</XholonModule>
  `
  console.log(obj)
})("Xholon module from existing ~/gwtspace/Xholon/Xholon/module/moduleTest01.xml")


// --------------------------------------------------------------------------------------------------
// Ken's Xholon version, using a Xholon module
// this works
((title) => {
  console.log(title)
  
  const x = 1
  const y = 1
  const t = 0
  const h = 0
  const shape = "square,10,10"
  const energy = 1234
  
  const lt = "<"
  const gt = ">"
  
  const obj = 
`<XholonModule>
  <XholonMap>
  
    <Attribute_String roleName="ih">${lt}![CDATA[
<_-.XholonClass>
  <TheSystem/>
  <Observer/>
  <Environment>
    <Field/>
  </Environment>
  <Entity>
    <Plant superClass="script"/>
  </Entity>
</_-.XholonClass>
    ]]${gt}</Attribute_String>
    
    <Attribute_String roleName="cd">${lt}![CDATA[
<xholonClassDetails>
  <TheSystem><Color>white</Color></TheSystem>
  <Environment><Color>pink</Color></Environment>
  <Field><Color>orange</Color></Field>
  
  <Plant>
    <Color>green</Color>
    <DefaultContent>
var me = this, beh = {
  postConfigure: () => {beh.init(); me.println(me.name() + " " + me.height)},
  act: () => {me.height = beh.grow(me.height); me.println(me.name() + " " + me.height + " acting ...")},
  init: () => me.height = Number(me.height),
  grow: (h) => h + 1
}
//# sourceURL=Plant.js
    </DefaultContent>
  </Plant>
  
</xholonClassDetails>
    ]]${gt}</Attribute_String>
    
    <Attribute_String roleName="csh">${lt}![CDATA[
<_-.csh>
  <TheSystem time="${t}">
    <Observer/>
    <Environment>
      <Field shape="${shape}">
        <Plant height="${h}" x="${x}" y="${y}" energy="${energy}"/>
      </Field>
    </Environment>
  </TheSystem>
</_-.csh>
    ]]${gt}</Attribute_String>
    
  </XholonMap>
</XholonModule>`
  console.log(obj)
  console.log(this)
  console.log(this.parent())
  this.parent().append(obj)
})("Xholon module")

// alien future ef
/*
((0000000[XholonClass](000000a[Plant]0000001[Chameleon]0000002[Quantity]0000005[TheSystem]0000006[Observer]0000007[Environment](0000008[Field])0000009[Entity]))(1000030>0000005([time=0]>00f4adf)(1000031>00000061000032>0000007(1000033>0000008([shape=square,10,10]>00f4ae3)(1000034>000000a)))))
*/

// JSON external format (Xholon ef)
/*
{
  "TheSystem": {
    "time": "0",
    "Observer": {},
    "Environment": {
      "Field": {
        "shape": "square,10,10",
        "Plant": {
          "implName": "org.primordion.xholon.base.Behavior_gwtjs",
          "Text": "var me = this, beh = {\n  postConfigure: () => {me.height = Number(me.height); me.println(me.name() + \" \" + me.height)},\n  act: () => {me.height = beh.grow(me.height); me.println(me.name() + \" \" + me.height + \" acting ...\")},\n  grow: (h) => h + 1\n}\n//# sourceURL=Plant.js"
        }
      }
    }
  }
}
*/

// SExpression ef
/*
(
 TheSystem (
  Observer
  Environment (
   Field (
    Plant
   )
  )
 )
)
*/

// Newick
/*
(Observer,
((Plant)Field)Environment)TheSystem;
*/

// --------------------------------------------------------------------------------------------------
// Ken's version 2 (KV2)
/* TODO
 - PhysicalSystem is the root of the CSH; should be called TheSystem
 - Inheritance_Tree can stay as is; in Xholon this is called XholonClass
 - instead of integers, use floating-point numbers, where the decimal part specifies the level of nesting (the hierarchy)
  - ex: 0.0 is the root
        1.1 is a child of the most recent previous node with a nesting of 0
  - OR use -1, 0, 1 to specify whether current level should be -1(up) 0(same) or 1(down)
    OR use 1, 2, 3 and first subtract 2 to get -1, 0, 1
 - could also use decimal part in CSH to specify its IH index
  - ex: 1.3005
    which means: id=1, nesting=+1, IH=[5]
 - if an item is a property, then it has a single child item that consists of the value
   - the child is a pure value and does not need to specify a nesting or IH index
   ex: 6.4007, // height
       123.45 // value of the height property
   - ports could be spcified in a similar way
 
 - 
*/
((title) => {
  console.log(title)
})("JMSM KV2")

// MathML
/*
see: https://www.w3.org/TR/2014/REC-MathML3-20140410/
see: 127.0.0.1:8080/war/Xholon.html?app=MathmlTest1 workbook&src=lstr&gui=clsc
     http://127.0.0.1:8080/war/wb/editwb.html?app=942a6d487b5df1172b13f11fb840f820&src=gist
see: https://github.com/kenwebb/Xholon/tree/master/Xholon/war/config/mech
     https://github.com/kenwebb/Xholon/tree/master/Xholon/src/org/primordion/xholon/mech/mathml
*/

// III B
/*
x=1
y=1
t=0
h = g(t+1) = g(t) + 1
g(0) = 1

use a memoized function to store and return height h
 - height should be based on relative time, rather than an absolute time
 - this could should be able to be dragged into a running Xholon app where the number of elapsed timesteps could be anything
 - the function could store and increment the time; result would be interpreted as a height
*/
((title) => {
  console.log(title)
  
  const x = 1
  const y = 1
  const t = 0
  
  // version 1
  //const g = t => t === 0 ? 1 : t + 1
  // height h is strictly a function of time t, and NOT of the previous value for h
  //const h = g(0)
  //console.log(h)
  
  // version 2
  const _g = (t0) => {
    let h = t0 + 1; // h = g(0) = 1
    return () => h++; // h = g(t+1) = g(t) + 1
  }
  const g = _g(t)
  // time is implied and does NOT need to be explicity specified; current time is simply the number of times that g() has been called
  console.log("h = " + g())
  console.log("h = " + g())
  console.log("h = " + g())

})("III B")


