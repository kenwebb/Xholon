# test of manually converting Xholon2Future output into CoffeeScript
# Hello World model
# The Xholon2Future output:
# ((0000000[XholonClass](0000001[HelloWorldSystem]0000002[Hello]0000003[World]))(1000000>0000001([State=0]>00f4adf)(1000002>0000002>1000003([State=0]>00f4adf)1000003>0000003>1000002([State=0]>00f4adf))))

# Notes
#   @ means this
#   this code is intended only to handle this one simple model
#   it attempts to capture only what is contained in or implied by the Xholon2Future output

# 0000000
class XholonClass
  first: (@firstChild) ->
  next: (@nextSibling) ->
  port: (@port) ->

# 0000001
class HelloWorldSystem extends XholonClass
  constructor: (@State) ->

# 0000002
class Hello extends XholonClass
  constructor: (@State) ->

# 0000003
class World extends XholonClass
  constructor: (@State) ->

# instances

# 1000000
hws = new HelloWorldSystem 0

# 1000002
h = new Hello 0
hws.first(h)

# 1000003
w = new World 0
h.next(w)

# ports
h.port(w)
w.port(h)

# test
console.log hws
console.log h
console.log w

