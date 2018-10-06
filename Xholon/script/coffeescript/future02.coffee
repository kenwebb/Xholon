# test of manually converting Xholon2Future output into CoffeeScript
# Spring Idol model
# The Xholon2Future output:
# ((0000000[XholonClass](0000001[Instrument](0000002[Cymbal]0000003[Guitar]0000004[Harmonica]0000005[Piano]0000006[Saxophone])0000007[Performer](0000008[Instrumentalist](0000009[BaseSaxophonist])000000a[Juggler](000000b[PoeticJuggler])000000c[OneManBand]000000d[Singer])000000e[Poem](000000f[Sonnet29]0000010[Damoyselle])0000011[TalentCompetition](0000012[SpringIdol])0000013[Song]0000014[Instruments]0000015[Performers]0000016[Songs]0000017[Poems]))(1000000>0000012(1000001>0000014(1000002>00000021000003>00000031000004>00000041000005>00000051000006>0000006)1000007>0000015(1000008[hank]>000000c1000009[duke]>000000b>100000e([BeanBags=3]>00f4adf)100000a[kenny]>0000009>100000c>1000006)100000b>0000016(100000c>0000013([Title=Jingle Bells]>00f4ae3))100000d>0000017(100000e>0000010))))

# 0000000
class XholonClass
  constructor: (@id) ->
  role: (@roleName) ->
  first: (@firstChild) ->
  next: (@nextSibling) ->
  port: (@port) ->

# 0000001
class Instrument extends XholonClass

# 0000002
class Cymbal extends Instrument

# 0000003
class Guitar extends Instrument

# 0000004
class Harmonica extends Instrument

# 0000005
class Piano extends Instrument

# 0000006
class Saxophone extends Instrument

# 0000007
class Performer extends XholonClass

# 0000008
class Instrumentalist extends Performer

# 0000009
class BaseSaxophonist extends Instrumentalist
  port2: (@port2) ->

# 000000a
class Juggler extends Performer

# 000000b
class PoeticJuggler extends Juggler
  beanBags: (@beanBags) ->

# 000000c
class OneManBand extends Performer

# 000000d
class Singer extends Performer

# 000000e
class Poem extends XholonClass

# 000000f
class Sonnet29 extends Poem

# 0000010
class Damoyselle extends Poem

# 0000011
class TalentCompetition extends XholonClass

# 0000012
class SpringIdol extends XholonClass

# 0000013
class Song extends XholonClass
  title: (@title) ->

# 0000014
class Instruments extends XholonClass

# 0000015
class Performers extends XholonClass

# 0000016
class Songs extends XholonClass

# 0000017
class Poems extends XholonClass

# instances

# 1000000>0000012(
a1000000 = new SpringIdol 1000000

# 1000001>0000014(
a1000001 = new Instruments 1000001
a1000000.first(a1000001)

# 1000002>0000002
a1000002 = new Cymbal 1000002
a1000001.first(a1000002)

# 1000003>0000003
guit = new Guitar
a1000002.next(guit)

# 1000004>0000004
harm = new Harmonica
guit.next(harm)

# 1000005>0000005
pian = new Piano
harm.next(pian)

# 1000006>0000006)
saxo = new Saxophone
pian.next(saxo)

# 1000007>0000015(
perfs = new Performers
inst.next(perfs)

# 1000008[hank]>000000c
hank = new OneManBand
perfs.first(hank)
hank.role("hank")

# 1000009[duke]>000000b>100000e([BeanBags=3]>00f4adf)
duke = new PoeticJuggler
hank.next(duke)
duke.role("duke")
duke.beanBags(3)

# 100000a[kenny]>0000009>100000c>1000006)
kenny = new BaseSaxophonist
duke.next(kenny)
kenny.role("kenny")

# 100000b>0000016(
songs = new Songs
perfs.next(songs)

# 100000c>0000013([Title=Jingle Bells]>00f4ae3))
song = new Song
song.title("Jingle Bells")
songs.first(song)

# 100000d>0000017(
poems = new Poems

# 100000e>0000010))))
poem = new Damoyselle
poems.first(poem)

# ports
duke.port(poem)
kenny.port(saxo)
kenny.port2(song)

# test
console.log si

