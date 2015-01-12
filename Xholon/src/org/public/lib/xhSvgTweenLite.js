// Transition between 2 or more adjacent SVG images.
// xhSvgTween.js
// Ken Webb  November 19, 2014
// Designed to work with 2 D3-CirclePack SVG images in "The Black Geese" app.
// Only handles SVG circle; returns if finds something else (ex: ellipse).
// for now I'm using TweenLite for tweening, rather than D3

// usage: xh.tween();
//        xh.tween("#xhgraph", 5);
//        xh.tween(null,2);

if (xh.require) {
  xh.require("TweenLite.min");
  xh.require("AttrPlugin.min");
}

xh.tween = function(selection, duration, sortedArr1) {
  
  if (typeof TweenLite == "undefined") {return;}
  
  // array of active TweenLite instances
  var tlInstanceArr = [];
  
  // for use with Array.sort
  var compare = function(a, b) {
    if (a.xhname < b.xhname) {return -1;}
    if (a.xhname == b.xhname) {return 0;}
    return 1;
  };
  
  // sort both arrays so it's easy to walk through them in parallel
  var makeSortedArray = function(group) {
    var arr = [];
    for (var i = 0; i < group.length; i++) {
      var g = group[i];
      if (g.className.baseVal == "d3cpdummy") {continue;}
      var obj = {};
      obj.group = g;
      obj.element = g.querySelector("circle");
      if (!obj.element) {return null;}
      obj.xhname = g.querySelector("title").textContent;
      obj.transform = g.transform;
      obj.rvalue = obj.element.r.baseVal;
      obj.className = g.className;
      //obj.text = g.querySelector("text");
      arr.push(obj);
    }
    arr.sort(compare);
    return arr;
  };
  
  var complete = function() {
    // kill any TweenLite animations that are still running, before removing their SVG target
    for (var i = 0; i < tlInstanceArr.length; i++) {
      var tli = tlInstanceArr[i];
      if (tli.isActive()) {
        console.log(tli);
        tli.kill();
      }
    }
    
    // delete svg1, effectively making svg2 the new svg1 for the next cycle
    if (svg1.parentNode) {
      svg1.parentNode.removeChild(svg1);
      // remove "hidden" from list of classes for svg2, if it exists
      if (svg2.classList) {
        // IE11 Unable to get property 'remove' of undefined or null reference
        svg2.classList.remove("hidden");
      }
      else {
        svg2.setAttribute("class", "");
      }
    }
    
    // is this a video rather than a Xholon app
    if (selection == "div#video") {return;}
    
    xh.tween(selection, duration, arr2);
  };
  
  if ((selection === undefined) || (selection === null)) {
    selection = "#xhgraph";
  }
  if ((duration === undefined) || (duration === null)) {
    duration = 5;
  }
  
  //var svg1 = document.querySelector(selection + ">svg");
  var svg1 = null;
  try {
    svg1 = document.querySelector(selection + ">svg");
  } catch(e) {
    console.log(e); // SYNTAX_ERR
    return;
  }
  if (!svg1) {return;}
  var svg2 = svg1.nextElementSibling;
  if (!svg2) {return;}
  
  if (sortedArr1 === undefined) {
    var g1 = svg1.querySelectorAll("g>g");
    arr1 = makeSortedArray(g1);
    if (!arr1) {return;}
  }
  else {
    arr1 = sortedArr1;
  }
  
  var g2 = svg2.querySelectorAll("g>g");
  var arr2 = makeSortedArray(g2);
  
  // only tween nodes that exist in both one and two
  var ix1 = 0;
  var ix2 = 0;
  while ((ix1 < arr1.length) && (ix2 < arr2.length)) {
    try {
      //throw new Error("This is an error TESTING");
      // TODO crash is probably caused by something here; comment things out one at a time
      var one = arr1[ix1];
      var two = arr2[ix2];
      if (one.xhname < two.xhname) {ix1++; continue;}
      if (two.xhname < one.xhname) {ix2++; continue;}
      if (Math.abs(one.rvalue.value - two.rvalue.value) > 1) {
        //tlInstanceArr.push(TweenLite.to(one.rvalue, duration, {value:two.rvalue.value})); // 2 no crash when commented out; crashes when this line is active; THIS IS THE PROBLEM
        // try attr plugin
        //TweenLite.to("#rect", 1, {attr:{x:100, y:50, width:100, height:100}, ease:Linear.easeNone});
        tlInstanceArr.push(TweenLite.to(one.element, duration, {attr:{r:two.rvalue.value}}));
      }
      var matrix1 = one.transform.baseVal.getItem(0).matrix; //baseVal[0].matrix;
      var matrix2 = two.transform.baseVal.getItem(0).matrix; //baseVal[0].matrix;
      if ((Math.abs(matrix1.e - matrix2.e) > 1) || (Math.abs(matrix1.f - matrix2.f) > 1)) {
        // the following line works on Chrome Firefox
        tlInstanceArr.push(TweenLite.to(matrix1, duration, {e:matrix2.e, f:matrix2.f})); // 3 commenting-out this doesn't fix crash
        
        //TweenLite.to("#element", 1.5, {attr:{transform:"translate(40, 60)"}});
        // there's a bug in TweenLite that prevents this from working; fixed in GSAP 1.14.0 ?
        //var tr = 'translate(' + matrix2.e + ', ' + matrix2.f + ')';
        //console.log(tr);
        //console.log(one.group);
        //tlInstanceArr.push(TweenLite.to(one.group, duration, {attr:{transform:tr}}));
      }
      one.className.baseVal = two.className.baseVal; // 1 commenting-out this doesn't fix crash
      ix1++; ix2++;
    } catch (e) {
      var str = e.name + ": " + e.message;
      if (xh.root) {
        xh.root().println(str);
      }
      if (console && console.log) {
        console.log(str);
      }
      //alert(str);
      
      var p = document.createElement("p");
      p.textContent = str;
      document.querySelector("body").appendChild(p);
      return;
    }
  }

  TweenLite.to(one, duration, {onComplete:complete});
}

