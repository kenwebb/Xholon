Math Notation in Xholon
Ken Webb
December 6, 2019

Notes:
see: my notebook for more details
see: http://127.0.0.1:8888/wb/editwb.html?app=Saunders+Mac+Lane+-+Mathematics+Form+and+Function&src=lstr
abbreviations:
 IH Inheritance Hierarchy
 CD Class Details
 CSH Composite Structure Hierarchy
The name of a set contains two parts, separated by a comma:
 name of a JavaScript file, function, or class (ex: SetPointed01.js)
 optional user-specified local name (ex: ihplanet)

see: https://en.wikipedia.org/wiki/Pointed_set
"In mathematics, a pointed set (also based set or rooted set) is an ordered pair (X, x0) where X is a set and x0 is an element of X called the base point, also spelled basepoint."
my example would be:
({Planet,Mercury,Venus,Earth,Mars}, Planet)

see: https://ncatlab.org/nlab/show/pointed+set
A pointed set is a pointed object in Set, hence a set S equipped with a chosen element s of S.

optionally put "XhMath" at the start, or prefix the name of a set with "XhMath."

and/or I could have a Xholon param called "XhMath", which if it has a value and the value is true, then the XML parser would look for XhMath text nodes
 - lines of text that start with "{" or "(" or "XhMath" ???

# simplest example
XhMath.Set01 = {One,Two,Three,Four}
<_-.xhforest>
  <One/>
  <Two/>
  <Three/>
  <Four/>
</_-.xhforest>

# IH example, a subtree of XholonClass type names
# note: "Planet" may or may not already exist as the name of a XholonClass in the IH
XhMath.SetPointed01.ihplanet = {Planet,Mercury,Venus,Earth,Mars}
<Planet>
  <Mercury/>
  <Venus/>
  <Earth/>
  <Mars/>
</Planet>

XhMath
# CSH example, a subtree of Xholon nodes, where each node is of the specified XholonClass type
SetPointed01.cshplanets = {Planets,Mercury,Venus,Earth,Mars}
<Planets>
  <Mercury/>
  <Venus/>
  <Earth/>
  <Mars/>
</Planets>

<XhMath>
# this is a comment
SetPointed01.cshplanets = {Planets,Mercury,Venus,Earth,Mars}
</XhMath>

<XhMath roleName="SetPointed01.cshplanets">
# this is a comment
{Planets,Mercury,Venus,Earth,Mars}
</XhMath>

<XhMath roleName="SetPointed01">
# this is the more correct way of writing a pointed set using Math notation
({Planets,Mercury,Venus,Earth,Mars}, Planets)
</XhMath>

SetPointed02
<Planets>
  <XhMath roleName="SetPointed02">
# the base point "Planet" is the tag name of every other element in the set
({Planet,Mercury,Venus,Earth,Mars}, Planet)
  </XhMath>
</Planets>
becomes:
<Planets>
  <Planet roleName="Mercury"/>
  <Planet roleName="Venus"/>
  <Planet roleName="Earth"/>
  <Planet roleName="Mars"/>
</Planets>

# can I do this ?
XhMath.SetPointedCombo0102 = ({Planets,({Planet,Mercury,Venus,Earth,Mars}, Planet)}, Planets)

# CSH
# this example is quite complex
# ports, where all nodes are siblings
# in this example, "trail" is the name of a JavaScript port Array
XhMath.Mapping01.trail = {(A,B),(B,A),(B,C),(B,J),(C,D)}
OR
XhMath.Mapping01 = ({(Thing,trail),(A,B),(B,A),(B,C),(B,J),(C,D)}, (Thing,trail))
OR
XhMath.Mapping01.trail = ({Thing, (A,B),(B,A),(B,C),(B,J),(C,D)}, Thing)
could become:
<_-.xhforest>
  <Thing roleName="A">
    <port name="trail" index="0" connector="../*[@roleName='B']"/>
  </Thing>
  <Thing roleName="B">
    <port name="trail" index="0" connector="../*[@roleName='A']"/>
    <port name="trail" index="1" connector="../*[@roleName='C']"/>
    <port name="trail" index="2" connector="../*[@roleName='J']"/>
  </Thing>
  <Thing roleName="C">
    <port name="trail" index="0" connector="../*[@roleName='D']"/>
  </Thing>
</_-.xhforest>
Note: if each tagname is different, then it becomes even more complex
see: https://gist.github.com/kenwebb/fddceec2929ecb57236d86a9ceb7211b
  Saunders Mac Lane - Mathematics Form and Function

  <Collectables>
    # <Collectable roleName="smoothpinkrock"/>
    XhMath.SetPointed02 = ({Collectable, smoothpinkrock, jaggedgreyrock, pineneedle3, greenleaf, brownleaf, blueberry, blackberry, acorn, pinecone, birchbark, blackrock, ice, mud, stick, thorn, smallseed, seeds, feather, horsetail, fern, snow, bug, moss, lichen, smoothgreyrock, jaggedgreenrock, pineneedle2, yellowleaf, orangeleaf, redberry, yellowberry, chestnut, fircone, bluerock, slush, muck, knot, thistle, bigseed, tinyfeather, ginkoleaf}, Collectable)
  </Collectables>

