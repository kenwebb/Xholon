-- Growing Trees - chapter 11
-- a branch holds 3 elements: an element (of type a), the left sub-tree (of type Tree a), and the right sub-tree (of type Tree a)

{-
examples of trees:
Br 1 Lf Lf
Br 2 (Br 1 Lf Lf) Lf
Br 2 (Br 1 Lf Lf) (Br 4 Lf Lf)
Lf  -- empty tree
Br (3, "three") (Br (1, "one") Lf (Br (2, "two") Lf Lf)) (Br (4, "four") Lf Lf)
-}

-- ken@ken-Lenovo-Yoga-3-14:~/haskellspace$ cd bookA/BinaryTree/
-- ghci

data Tree a = Br a (Tree a) (Tree a) -- branch
            | Lf deriving Show -- leaf

treeSize :: Num b => Tree a -> b
treeSize (Br _ l r) = 1 + treeSize l + treeSize r
treeSize Lf = 0

-- add up all the integers in a tree of numbers
treeTotal :: Num a => Tree a -> a
treeTotal (Br x l r) = x + treeTotal l + treeTotal r
treeTotal Lf = 0

listOfTree :: Tree a -> [a]
listOfTree (Br x l r) = [x] ++ listOfTree l ++ listOfTree r
listOfTree Lf = []

max' :: Ord a => a -> a -> a
maxDepth :: (Num b, Ord b) => Tree a -> b
max' a b = if a > b then a else b
maxDepth (Br _ l r) = 1 + max' (maxDepth l) (maxDepth r)
maxDepth Lf = 0

treeMap :: (a -> b) -> Tree a -> Tree b
treeMap f (Br x l r) = Br (f x) (treeMap f l) (treeMap f r)
treeMap f Lf = Lf

treeLookup :: Ord a => Tree (a, b) -> a -> Maybe b
treeLookup Lf _ = Nothing
treeLookup (Br (k', v) l r) k =
  if k == k' then Just v else
  if k < k' then treeLookup l k else
    treeLookup r k


treeInsert :: Ord a => Tree (a, b) -> a -> b -> Tree (a, b)
treeInsert Lf k v = Br (k, v) Lf Lf
treeInsert (Br (k', v') l r) k v =
  if k == k' then Br (k, v) l r else
  if k < k' then Br (k, v) (treeInsert l k v) r else
    Br (k', v') l (treeInsert r k v)

{-
usage:
:l bt

:t Br
Br :: a -> Tree a -> Tree a -> Tree a
*Main> :t Lf
Lf :: Tree a
*Main> Br 1 Lf Lf
Br 1 Lf Lf
*Main> let tree1 = Br 1 Lf Lf
*Main> treeSize tree1
1
*Main> let tree2 = Br 2 (Br 1 Lf Lf) (Br 4 Lf Lf)
*Main> treeSize tree2
3
*Main> treeSize Lf
0

-}

-- Xholon-like tree
{-
let tree3 = Br "helloworld" (Br "hello" Lf Lf) (Br "world" Lf Lf)
*Main> treeSize tree3
3
*Main> listOfTree tree3
["helloworld","hello","world"]

-}

{-
Haskell BT vs Newick, SExpression

Haskell BT
----------
Br "HelloWorldSystem" (Br "Hello" Lf Lf) (Br "World" Lf Lf)

Newick
------
(Hello,
World)HelloWorldSystem;

SExpression
-----------
(
 HelloWorldSystem (
  Hello
  World
 )
)

GraphvizBT
----------
$wnd.xh.xport("GraphvizBT", $wnd.xh.root(),
'{"gvFileExt":".gv",
"gvGraph":"graph",
"layout":"dot",
"edgeOp":"--","gvCluster":"",
"shouldShowStateMachineEntities":false,
"filter":"--Behavior,Script",
"nameTemplateNodeId":"^^^^i^",
"nameTemplateNodeLabel":"R^^^^^",
"shouldQuoteLabels":true,
"shouldShowLinks":false,
"shouldShowLinkLabels":false,
"shouldSpecifyLayout":false,
"maxLabelLen":-1,"shouldColor":true,
"defaultNodeColor":"#f0f8ff",
"bgGraphColor":"white",
"shouldSpecifyShape":false,
"shape":"ellipse",
"shouldSpecifySize":false,
"size":"6",
"shouldSpecifyFontname":false,
"fontname":"\"Courier New\"",
"shouldSpecifyArrowhead":false,
"arrowhead":"vee",
"shouldSpecifyStylesheet":true,
"stylesheet":"Xholon.css",
"shouldSpecifyRankdir":false,
"rankdir":"LR",
"binaryTreePaths":true,"edgeLabels":" , ",
"shouldShowNullEdges":true,
"shouldDisplayGraph":true,
"outputFormat":"svg"}'
);

graph 0 {
 graph [label="HelloWorldSystem",id="HelloWorldSystem",stylesheet="Xholon.css",bgcolor=white]
 node [style=filled,fillcolor="#f0f8ff"]
 edge [ fontsize=8]
0 [label="HelloWorldSystem" id="HelloWorldSystem"]
0 -- 2 [label=" "];
2 [label="Hello" id="HelloWorldSystem/Hello"]
null20 [shape=point fillcolor="#000000"];
2 -- null20 [label=" "];
2 -- 3 [label=" "];
3 [label="World" id="HelloWorldSystem/World"]
null21 [shape=point fillcolor="#000000"];
3 -- null21 [label=" "];
null22 [shape=point fillcolor="#ffffff"];
3 -- null22 [label=" "];
null23 [shape=point fillcolor="#ffffff"];
0 -- null23 [label=" "];
}

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?xml-stylesheet href="Xholon.css" type="text/css"?>
<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN"
 "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
<!-- Generated by graphviz version 2.28.0 (20140111.2315)
 -->
<!-- Title: 0 Pages: 1 -->
<svg width="178pt" height="283pt"
 viewBox="0.00 0.00 178.00 282.80" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
<g id="HelloWorldSystem" class="graph" transform="scale(1 1) rotate(0) translate(4 278.8)">
<title>0</title>
<polygon fill="white" stroke="none" points="-4,4 -4,-278.8 174,-278.8 174,4 -4,4"/>
<text text-anchor="middle" x="85" y="-8.2" font-family="Times,serif" font-size="14.00">HelloWorldSystem</text>
<!-- 0 -->
<g id="HelloWorldSystem" class="node"><title>0</title>
<ellipse fill="#f0f8ff" stroke="black" cx="85" cy="-256.8" rx="85.3935" ry="18"/>
<text text-anchor="middle" x="85" y="-252.6" font-family="Times,serif" font-size="14.00">HelloWorldSystem</text>
</g>
<!-- 2 -->
<g id="HelloWorldSystem/Hello" class="node"><title>2</title>
<ellipse fill="#f0f8ff" stroke="black" cx="46" cy="-174.8" rx="32.2356" ry="18"/>
<text text-anchor="middle" x="46" y="-170.6" font-family="Times,serif" font-size="14.00">Hello</text>
</g>
<!-- 0&#45;&#45;2 -->
<g id="HelloWorldSystem_edge1" class="edge"><title>0&#45;&#45;2</title>
<path fill="none" stroke="black" d="M76.7291,-238.834C70.066,-225.166 60.7493,-206.055 54.1252,-192.467"/>
<text text-anchor="middle" x="69" y="-213.4" font-family="Times,serif" font-size="8.00"> </text>
</g>
<!-- null23 -->
<g id="HelloWorldSystem_node7" class="node"><title>null23</title>
<ellipse fill="#ffffff" stroke="black" cx="98" cy="-174.8" rx="1.8" ry="1.8"/>
</g>
<!-- 0&#45;&#45;null23 -->
<g id="HelloWorldSystem_edge6" class="edge"><title>0&#45;&#45;null23</title>
<path fill="none" stroke="black" d="M87.8208,-238.441C91.2858,-217.118 96.8305,-182.997 97.8397,-176.786"/>
<text text-anchor="middle" x="94" y="-213.4" font-family="Times,serif" font-size="8.00"> </text>
</g>
<!-- null20 -->
<g id="HelloWorldSystem_node3" class="node"><title>null20</title>
<ellipse fill="#000000" stroke="black" cx="34" cy="-92.8" rx="1.8" ry="1.8"/>
</g>
<!-- 2&#45;&#45;null20 -->
<g id="HelloWorldSystem_edge2" class="edge"><title>2&#45;&#45;null20</title>
<path fill="none" stroke="black" d="M43.4551,-156.834C40.2629,-135.552 35.0912,-101.075 34.1496,-94.797"/>
<text text-anchor="middle" x="42" y="-131.4" font-family="Times,serif" font-size="8.00"> </text>
</g>
<!-- 3 -->
<g id="HelloWorldSystem/World" class="node"><title>3</title>
<ellipse fill="#f0f8ff" stroke="black" cx="90" cy="-92.8" rx="35.5598" ry="18"/>
<text text-anchor="middle" x="90" y="-88.6" font-family="Times,serif" font-size="14.00">World</text>
</g>
<!-- 2&#45;&#45;3 -->
<g id="HelloWorldSystem_edge3" class="edge"><title>2&#45;&#45;3</title>
<path fill="none" stroke="black" d="M55.1168,-157.224C62.6683,-143.494 73.3466,-124.079 80.8949,-110.355"/>
<text text-anchor="middle" x="72" y="-131.4" font-family="Times,serif" font-size="8.00"> </text>
</g>
<!-- null21 -->
<g id="HelloWorldSystem_node5" class="node"><title>null21</title>
<ellipse fill="#000000" stroke="black" cx="77" cy="-26.8" rx="1.8" ry="1.8"/>
</g>
<!-- 3&#45;&#45;null21 -->
<g id="HelloWorldSystem_edge4" class="edge"><title>3&#45;&#45;null21</title>
<path fill="none" stroke="black" d="M86.5162,-74.6492C83.1468,-58.0613 78.388,-34.6333 77.251,-29.0355"/>
<text text-anchor="middle" x="84" y="-49.4" font-family="Times,serif" font-size="8.00"> </text>
</g>
<!-- null22 -->
<g id="HelloWorldSystem_node6" class="node"><title>null22</title>
<ellipse fill="#ffffff" stroke="black" cx="102" cy="-26.8" rx="1.8" ry="1.8"/>
</g>
<!-- 3&#45;&#45;null22 -->
<g id="HelloWorldSystem_edge5" class="edge"><title>3&#45;&#45;null22</title>
<path fill="none" stroke="black" d="M93.2158,-74.6492C96.326,-58.0613 100.719,-34.6333 101.768,-29.0355"/>
<text text-anchor="middle" x="99" y="-49.4" font-family="Times,serif" font-size="8.00"> </text>
</g>
</g>
</svg>

-}

