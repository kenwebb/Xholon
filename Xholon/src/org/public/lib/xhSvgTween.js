// Transition between 2 or more adjacent SVG images.
// xhSvgTween.js
// Ken Webb  January 8, 2015
// Designed to tween between 2 D3-CirclePack SVG images.
// Only handles SVG circle; returns if finds something else (ex: ellipse).

// usage: xh.tween();
//        xh.tween("#xhgraph", 5);
//        xh.tween(null,2);

xh.tween = function(selection, duration, sortedArr1) {
  
  if (typeof d3 == "undefined") {return;}
  
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
      arr.push(obj);
    }
    arr.sort(compare);
    return arr;
  };
  
  xh.tween.complete = function() {
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
  var durationMs = duration * 1000; // convert seconds to ms
  
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
      var one = arr1[ix1];
      var two = arr2[ix2];
      if (one.xhname < two.xhname) {ix1++; continue;}
      if (two.xhname < one.xhname) {ix2++; continue;}
      if (Math.abs(one.rvalue.value - two.rvalue.value) > 1) {
        d3.select(one.element).transition()
        .attr("r", two.rvalue.value)
        .duration(durationMs);
      }
      var matrix1 = one.transform.baseVal.getItem(0).matrix;
      var matrix2 = two.transform.baseVal.getItem(0).matrix;
      if ((Math.abs(matrix1.e - matrix2.e) > 1) || (Math.abs(matrix1.f - matrix2.f) > 1)) {
        d3.select(one.group).transition()
        .attr("transform", "translate(" + matrix2.e + "," + matrix2.f + ")")
        .duration(durationMs);
      }
      one.className.baseVal = two.className.baseVal;
      ix1++; ix2++;
    } catch (e) {
      var str = e.name + ": " + e.message;
      if (xh.root) {
        xh.root().println(str);
      }
      if (console && console.log) {
        console.log(str);
      }
      
      var p = document.createElement("p");
      p.textContent = str;
      document.querySelector("body").appendChild(p);
      return;
    }
  }

  setTimeout(xh.tween.complete, durationMs);
}

