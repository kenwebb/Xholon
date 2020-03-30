/**
 * Scrape info from Haskell prelude module.
 * http://www.cse.chalmers.se/edu/course/TDA555/tourofprelude.html
 * Ken Webb
 * March 22, 2020
 */

var sections = document.querySelectorAll("section");
var outtext = "";
sections.forEach(function(section) {
  if (section && section.querySelector) {
    var fname = section.firstElementChild.textContent;
    var spantype = section.querySelector("span.type");
    if (spantype) {
      outtext += "// " + spantype.textContent.trim() + "\n";
      outtext += "RP." + fname + " = function() {\n  return TODO;\n}\n\n";
    }
  }
});
console.log(outtext);

