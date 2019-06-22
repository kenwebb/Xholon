/**
 * xhtype.js
 * (C) Ken Webb, MIT license
 * May 25, 2019
 * 
 * Each XholonClass has an xhType. This is an injective mapping, but is not surjective.
 *  XholonClass --> XhTypeId --> XhTypelabel
 *  ex: "HelloWorld" |-> 1
 * There is a one-to-one bijective mapping between arrayofXhTypeIds and arrayofXhTypelabels.
 *  ex: 0 |-> "Xhtypexxxxxxxxx"  and  "Xhtypexxxxxxxxx" |-> 0
 * This mapping could be expressed as a set of ordered pairs [(0,"Xhtypexxxxxxxxx"), ...] .
 * Note that in this case I don't want to take the product arrayofXhTypeIds x arrayofXhTypelabels .
 * The only part of the product that would be useful or make sense is the diagonal.
 * Some xhTypes have alternative (more user friendly) labels.
 * xhType is like a relational database lookup table
 * 
 * @see https://github.com/kenwebb/Xholon/blob/master/Xholon/src/org/primordion/xholon/base/XholonClass.java
 * This .java file also includes some less important xhType values that I am not including here.
 */

const arrayofXhTypeIds = [0, 1, 2, 3, 4, 5, 6, 7, 8, 16, 24, 32, 40, 48, 56, 64];

const arrayofXhTypelabels = [
"Xhtypexxxxxxxxx", "XhtypexxxxxxCon", "XhtypexxxFgsxxx", "XhtypexxxFgsCon", "XhtypeBehxxxxxx",
"XhtypeBehxxxCon", "XhtypeBehFgsxxx", "XhtypeBehFgsCon", "XhtypeActivity", "XhtypeConfigContainer",
"XhtypeStateMachineEntity", "XhtypeCtrnnEntity", "XhtypeMemCompEntity", "XhtypeBraneCalcEntity", "XhtypePort",
"XhtypeGridEntity"
];

const arrayofXhTypelabelsAlt = [
"XhtypeNone", "XhtypePureContainer", "XhtypePurePassiveObject", "XhtypexxxFgsCon", "XhtypePureActiveObject",
"XhtypeBehxxxCon", "XhtypeBehFgsxxx", "XhtypeBehFgsCon", "XhtypeActivity", "XhtypeConfigContainer",
"XhtypeStateMachineEntity", "XhtypeCtrnnEntity", "XhtypeMemCompEntity", "XhtypeBraneCalcEntity", "XhtypePort",
"XhtypeGridEntity"
];

function buildArrayofXhTypeIds() {
  return arrayofXhTypeIds;
}

function buildArrayofXhTypelabels() {
  return arrayofXhTypelabels;
}

function buildArrayofXhTypelabelsAlt() {
  return arrayofXhTypelabelsAlt;
}

function buildArrayofXhTypePairs() {
  let arr = [];
  for (var i = 0; i < arrayofXhTypeIds.length; i++) {
    arr.push([arrayofXhTypeIds[i], arrayofXhTypelabels[i]]);
  }
  return arr;
}

function buildArrayofXhTypePairsAlt() {
  let arr = [];
  for (var i = 0; i < arrayofXhTypeIds.length; i++) {
    arr.push([arrayofXhTypeIds[i], arrayofXhTypelabelsAlt[i]]);
  }
  return arr;
}

export {buildArrayofXhTypeIds, buildArrayofXhTypelabels, buildArrayofXhTypelabelsAlt, buildArrayofXhTypePairs, buildArrayofXhTypePairsAlt};

