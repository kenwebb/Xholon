// Transition between 2 or more adjacent SVG images.
// xhSvgTween.js
// Ken Webb  January 8, 2015
// MIT License, Copyright (C) 2015 Ken Webb
// Designed to tween between 2 D3-CirclePack SVG images.
// Only handles SVG circle; returns if finds something else (ex: ellipse).

// usage: xh.tween();
//        xh.tween("#xhgraph", 5);
//        xh.tween(null,2);

if (typeof xh == "undefined") {
  xh = {};
}

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
  
  // advanced options (under construction)
  var tweenFontSize = true;
  var tweenTextDy = false;
  var fadeIn = true;
  var fadeOut = true;
  
  // d3 easings
  //var easingTypes = ["linear", "poly", "quad", "cubic", "sin", "exp", "circle", "elastic", "back", "bounce"];
  //var easingModes = ["in", "out", "in-out", "out-in"];
  //var easingType = "cubic"; // default is cubic
  //var easingMode = "in-out"; // default is in-out
  //var easing = "cubic-in-out"; // default is cubic-in-out
  
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
      if (one.xhname < two.xhname) {
        if (fadeOut) {
          // optionally fade "one" out of existence; opacity or alpha
          d3.select(one.group).transition()
          .style("opacity", 0) // and/or .attr() ?   .style() works in Harold
          .duration(durationMs)
          .remove();
          // OR fade-out the whole group using transform scale to 0; this also translates group to upper left
          /*d3.select(one.group).transition()
          .attr("transform", "scale(0,0)")
          .duration(durationMs);*/
        }
        ix1++;
        continue;
      }
      if (two.xhname < one.xhname) {
        if (fadeIn) {
          // optionally fade "two" into existence
          var twoNode = two.group;
          var newG = d3.select(twoNode.cloneNode(true))
          .style("opacity", 0);
          var topG = d3.select(svg1).select("g");
          topG.append(function() {return newG.node();});
          // where do I get the svg2 opacity from ?
          // make it a relatively high value in g; at end of transition, set it to none
          newG.transition()
          .style("opacity", 1)
          .duration(durationMs);
        }
        ix2++;
        continue;
      }
      if (Math.abs(one.rvalue.value - two.rvalue.value) > 1) {
        d3.select(one.element).transition()
        .attr("r", two.rvalue.value)
        .duration(durationMs);
        //.ease(easing);
        if (tweenFontSize) {
          var text1 = one.group.querySelector("text");
          if (text1) {
            var text2 = two.group.querySelector("text");
            if (text2) {
              d3.select(text1).transition()
              .style("font-size", function() {
                return d3.select(text2).style("font-size");
              })
              .duration(durationMs);
              if (tweenTextDy) {
                // if dy is in "em" units in one, and "px" units in other, d3 may not be able to do this
                // TODO it would be best if "em" units are not used at all
                // google: convert em to px javascript d3
                // http://stackoverflow.com/questions/10463518/converting-em-to-px-in-javascript-and-getting-default-font-size
                var oneVal = d3.select(text1).attr("dy");
                var twoVal = d3.select(text2).attr("dy");
                //console.log(oneVal + " " + twoVal);
                //if (oneVal == ".3em") {console.log(".3em" + d3.select(text1).style("font-size"));}
                //if (twoVal == ".3em") {console.log(".3em" + d3.select(text2).style("font-size"));}
                if (oneVal != twoVal) {
                  if (oneVal == ".3em") {
                    console.log(d3.select(text1).style("font-size").substring(0,2));
                    console.log(0.3 * d3.select(text1).style("font-size").substring(0,2));
                    d3.select(text1).attr("dy", "" + Math.round(0.3 * d3.select(text1).style("font-size").substring(0,2)) + "px");
                    console.log(d3.select(text1).attr("dy"));
                  }
                  d3.select(text1).transition()
                  .attr("dy", function() {
                    if (twoVal == ".3em") {
                      return "" + Math.round(0.3 * d3.select(text2).style("font-size").substring(0,2)) + "px";
                    }
                    return twoVal;
                  })
                  .duration(durationMs);
                }
              }
            }
          }
        }
      }
      var matrix1 = one.transform.baseVal.getItem(0).matrix;
      var matrix2 = two.transform.baseVal.getItem(0).matrix;
      if ((Math.abs(matrix1.e - matrix2.e) > 1) || (Math.abs(matrix1.f - matrix2.f) > 1)) {
        d3.select(one.group).transition()
        .attr("transform", "translate(" + matrix2.e + "," + matrix2.f + ")")
        .duration(durationMs);
        //.ease(easing);
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

