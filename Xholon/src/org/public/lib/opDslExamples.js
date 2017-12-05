/**
 * Operadics DSL examples
 * source: 
 */

(function(root) {
  "use strict";

  function getOpDslExample(t) {
    var v = t; //.value;
    
    if (v == "Example 1") { return "Oops\n"; }
    
    else if (v == "Recipe Z") { return ""
 + "# Recipe Z - David I. Spivak (2015/06/17) Operadics: the mathematics of modular design - slide 11\n"
 + "pack Y1 = {a1, b1}\n"
 + "pack Y2 = {a2}\n"
 + "pack Y3 = {a3, b3}\n"
 + "pack Y4 = {a4}\n"
 + "pack Y5 = {a5}\n"
 + "pack Y6 = {a6}\n"
 + "pack Z = {az, bz}\n"
 + "bindings a1 |-> Y4\n"
 + "bindings b1 |-> Y3\n"
 + "bindings a2 |-> Y3\n"
 + "bindings a3 |-> Y4\n"
 + "bindings b3 |-> Y5\n"
 + "bindings a4 |-> Z\n"
 + "bindings a5 |-> Y6\n"
 + "bindings a6 |-> Z\n"
 + "bindings az |-> Y1\n"
 + "bindings bz |-> Y2\n"
 + "morphism comp: (Y1, Y2, Y3, Y4, Y5, Y6) -> Z\n"; }
 
    else if (v == "Recipe Z nested") { return ""
 + "# Recipe Z nested - David I. Spivak (2015/06/17) Operadics: the mathematics of modular design - slide 11\n"
 + "# retain intermediate Y3 pack\n"
 + "# packs\n"
 + "pack X1 = {d1, e1}\n"
 + "pack X2 = {d2, e2}\n"
 + "pack X3 = {d3}\n"
 + "pack Y1 = {a1, b1}\n"
 + "pack Y2 = {a2}\n"
 + "pack Y3 = {a3, b3}\n"
 + "pack Y4 = {a4}\n"
 + "pack Y5 = {a5}\n"
 + "pack Y6 = {a6}\n"
 + "pack Z = {az, bz}\n"
 + "# bindings\n"
 + "bindings d1 |-> X3\n"
 + "bindings e1 |-> X2\n"
 + "bindings d2 |-> X3\n"
 + "bindings e2 |-> Y3\n"
 + "bindings d3 |-> Y3\n"
 + "bindings a1 |-> Y4\n"
 + "bindings b1 |-> Y3\n"
 + "bindings a2 |-> Y3\n"
 + "bindings a3 |-> Y4\n"
 + "bindings b3 |-> Y5\n"
 + "bindings a4 |-> Z\n"
 + "bindings a5 |-> Y6\n"
 + "bindings a6 |-> Z\n"
 + "bindings az |-> Y1\n"
 + "bindings bz |-> Y2\n"
 + "# morphisms\n"
 + "morphism comp1: (X1, X2, X3) -> Y3\n"
 + "morphism comp2: (Y1, Y2, Y3, Y4, Y5, Y6) -> Z\n"; }
 
    else if (v == "Recipe Z nested replace") { return ""
 + "# Recipe Z nested replace - David I. Spivak (2015/06/17) Operadics: the mathematics of modular design - slide 11\n"
 + "# remove intermediate Y3 pack\n"
 + "# packs\n"
 + "pack X1 = {d1, e1}\n"
 + "pack X2 = {d2, e2}\n"
 + "pack X3 = {d3}\n"
 + "pack Y1 = {a1, b1}\n"
 + "pack Y2 = {a2}\n"
 + "pack Y3 = {a3, b3}\n"
 + "pack Y4 = {a4}\n"
 + "pack Y5 = {a5}\n"
 + "pack Y6 = {a6}\n"
 + "pack Z = {az, bz}\n"
 + "# bindings\n"
 + "bindings d1 |-> X3\n"
 + "bindings e1 |-> X2\n"
 + "bindings d2 |-> X3\n"
 + "bindings e2 |-> Y5 #Y3\n"
 + "bindings d3 |-> Y4 #Y3\n"
 + "bindings a1 |-> Y4\n"
 + "bindings b1 |-> X1 #Y3\n"
 + "bindings a2 |-> X2 #Y3\n"
 + "bindings a3 |-> Y4\n"
 + "bindings b3 |-> Y5\n"
 + "bindings a4 |-> Z\n"
 + "bindings a5 |-> Y6\n"
 + "bindings a6 |-> Z\n"
 + "bindings az |-> Y1\n"
 + "bindings bz |-> Y2\n"
 + "# morphisms\n"
 + "morphism comp1: (X1, X2, X3) -> Y3\n"
 + "morphism comp2: (Y1, Y2, Y3, Y4, Y5, Y6) -> Z\n"; }

    else if (v == "Arrangements") { return ""
 + "# arrangements - David I. Spivak (2015/06/17) Operadics: the mathematics of modular design - slide 19\n"
 + "pack X11 = {} # no ports\n"
 + "pack X12 = {}\n"
 + "pack X13 = {}\n"
 + "pack Y1 = {}\n"
 + "pack X21 = {}\n"
 + "pack X22 = {}\n"
 + "pack Y2 = {}\n"
 + "pack Z = {}\n"
 + "bindings # dummy bindings\n"
 + "morphism φ1: (X11, X12, X13) -> Y1\n"
 + "morphism φ2: (X21, X22) -> Y2\n"
 + "morphism φ: (Y1, Y2) -> Z\n"; }

    else if (v == "Composition of networks") { return ""
 + "# arrangements - Composition of networks - David I. Spivak (2015/06/17) Operadics: the mathematics of modular design - slide 20\n"
 + "pack X11 = {a, b}\n"
 + "pack X12 = {c, d}\n"
 + "pack X13 = {e}\n"
 + "pack Y1 = {}\n"
 + "pack X21 = {f, g}\n"
 + "pack X22 = {h, i}\n"
 + "pack Y2 = {}\n"
 + "pack Z = {j}\n"
 + "bindings a |-> X13\n"
 + "bindings b |-> X12\n"
 + "bindings c |-> X13\n"
 + "bindings d |-> X21\n"
 + "bindings e |-> Z\n"
 + "bindings f |-> X22\n"
 + "bindings g |-> Z\n"
 + "bindings h |-> X21\n"
 + "bindings i |-> X11\n"
 + "bindings j |-> X12\n"
 + "morphism φ1: (X11, X12, X13) -> Y1\n"
 + "morphism φ2: (X21, X22) -> Y2\n"
 + "morphism φ: (Y1, Y2) -> Z\n"; }
    
    else if (v == "Brusselator") { return ""
 + "# Brusselator\n"
 + "pack R1 = {a1, x1}\n"
 + "pack R2 = {x2, x2', y2}\n" // there are 2 ports from R1 to X
 + "pack R3 = {x3, y3, d3, b3}\n"
 + "pack R4 = {x4, e4}\n"
 + "pack X = {}\n"
 + "pack Y = {}\n"
 + "pack E = {}\n"
 + "pack D = {}\n"
 + "pack B = {}\n"
 + "pack A = {}\n"
 + "pack N = {}\n" // compartment
 + "bindings a1 |-> A\n" // from R1:Pack port a1 to A:Pack
 + "bindings x1 |-> X\n"
 + "bindings x2 |-> X\n"
 + "bindings x2' |-> X\n"
 + "bindings y2 |-> Y\n"
 + "bindings x3 |-> X\n"
 + "bindings y3 |-> Y\n"
 + "bindings d3 |-> D\n"
 + "bindings b3 |-> B\n"
 + "bindings x4 |-> X\n"
 + "bindings e4 |-> E\n"
 + "morphism comp: (R1, R2, R3, R4, X, Y, E, D, B, A) -> N\n"; }
    
    else if (v == "Brusselator 2") { return ""
 + "# Brusselator 2 - from original Xholon model which was from an SBML model\n"
 + "# I'm getting the port names from the Xholon CD file (ClassDetails.xml):\n"
 + "#	<R1 xhType='XhtypePureActiveObject'>\n"
 + "#		<port name='port' index='P_SM_SUB1' connector='#xpointer(ancestor::compartment/descendant::A)'/>\n"
 + "#		<port name='port' index='P_SM_PRD1' connector='#xpointer(ancestor::compartment/descendant::X)'/>\n"
 + "#		<attribute name='reversible' value='false'/>\n"
 + "#	</R1>\n"
 + "# The Xholon parser would generate actual nodes for all implied variables, as specified by the DSL cable/link names\n"
 + "# Which ports are external vs internal (exposed vs not-exposed); maybe use prime ' to distinguish\n"
 + "# The Brusselator doesn't compute an end result; the variables X and Y continnuously cycle through a sequence of values\n"
 + "# \n"
 + "pack C = {}  # compartment_0\n"
 + "pack R1 = {a1, x1}\n"
 + "pack R2 = {x2, y2, x2} # 2 ports reference the X cable/link, once as a substrate and once as a product; these need to have different names\n"
 + "pack R3 = {x3, b3, y3, d3}\n"
 + "pack R4 = {x4, e4}\n"
 + "cables L = {X, Y, E, D, B, A} # these are the XholonClass names as specified in the Xholon IH and CSH files; the passive objects, variables\n"
 + "bindings a1 |-> A\n"
 + "bindings b3 |-> B\n"
 + "bindings d3 |-> D\n"
 + "bindings e4 |-> E\n"
 + "bindings x1,x2,x3,x4 |-> X\n"
 + "bindings y2,y3 |-> Y\n"
 + "morphism (R1, R2, R3, R4) -> C\n"; }

    else if (v == "Compose Operad Morphisms 1") { return ""
 + "# Compose Operad Morphisms - example from David email Nov 9, 2017\n"
 + "# f: P1, P2, P3-->P'\n"
 + "# g:Q1, Q2-->Q'\n"
 + "# h:P', Q'-->R\n"
 + "# ------------\n"
 + "# (f,g)//h: P1, P2, P3, Q1, Q2-->R\n"
 + "# \n"
 + "pack P' = {} # no ports\n"
 + "pack P1 = {a1,b1}\n"
 + "pack P2 = {}\n"
 + "pack P3 = {}\n"
 + "#cables C1 = {u,v,w} # this works\n"
 + "# \n"
 + "pack Q' = {}\n"
 + "pack Q1 = {}\n"
 + "pack Q2 = {}\n"
 + "#cables C2 = {x,y,z} # this works\n"
 + "# \n"
 + "pack R = {}\n"
 + "# no cables\n"
 + "# no bindings\n"
 + "bindings # dummy bindings for now, to force generation of Xholon Pack nodes\n"
 + "#bindings a1 |-> u # this works\n"
 + "morphism (P1, P2, P3) -> P'\n"
 + "morphism (Q1, Q2) -> Q'\n"
 + "morphism (P', Q') -> R\n"; }

    else if (v == "Compose Operad Morphisms 2") { return ""
 + "# example from David email Nov 10, 2017\n"
 + "# Compose Operad Morphisms with ports and bindings\n"
 + "# David uses := instead of = in Pack statements; I've changed := to =\n"
 + "# TODO my parser does not handle this correctly yet\n"
 + "# \n"
 + "pack P' = {a',c',f',d'}\n"
 + "pack P1 = {a1,b1,f1}\n"
 + "pack P2 = {b2,c2,d2,e2}\n"
 + "pack P3 = {e3,f3}\n"
 + "# morphism phi_1: (P1, P2, P3) -> P' := {a'=a1, b1=b2, c2=c', d2=d', e2=e3, f1=f3=f'}\n"
 + "#\n"
 + "pack Q' = {dd, gg}\n"
 + "pack Q  = {ddd, ggg}\n"
 + "# morphism phi_2: (Q) -> Q' := {dd=ddd, gg=ggg}\n"
 + "#\n"
 + "pack R = {aR, fR, gR}\n"
 + "# morphism psi: (P', Q') -> R := {aR=a', dd=d', fR=f', gg=gR, c'} // c' is dangling.\n"
 + "# \n"
 + "bindings # dummy bindings\n"
 + "morphism phi_1: (P1, P2, P3) -> P' := {a'=a1, b1=b2, c2=c', d2=d', e2=e3, f1=f3=f'}\n"
 + "morphism phi_2: (Q) -> Q' := {dd=ddd, gg=ggg}\n"
 + "morphism psi: (P', Q') -> R := {aR=a', dd=d', fR=f', gg=gR, c'} # c' is dangling.\n"; }

    else if (v == "Compose Operad Morphisms 3") { return ""
 + "# example from David email Nov 10, 2017 - composed\n"
 + "# Compose Operad Morphisms with ports and bindings - composed\n"
 + "# this is exactly the same as Example 1\n"
 + "# \n"
 + "pack P1 = {a1,b1,f1}\n"
 + "pack P2 = {b2,c2,d2,e2}\n"
 + "pack P3 = {e3,f3}\n"
 + "pack Q  = {ddd, ggg}\n"
 + "pack R  = {aR, fR, gR}\n"
 + "# \n"
 + "bindings # dummy bindings\n"
 + "morphism comp: (P1, P2, P3, Q) -> R := {a1=aR, b1=b2, c2, d2=ddd, e2=e3, f1=f3=fR, ggg=gR}\n"; }

    else if (v == "Pixel Array 1.1") { return ""
 + "# example from 1.1 - A simple example - in Pixel Array paper by David Spivak\n"
 + "# another example\n"
 + "# two equations: x² = w    w = 1 - y²\n"
 + "# I'm inferring the packs and cables from the two equations; the packs and cables are not specified in the paper\n"
 + "pack P' = {}\n"
 + "pack P1 = {w1, x1}\n"
 + "pack P2 = {w2, y2}\n"
 + "cables C = {w, x, y}\n"
 + "bindings w1,w2 |-> w\n"
 + "bindings x1 |-> x\n"
 + "bindings y2 |-> y\n"
 + "morphism (P1, P2) -> P'\n"; }

    else if (v == "Pixel Array 2.1") { return ""
 + "# example from 2.1 and 2.2 in Pixel Array paper by David Spivak\n"
 + "# another example\n"
 + "# three relations\n"
 + "# variables v, z are to be exposed\n"
 + "# \n"
 + "pack R' = {v', z'}\n"
 + "pack R1 = {x1, y1}\n"
 + "pack R2 = {v2, w2, y2}\n"
 + "pack R3 = {u3, w3, x3, z3}\n"
 + "cables C = {u, v, w, x, y, z}\n"
 + "bindings u3 |-> u\n"
 + "bindings v2, v' |-> v\n"
 + "bindings w2, w3 |-> w\n"
 + "bindings x1, x3 |-> x\n"
 + "bindings y1, y2 |-> y\n"
 + "bindings z3, z' |-> z\n"
 + "morphism (R1, R2, R3) -> R'\n"; }

    else if (v == "Pixel Array 3.1.3") { return ""
 + "# another example of the DSL format\n"
 + "# this example uses a different notation for links/cables; I assume links and cables are the same thing?\n"
 + "# \n"
 + "pack P' = {x', v', z'}\n"
 + "#pack P_ = {x_,v_,z_}     # node with ports; I can't use P' as an XML tag name; can't use ' or ` or  ′ &prime; but could use _\n"
 + "pack P1 = {x1, y1}\n"
 + "pack P2 = {u2,v2,w2,y2}\n"
 + "pack P3 = {u3,y3,z3}\n"
 + "cables L = {u,v,w,x,y, z} # Λ, links, hyperedges  see Remark 3.1.8\n"
 + "#bindings f2(u2) = f3(u3) = lu # linking function  u2,u3 |-> lu  etc.\n"
 + "bindings u2,u3 |-> u\n"
 + "bindings v2,v' |-> v\n"
 + "bindings w2 |-> w\n"
 + "bindings x1,x' |-> x\n"
 + "bindings y1,y2,y3 |-> y\n"
 + "bindings z3,z' |-> z\n"
 + "morphism (P1, P2, P3) -> P'\n"; }

    else if (v == "Figure 6") { return ""
 + "# example of the DSL format from FIGURE 6 in a David Spivak paper\n"
 + "# these keywords are just placeholders; for now they are just the first thing that came to mind\n"
 + "# \n"
 + "# NODES\n"
 + "node Y = {a, b, c, d, e}        # Create a node of type Y with 5 ports (wires)\n"
 + "node X1 = {r, s, t}             # Create 3 more nodes with ports (wires)\n"
 + "node X2 = {u, v}\n"
 + "node X3 = {w, x, y, z}\n"
 + "\n"
 + "# the additional domain objects ('inner stars') that are inside X1 X2 X3\n"
 + "# nodes inside X1 Φ1\n"
 + "node V1 = {aa, bb, cc}\n"
 + "#node W1 = {dd, ee, ff}\n"
 + "# nodes inside X2 Φ2\n"
 + "node V2 = {gg, hh, ii}\n"
 + "node W2 = {jj, kk}\n"
 + "# nodes inside X3 Φ3\n"
 + "node U3 = {ll, mm}\n"
 + "node V3 = {nn, oo}\n"
 + "node W3 = {pp, qq}\n"
 + "\n"
 + "# CABLES\n"
 + "cables C = {1, 2, 3, 4, 5, 6}   # Create 6 cables\n"
 + "#cables C = {_1, _2, _3, _4, _5, _6}  # Xholon, XML, JavaScript names can't start with a digit\n"
 + "\n"
 + "# new inner cables\n"
 + "cables D = {11, 21, 31, 32}\n"
 + "\n"
 + "# BINDINGS\n"
 + "#bindings f1(s) = f3(w) = g(a) = 1  # Connect the ports (wires), port connections\n"
 + "bindings s,w,a |-> 1\n"
 + "bindings r,b |-> 2\n"
 + "bindings t,v,c |-> 3\n"
 + "bindings u,x |-> 4\n"
 + "bindings y,d |-> 5\n"
 + "bindings z,e |-> 6\n"
 + "\n"
 + "# TODO new bindings\n"
 + "# 2 approaches: (1) go all the way through to cables in Y,X1,X2,X3 (as in Xholon), or (2) just go to a cable that connects to a local relay port (as in eTrice)\n"
 + "#bindings aa |-> 1 # ?\n"
 + "\n"
 + "# MORPHISMS\n"
 + "\n"
 + "# new morphisms\n"
 + "#morphism (V1, W1) -> X1\n"
 + "#morphism (V2, W2) -> X2\n"
 + "#morphism (U3, V3, W3) -> X3\n"
 + "\n"
 + "morphism (X1, X2, X3) -> Y      # Move X1, X2, and X3 inside Y\n"; }

/*
# Cell Model - packs
pack Glucose = {}
pack Glucose6Phosphate = {}
pack Fructose6Phosphate = {}
pack Fructose1x6Biphosphate = {}
pack DihydroxyacetonePhosphate = {}
pack Glyceraldehyde3Phosphate = {}
pack X1x3BisphosphoGlycerate = {}
pack X3PhosphoGlycerate = {}
pack X2PhosphoGlycerate = {}
pack PhosphoEnolPyruvate = {}
pack Pyruvate = {}
pack Cytosol = {}
#
pack Hexokinase = {p1, p2}
pack PhosphoGlucoIsomerase = {q1, q2}
pack PhosphoFructokinase = {r1, r2}
pack Aldolase = {s1, s2, s3}
pack TriosePhosphateIsomerase = {t1, t2}
pack Glyceraldehyde3phosphateDehydrogenase = {u1, u2}
pack PhosphoGlycerokinase = {v1, v2}
pack PhosphoGlyceromutase = {w1, w2}
pack Enolase = {x1, x2}
pack PyruvateKinase = {y1, y2}
pack Cytoplasm = {}
#
pack EukaryoticCell = {}
#
bindings p1 |-> Glucose
bindings p2 |-> Glucose6Phosphate
bindings q1 |-> Glucose6Phosphate
bindings q2 |-> Fructose6Phosphate
bindings r1 |-> Fructose6Phosphate
bindings r2 |-> Fructose1x6Biphosphate
bindings s1 |-> Fructose1x6Biphosphate
bindings s2 |-> Glyceraldehyde3Phosphate
bindings s3 |-> DihydroxyacetonePhosphate
bindings t1 |-> DihydroxyacetonePhosphate
bindings t2 |-> Glyceraldehyde3Phosphate
bindings u1 |-> Glyceraldehyde3Phosphate
bindings u2 |-> X1x3BisphosphoGlycerate
bindings v1 |-> X1x3BisphosphoGlycerate
bindings v2 |-> X3PhosphoGlycerate
bindings w1 |-> X3PhosphoGlycerate
bindings w2 |-> X2PhosphoGlycerate
bindings x1 |-> X2PhosphoGlycerate
bindings x2 |-> PhosphoEnolPyruvate
bindings y1 |-> PhosphoEnolPyruvate
bindings y2 |-> Pyruvate
#
morphism comp1: (Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate) -> Cytosol
morphism comp2: (Hexokinase, PhosphoGlucoIsomerase, PhosphoFructokinase, Aldolase, TriosePhosphateIsomerase, Glyceraldehyde3phosphateDehydrogenase, PhosphoGlycerokinase, PhosphoGlyceromutase, Enolase, PyruvateKinase) -> Cytoplasm
morphism comp: (Cytosol, Cytoplasm) -> EukaryoticCell
*/

/*
# Cell Model - cables
cables C = {Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate}
pack Cytosol = {}
#
pack Hexokinase = {p1, p2}
pack PhosphoGlucoIsomerase = {q1, q2}
pack PhosphoFructokinase = {r1, r2}
pack Aldolase = {s1, s2, s3}
pack TriosePhosphateIsomerase = {t1, t2}
pack Glyceraldehyde3phosphateDehydrogenase = {u1, u2}
pack PhosphoGlycerokinase = {v1, v2}
pack PhosphoGlyceromutase = {w1, w2}
pack Enolase = {x1, x2}
pack PyruvateKinase = {y1, y2}
pack Cytoplasm = {}
#
pack EukaryoticCell = {}
#
bindings p1 |-> Glucose
bindings p2 |-> Glucose6Phosphate
bindings q1 |-> Glucose6Phosphate
bindings q2 |-> Fructose6Phosphate
bindings r1 |-> Fructose6Phosphate
bindings r2 |-> Fructose1x6Biphosphate
bindings s1 |-> Fructose1x6Biphosphate
bindings s2 |-> Glyceraldehyde3Phosphate
bindings s3 |-> DihydroxyacetonePhosphate
bindings t1 |-> DihydroxyacetonePhosphate
bindings t2 |-> Glyceraldehyde3Phosphate
bindings u1 |-> Glyceraldehyde3Phosphate
bindings u2 |-> X1x3BisphosphoGlycerate
bindings v1 |-> X1x3BisphosphoGlycerate
bindings v2 |-> X3PhosphoGlycerate
bindings w1 |-> X3PhosphoGlycerate
bindings w2 |-> X2PhosphoGlycerate
bindings x1 |-> X2PhosphoGlycerate
bindings x2 |-> PhosphoEnolPyruvate
bindings y1 |-> PhosphoEnolPyruvate
bindings y2 |-> Pyruvate
#
morphism comp1: (Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate) -> Cytosol
morphism comp2: (Hexokinase, PhosphoGlucoIsomerase, PhosphoFructokinase, Aldolase, TriosePhosphateIsomerase, Glyceraldehyde3phosphateDehydrogenase, PhosphoGlycerokinase, PhosphoGlyceromutase, Enolase, PyruvateKinase) -> Cytoplasm
morphism comp: (Cytosol, Cytoplasm) -> EukaryoticCell
*/

/*
# Cell Model - complete collection of active objects (packs), passive objects (cables), and containers
# Webb, K., & White, T. (2005). UML as a cell and biochemistry modeling language. BioSystems, 80, 283–302.
# 
cables D = {GlucoseECS}
# 
# Cytosol - passive objects, cables, hyperedges
cables C = {Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate}
pack Cytosol = {}
# 
# CellMembrane
pack CellBilayer = {a1, a2}
pack CellMembrane = {}
# 
# Cytoplasm
pack Hexokinase = {p1, p2}
pack PhosphoGlucoIsomerase = {q1, q2}
pack PhosphoFructokinase = {r1, r2}
pack Aldolase = {s1, s2, s3}
pack TriosePhosphateIsomerase = {t1, t2}
pack Glyceraldehyde3phosphateDehydrogenase = {u1, u2}
pack PhosphoGlycerokinase = {v1, v2}
pack PhosphoGlyceromutase = {w1, w2}
pack Enolase = {x1, x2}
pack PyruvateKinase = {y1, y2}
pack Cytoplasm = {}
#
pack ExtraCellularSolution = {}
# 
pack EukaryoticCell = {}
pack ExtraCellularSpace = {}
#
bindings p1 |-> Glucose
bindings p2 |-> Glucose6Phosphate
bindings q1 |-> Glucose6Phosphate
bindings q2 |-> Fructose6Phosphate
bindings r1 |-> Fructose6Phosphate
bindings r2 |-> Fructose1x6Biphosphate
bindings s1 |-> Fructose1x6Biphosphate
bindings s2 |-> Glyceraldehyde3Phosphate
bindings s3 |-> DihydroxyacetonePhosphate
bindings t1 |-> DihydroxyacetonePhosphate
bindings t2 |-> Glyceraldehyde3Phosphate
bindings u1 |-> Glyceraldehyde3Phosphate
bindings u2 |-> X1x3BisphosphoGlycerate
bindings v1 |-> X1x3BisphosphoGlycerate
bindings v2 |-> X3PhosphoGlycerate
bindings w1 |-> X3PhosphoGlycerate
bindings w2 |-> X2PhosphoGlycerate
bindings x1 |-> X2PhosphoGlycerate
bindings x2 |-> PhosphoEnolPyruvate
bindings y1 |-> PhosphoEnolPyruvate
bindings y2 |-> Pyruvate
bindings a1 |-> Glucose
bindings a2 |-> GlucoseECS
#
morphism comp1: (Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate) -> Cytosol
# 
morphism comp2: (Hexokinase, PhosphoGlucoIsomerase, PhosphoFructokinase, Aldolase, TriosePhosphateIsomerase, Glyceraldehyde3phosphateDehydrogenase, PhosphoGlycerokinase, PhosphoGlyceromutase, Enolase, PyruvateKinase, Cytosol) -> Cytoplasm
# 
morphism comp3: (CellBilayer) -> CellMembrane
# 
morphism comp4: (CellMembrane, Cytoplasm) -> EukaryoticCell
# 
morphism comp5: (GlucoseECS) -> ExtraCellularSolution
# 
morphism comp: (EukaryoticCell, ExtraCellularSolution) -> ExtraCellularSpace
*/

    else if (v == "Cell Model") { return ""
 + "# Cell Model - complete collection of active objects (packs), passive objects (cables), and containers\n"
 + "# Webb, K., & White, T. (2005). UML as a cell and biochemistry modeling language. BioSystems, 80, 283–302.\n"
 + "# \n"
 + "cables D = {GlucoseECS}\n"
 + "# \n"
 + "# Cytosol - passive objects, cables, hyperedges\n"
 + "cables C = {Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate}\n"
 + "pack Cytosol = {}\n"
 + "# \n"
 + "# CellMembrane\n"
 + "pack CellBilayer = {a1, a2}\n"
 + "pack CellMembrane = {}\n"
 + "# \n"
 + "# Cytoplasm\n"
 + "pack Hexokinase = {p1, p2}\n"
 + "pack PhosphoGlucoIsomerase = {q1, q2}\n"
 + "pack PhosphoFructokinase = {r1, r2}\n"
 + "pack Aldolase = {s1, s2, s3}\n"
 + "pack TriosePhosphateIsomerase = {t1, t2}\n"
 + "pack Glyceraldehyde3phosphateDehydrogenase = {u1, u2}\n"
 + "pack PhosphoGlycerokinase = {v1, v2}\n"
 + "pack PhosphoGlyceromutase = {w1, w2}\n"
 + "pack Enolase = {x1, x2}\n"
 + "pack PyruvateKinase = {y1, y2}\n"
 + "pack Cytoplasm = {}\n"
 + "#\n"
 + "pack ExtraCellularSolution = {}\n"
 + "# \n"
 + "pack EukaryoticCell = {}\n"
 + "pack ExtraCellularSpace = {}\n"
 + "#\n"
 + "bindings p1 |-> Glucose\n"
 + "bindings p2 |-> Glucose6Phosphate\n"
 + "bindings q1 |-> Glucose6Phosphate\n"
 + "bindings q2 |-> Fructose6Phosphate\n"
 + "bindings r1 |-> Fructose6Phosphate\n"
 + "bindings r2 |-> Fructose1x6Biphosphate\n"
 + "bindings s1 |-> Fructose1x6Biphosphate\n"
 + "bindings s2 |-> Glyceraldehyde3Phosphate\n"
 + "bindings s3 |-> DihydroxyacetonePhosphate\n"
 + "bindings t1 |-> DihydroxyacetonePhosphate\n"
 + "bindings t2 |-> Glyceraldehyde3Phosphate\n"
 + "bindings u1 |-> Glyceraldehyde3Phosphate\n"
 + "bindings u2 |-> X1x3BisphosphoGlycerate\n"
 + "bindings v1 |-> X1x3BisphosphoGlycerate\n"
 + "bindings v2 |-> X3PhosphoGlycerate\n"
 + "bindings w1 |-> X3PhosphoGlycerate\n"
 + "bindings w2 |-> X2PhosphoGlycerate\n"
 + "bindings x1 |-> X2PhosphoGlycerate\n"
 + "bindings x2 |-> PhosphoEnolPyruvate\n"
 + "bindings y1 |-> PhosphoEnolPyruvate\n"
 + "bindings y2 |-> Pyruvate\n"
 + "bindings a1 |-> Glucose\n"
 + "bindings a2 |-> GlucoseECS\n"
 + "#\n"
 + "morphism comp1: (Glucose, Glucose6Phosphate, Fructose6Phosphate, Fructose1x6Biphosphate, DihydroxyacetonePhosphate, Glyceraldehyde3Phosphate, X1x3BisphosphoGlycerate, X3PhosphoGlycerate, X2PhosphoGlycerate, PhosphoEnolPyruvate, Pyruvate) -> Cytosol\n"
 + "# \n"
 + "morphism comp2: (Hexokinase, PhosphoGlucoIsomerase, PhosphoFructokinase, Aldolase, TriosePhosphateIsomerase, Glyceraldehyde3phosphateDehydrogenase, PhosphoGlycerokinase, PhosphoGlyceromutase, Enolase, PyruvateKinase, Cytosol) -> Cytoplasm\n"
 + "# \n"
 + "morphism comp3: (CellBilayer) -> CellMembrane\n"
 + "# \n"
 + "morphism comp4: (CellMembrane, Cytoplasm) -> EukaryoticCell\n"
 + "# \n"
 + "morphism comp5: (GlucoseECS) -> ExtraCellularSolution\n"
 + "# \n"
 + "morphism comp: (EukaryoticCell, ExtraCellularSolution) -> ExtraCellularSpace\n"; }

/*
------------------------------------------------------------------------------ ELEVATOR -------------
ManyEyes
--------
XholonClass	Occurrences
AnElevatorSystem	1
CallButton	16
Dispatcher	1
Door	2
DoorControlButton	4
Elevator	2
ElevatorPanel	2
Floor	8
FloorSelectionButton	16
Hoist	2
Indicator	2
TickGenerator	2
UserJTree	1

##########################################
# Elevator - all packs, no cables
# without the multiplicities in the original Xholon model (2 elevators and 8 floors); I've written these multiplicities as comments for each pack
# http://www.primordion.com/Xholon/gwt/Xholon.html?app=Elevator&gui=clsc
# http://www.primordion.com/pub/presentations/KenWebb_Oclug_UmlPresentation_v2.pdf
pack Door = {} # 2
pack ElevatorPanel = {} # 2
pack Hoist = {h0} # 2  Hoist is both an active object and a container
pack Indicator = {} # 2
pack TickGenerator = {} # 2
pack Elevator = {} # 2
# 
pack FloorSelectionButton = {s0, s1} # 16
pack DoorControlButton = {c0, c1} # 4
pack CallButton = {b0, b1} # 16
# 
pack Floor = {} # 8
# 
pack UserJTree = {u0, u1, u2} # 1
pack Dispatcher = {d0, d1, d2} # 1
pack AnElevatorSystem = {} # 1 # 1
# 
bindings s0, c0, b0 |-> UserJTree
bindings s1, c1, b1 |-> Dispatcher
bindings u0, d0 |-> FloorSelectionButton
bindings u1, d1 |-> DoorControlButton
bindings u2, d2 |-> CallButton
# 
morphism floor: (CallButton) -> Floor
morphism elevatorPanel: (Indicator, DoorControlButton, FloorSelectionButton) -> ElevatorPanel
morphism hoist: (TickGenerator) -> Hoist
morphism elevator: (ElevatorPanel, Door, Hoist) -> Elevator
morphism anElevatorSystem: (UserJTree, Floor, Elevator, Dispatcher) -> AnElevatorSystem
*/

    else if (v == "Elevator") { return ""
 + "# Elevator - all packs, no cables\n"
 + "# without the multiplicities in the original Xholon model (2 elevators and 8 floors); I've written these multiplicities as comments for each pack\n"
 + "# http://www.primordion.com/Xholon/gwt/Xholon.html?app=Elevator&gui=clsc\n"
 + "# http://www.primordion.com/pub/presentations/KenWebb_Oclug_UmlPresentation_v2.pdf\n"
 + "pack Door = {} # 2\n"
 + "pack ElevatorPanel = {} # 2\n"
 + "pack Hoist = {h0} # 2  Hoist is both an active object and a container\n"
 + "pack Indicator = {} # 2\n"
 + "pack TickGenerator = {} # 2\n"
 + "pack Elevator = {} # 2\n"
 + "# \n"
 + "pack FloorSelectionButton = {s0, s1} # 16\n"
 + "pack DoorControlButton = {c0, c1} # 4\n"
 + "pack CallButton = {b0, b1} # 16\n"
 + "# \n"
 + "pack Floor = {} # 8\n"
 + "# \n"
 + "pack UserJTree = {u0, u1, u2} # 1\n"
 + "pack Dispatcher = {d0, d1, d2} # 1\n"
 + "pack AnElevatorSystem = {} # 1 # 1\n"
 + "# \n"
 + "bindings s0, c0, b0 |-> UserJTree\n"
 + "bindings s1, c1, b1 |-> Dispatcher\n"
 + "bindings u0, d0 |-> FloorSelectionButton\n"
 + "bindings u1, d1 |-> DoorControlButton\n"
 + "bindings u2, d2 |-> CallButton\n"
 + "# \n"
 + "morphism floor: (CallButton) -> Floor\n"
 + "morphism elevatorPanel: (Indicator, DoorControlButton, FloorSelectionButton) -> ElevatorPanel\n"
 + "morphism hoist: (TickGenerator) -> Hoist\n"
 + "morphism elevator: (ElevatorPanel, Door, Hoist) -> Elevator\n"
 + "morphism anElevatorSystem: (UserJTree, Floor, Elevator, Dispatcher) -> AnElevatorSystem\n"; }

/*
# Hypergraph 1
# https://en.wikipedia.org/wiki/Hypergraph
# https://en.wikipedia.org/wiki/File:Hypergraph-wikipedia.svg
cables C = {E1, E2, E3, E4}
pack V1 = {a1}
pack V2 = {a2, b2}
pack V3 = {a3, b3, c3}
pack V4 = {a4}
pack V5 = {a5}
pack V6 = {a6}
pack V7 = {}
pack Top = {}
bindings a1, b2, c3 |-> E1
bindings a2, a3 |-> E2
bindings b3, a5, a6 |-> E3
bindings a4 |-> E4
morphism comp: (V1, V2, V3, V4, V5, V6, V7) -> Top
*/

    else if (v == "Hypergraph 1") { return ""
 + "# Hypergraph 1\n"
 + "# https://en.wikipedia.org/wiki/Hypergraph\n"
 + "# https://en.wikipedia.org/wiki/File:Hypergraph-wikipedia.svg\n"
 + "cables C = {E1, E2, E3, E4}\n"
 + "pack V1 = {a1}\n"
 + "pack V2 = {a2, b2}\n"
 + "pack V3 = {a3, b3, c3}\n"
 + "pack V4 = {a4}\n"
 + "pack V5 = {a5}\n"
 + "pack V6 = {a6}\n"
 + "pack V7 = {}\n"
 + "pack Top = {}\n"
 + "bindings a1, b2, c3 |-> E1\n"
 + "bindings a2, a3 |-> E2\n"
 + "bindings b3, a5, a6 |-> E3\n"
 + "bindings a4 |-> E4\n"
 + "morphism comp: (V1, V2, V3, V4, V5, V6, V7) -> Top\n"; }

    else return "Unable to find " + v + ".";
  };
  
  root.OPDSLEXAMPLES = {
    getExample: getOpDslExample
  };
})(this);
